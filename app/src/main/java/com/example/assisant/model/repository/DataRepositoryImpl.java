package com.example.assisant.model.repository;

import android.util.Log;
import android.util.LruCache;

import com.example.assisant.model.*;
import com.example.assisant.utils.StorageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 数据仓库实现类
 * 
 * 实现DataRepository接口，提供统一的数据访问抽象层。
 * 集成本地数据源，支持数据缓存策略、异步操作和线程安全访问。
 * 
 * 功能特性：
 * - 统一的数据访问接口
 * - LRU缓存策略，提高数据访问性能
 * - 异步操作支持，避免阻塞UI线程
 * - 读写锁机制，确保线程安全
 * - 数据一致性保证
 * - 异常处理和容错机制
 * 
 * @author Campus AI Assistant Team
 * @version 1.0
 */
public class DataRepositoryImpl implements DataRepository {

    private static final String TAG = "DataRepositoryImpl";

    // 缓存大小配置
    private static final int CACHE_SIZE = 50; // 最大缓存条目数
    private static final long CACHE_EXPIRE_TIME = 5 * 60 * 1000; // 5分钟过期时间

    // 单例实例
    private static volatile DataRepositoryImpl instance;

    // 本地数据源
    private final LocalDataSource localDataSource;

    // LRU缓存
    private final LruCache<String, CacheEntry> cache;

    // 线程池，用于异步操作
    private final ExecutorService executorService;

    // 读写锁，确保线程安全
    private final ReadWriteLock readWriteLock;

    /**
     * 缓存条目类
     */
    private static class CacheEntry {
        final Object data;
        final long timestamp;

        CacheEntry(Object data) {
            this.data = data;
            this.timestamp = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRE_TIME;
        }
    }

    /**
     * 私有构造函数，实现单例模式
     * 
     * @param storageManager 存储管理器实例
     */
    private DataRepositoryImpl(StorageManager storageManager) {
        this.localDataSource = new LocalDataSource(storageManager);
        this.cache = new LruCache<String, CacheEntry>(CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, CacheEntry value) {
                return 1; // 每个条目占用1个单位
            }
        };
        this.executorService = Executors.newFixedThreadPool(4);
        this.readWriteLock = new ReentrantReadWriteLock();

        Log.d(TAG, "DataRepositoryImpl initialized");
    }

    /**
     * 获取DataRepositoryImpl单例实例
     * 
     * @param storageManager 存储管理器实例
     * @return DataRepositoryImpl实例
     */
    public static DataRepositoryImpl getInstance(StorageManager storageManager) {
        if (instance == null) {
            synchronized (DataRepositoryImpl.class) {
                if (instance == null) {
                    instance = new DataRepositoryImpl(storageManager);
                }
            }
        }
        return instance;
    }

    /**
     * 从缓存获取数据
     * 
     * @param key   缓存键
     * @param clazz 数据类型
     * @return 缓存的数据，如果不存在或已过期则返回null
     */
    @SuppressWarnings("unchecked")
    private <T> T getFromCache(String key, Class<T> clazz) {
        readWriteLock.readLock().lock();
        try {
            CacheEntry entry = cache.get(key);
            if (entry != null && !entry.isExpired()) {
                Log.d(TAG, "Cache hit for key: " + key);
                return (T) entry.data;
            } else if (entry != null && entry.isExpired()) {
                Log.d(TAG, "Cache expired for key: " + key);
                cache.remove(key);
            }
            return null;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 将数据存入缓存
     * 
     * @param key  缓存键
     * @param data 要缓存的数据
     */
    private void putToCache(String key, Object data) {
        if (data != null) {
            readWriteLock.writeLock().lock();
            try {
                cache.put(key, new CacheEntry(data));
                Log.d(TAG, "Data cached for key: " + key);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
    }

    /**
     * 从缓存中移除数据
     * 
     * @param key 缓存键
     */
    private void removeFromCache(String key) {
        readWriteLock.writeLock().lock();
        try {
            cache.remove(key);
            Log.d(TAG, "Cache removed for key: " + key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 清除相关缓存
     * 
     * @param prefix 缓存键前缀
     */
    private void clearRelatedCache(String prefix) {
        readWriteLock.writeLock().lock();
        try {
            List<String> keysToRemove = new ArrayList<>();
            for (String key : cache.snapshot().keySet()) {
                if (key.startsWith(prefix)) {
                    keysToRemove.add(key);
                }
            }
            for (String key : keysToRemove) {
                cache.remove(key);
            }
            Log.d(TAG, "Cleared cache with prefix: " + prefix);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // ==================== 课程数据操作 ====================

    @Override
    public List<Course> getAllCourses() {
        final String cacheKey = "all_courses";

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<Course> cachedCourses = getFromCache(cacheKey, List.class);
        if (cachedCourses != null) {
            return cachedCourses;
        }

        // 从本地数据源获取
        List<Course> courses = localDataSource.getAllCourses();

        // 存入缓存
        putToCache(cacheKey, courses);

        return courses;
    }

    @Override
    public Course getCourseById(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            return null;
        }

        final String cacheKey = "course_" + courseId;

        // 尝试从缓存获取
        Course cachedCourse = getFromCache(cacheKey, Course.class);
        if (cachedCourse != null) {
            return cachedCourse;
        }

        // 从本地数据源获取
        Course course = localDataSource.getCourseById(courseId);

        // 存入缓存
        if (course != null) {
            putToCache(cacheKey, course);
        }

        return course;
    }

    @Override
    public boolean saveCourse(Course course) {
        if (course == null) {
            Log.w(TAG, "Cannot save null course");
            return false;
        }

        // 保存到本地数据源
        boolean success = localDataSource.saveCourse(course);

        if (success) {
            // 清除相关缓存
            clearRelatedCache("course");
            clearRelatedCache("all_courses");

            // 更新单个课程缓存
            putToCache("course_" + course.getCourseId(), course);

            Log.d(TAG, "Course saved successfully: " + course.getCourseId());
        }

        return success;
    }

    @Override
    public boolean deleteCourse(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            return false;
        }

        // 从本地数据源删除
        boolean success = localDataSource.deleteCourse(courseId);

        if (success) {
            // 清除相关缓存
            removeFromCache("course_" + courseId);
            clearRelatedCache("all_courses");
            clearRelatedCache("grades_course_" + courseId);

            Log.d(TAG, "Course deleted successfully: " + courseId);
        }

        return success;
    }

    @Override
    public List<Grade> getGradesByCourse(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        final String cacheKey = "grades_course_" + courseId;

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<Grade> cachedGrades = getFromCache(cacheKey, List.class);
        if (cachedGrades != null) {
            return cachedGrades;
        }

        // 从本地数据源获取
        List<Grade> grades = localDataSource.getGradesByCourse(courseId);

        // 存入缓存
        putToCache(cacheKey, grades);

        return grades;
    }

    // ==================== 学习计划操作 ====================

    @Override
    public List<StudyPlan> getAllStudyPlans() {
        final String cacheKey = "all_study_plans";

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<StudyPlan> cachedPlans = getFromCache(cacheKey, List.class);
        if (cachedPlans != null) {
            return cachedPlans;
        }

        // 从本地数据源获取
        List<StudyPlan> plans = localDataSource.getAllStudyPlans();

        // 存入缓存
        putToCache(cacheKey, plans);

        return plans;
    }

    @Override
    public StudyPlan getStudyPlanById(String planId) {
        if (planId == null || planId.trim().isEmpty()) {
            return null;
        }

        final String cacheKey = "study_plan_" + planId;

        // 尝试从缓存获取
        StudyPlan cachedPlan = getFromCache(cacheKey, StudyPlan.class);
        if (cachedPlan != null) {
            return cachedPlan;
        }

        // 从本地数据源获取
        StudyPlan plan = localDataSource.getStudyPlanById(planId);

        // 存入缓存
        if (plan != null) {
            putToCache(cacheKey, plan);
        }

        return plan;
    }

    @Override
    public boolean saveStudyPlan(StudyPlan plan) {
        if (plan == null) {
            Log.w(TAG, "Cannot save null study plan");
            return false;
        }

        // 保存到本地数据源
        boolean success = localDataSource.saveStudyPlan(plan);

        if (success) {
            // 清除相关缓存
            clearRelatedCache("study_plan");
            clearRelatedCache("all_study_plans");

            // 更新单个计划缓存
            putToCache("study_plan_" + plan.getPlanId(), plan);

            Log.d(TAG, "Study plan saved successfully: " + plan.getPlanId());
        }

        return success;
    }

    @Override
    public boolean deleteStudyPlan(String planId) {
        if (planId == null || planId.trim().isEmpty()) {
            return false;
        }

        // 从本地数据源删除
        boolean success = localDataSource.deleteStudyPlan(planId);

        if (success) {
            // 清除相关缓存
            removeFromCache("study_plan_" + planId);
            clearRelatedCache("all_study_plans");
            clearRelatedCache("tasks_plan_" + planId);

            Log.d(TAG, "Study plan deleted successfully: " + planId);
        }

        return success;
    }

    @Override
    public List<StudyTask> getTasksByPlan(String planId) {
        if (planId == null || planId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        final String cacheKey = "tasks_plan_" + planId;

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<StudyTask> cachedTasks = getFromCache(cacheKey, List.class);
        if (cachedTasks != null) {
            return cachedTasks;
        }

        // 从本地数据源获取
        List<StudyTask> tasks = localDataSource.getTasksByPlan(planId);

        // 存入缓存
        putToCache(cacheKey, tasks);

        return tasks;
    }

    // ==================== 聊天记录操作 ====================

    @Override
    public List<ChatSession> getAllChatSessions() {
        final String cacheKey = "all_chat_sessions";

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<ChatSession> cachedSessions = getFromCache(cacheKey, List.class);
        if (cachedSessions != null) {
            return cachedSessions;
        }

        // 从本地数据源获取
        List<ChatSession> sessions = localDataSource.getAllChatSessions();

        // 存入缓存
        putToCache(cacheKey, sessions);

        return sessions;
    }

    @Override
    public ChatSession getChatSessionById(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return null;
        }

        final String cacheKey = "chat_session_" + sessionId;

        // 尝试从缓存获取
        ChatSession cachedSession = getFromCache(cacheKey, ChatSession.class);
        if (cachedSession != null) {
            return cachedSession;
        }

        // 从本地数据源获取
        ChatSession session = localDataSource.getChatSessionById(sessionId);

        // 存入缓存
        if (session != null) {
            putToCache(cacheKey, session);
        }

        return session;
    }

    @Override
    public boolean saveChatSession(ChatSession session) {
        if (session == null) {
            Log.w(TAG, "Cannot save null chat session");
            return false;
        }

        // 保存到本地数据源
        boolean success = localDataSource.saveChatSession(session);

        if (success) {
            // 清除相关缓存
            clearRelatedCache("chat_session");
            clearRelatedCache("all_chat_sessions");

            // 更新单个会话缓存
            putToCache("chat_session_" + session.getSessionId(), session);

            Log.d(TAG, "Chat session saved successfully: " + session.getSessionId());
        }

        return success;
    }

    @Override
    public boolean saveMessage(Message message) {
        if (message == null) {
            Log.w(TAG, "Cannot save null message");
            return false;
        }

        // 保存到本地数据源
        boolean success = localDataSource.saveMessage(message);

        if (success) {
            // 清除相关缓存（消息变化可能影响会话）
            clearRelatedCache("chat_session");
            clearRelatedCache("all_chat_sessions");

            Log.d(TAG, "Message saved successfully: " + message.getMessageId());
        }

        return success;
    }

    @Override
    public boolean deleteChatSession(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return false;
        }

        // 从本地数据源删除
        boolean success = localDataSource.deleteChatSession(sessionId);

        if (success) {
            // 清除相关缓存
            removeFromCache("chat_session_" + sessionId);
            clearRelatedCache("all_chat_sessions");

            Log.d(TAG, "Chat session deleted successfully: " + sessionId);
        }

        return success;
    }

    // ==================== 校园信息操作 ====================

    @Override
    public List<InfoCategory> getInfoCategories() {
        final String cacheKey = "info_categories";

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<InfoCategory> cachedCategories = getFromCache(cacheKey, List.class);
        if (cachedCategories != null) {
            return cachedCategories;
        }

        // 从本地数据源获取
        List<InfoCategory> categories = localDataSource.getInfoCategories();

        // 存入缓存
        putToCache(cacheKey, categories);

        return categories;
    }

    @Override
    public List<CampusInfo> getInfoByCategory(String categoryId) {
        if (categoryId == null || categoryId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        final String cacheKey = "info_category_" + categoryId;

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<CampusInfo> cachedInfo = getFromCache(cacheKey, List.class);
        if (cachedInfo != null) {
            return cachedInfo;
        }

        // 从本地数据源获取
        List<CampusInfo> info = localDataSource.getInfoByCategory(categoryId);

        // 存入缓存
        putToCache(cacheKey, info);

        return info;
    }

    @Override
    public List<CampusInfo> searchCampusInfo(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // 搜索结果不缓存，因为搜索条件变化频繁
        return localDataSource.searchCampusInfo(keyword);
    }

    @Override
    public List<CampusInfo> getFavoriteInfo() {
        final String cacheKey = "favorite_info";

        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<CampusInfo> cachedFavorites = getFromCache(cacheKey, List.class);
        if (cachedFavorites != null) {
            return cachedFavorites;
        }

        // 从本地数据源获取
        List<CampusInfo> favorites = localDataSource.getFavoriteInfo();

        // 存入缓存
        putToCache(cacheKey, favorites);

        return favorites;
    }

    @Override
    public boolean toggleFavorite(String infoId) {
        if (infoId == null || infoId.trim().isEmpty()) {
            return false;
        }

        // 切换收藏状态
        boolean success = localDataSource.toggleFavorite(infoId);

        if (success) {
            // 清除相关缓存
            clearRelatedCache("favorite_info");
            clearRelatedCache("info_category");

            Log.d(TAG, "Favorite toggled successfully for info: " + infoId);
        }

        return success;
    }

    // ==================== 数据管理操作 ====================

    @Override
    public boolean clearAllCache() {
        readWriteLock.writeLock().lock();
        try {
            cache.evictAll();
            Log.d(TAG, "All cache cleared");
            return true;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean exportAllData(String exportPath) {
        // 导出前清除缓存，确保数据一致性
        clearAllCache();

        return localDataSource.exportAllData(exportPath);
    }

    @Override
    public boolean importAllData(String importPath) {
        // 导入后清除缓存，强制重新加载数据
        boolean success = localDataSource.importAllData(importPath);

        if (success) {
            clearAllCache();
            Log.d(TAG, "Data imported successfully, cache cleared");
        }

        return success;
    }

    @Override
    public DataStatistics getDataStatistics() {
        final String cacheKey = "data_statistics";

        // 尝试从缓存获取
        DataStatistics cachedStats = getFromCache(cacheKey, DataStatistics.class);
        if (cachedStats != null) {
            return cachedStats;
        }

        // 从本地数据源获取
        DataStatistics stats = localDataSource.getDataStatistics();

        // 存入缓存（统计信息缓存时间较短）
        putToCache(cacheKey, stats);

        return stats;
    }

    // ==================== 异步操作支持 ====================

    /**
     * 异步获取所有课程
     * 
     * @return Future对象，可用于获取异步结果
     */
    public Future<List<Course>> getAllCoursesAsync() {
        return executorService.submit(this::getAllCourses);
    }

    /**
     * 异步保存课程
     * 
     * @param course 要保存的课程
     * @return Future对象，可用于获取异步结果
     */
    public Future<Boolean> saveCourseAsync(Course course) {
        return executorService.submit(() -> saveCourse(course));
    }

    /**
     * 异步获取所有学习计划
     * 
     * @return Future对象，可用于获取异步结果
     */
    public Future<List<StudyPlan>> getAllStudyPlansAsync() {
        return executorService.submit(this::getAllStudyPlans);
    }

    /**
     * 异步保存学习计划
     * 
     * @param plan 要保存的学习计划
     * @return Future对象，可用于获取异步结果
     */
    public Future<Boolean> saveStudyPlanAsync(StudyPlan plan) {
        return executorService.submit(() -> saveStudyPlan(plan));
    }

    /**
     * 异步搜索校园信息
     * 
     * @param keyword 搜索关键字
     * @return Future对象，可用于获取异步结果
     */
    public Future<List<CampusInfo>> searchCampusInfoAsync(String keyword) {
        return executorService.submit(() -> searchCampusInfo(keyword));
    }

    // ==================== 资源管理 ====================

    /**
     * 关闭资源，释放线程池
     */
    public void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            Log.d(TAG, "ExecutorService shutdown");
        }
    }

    /**
     * 获取缓存统计信息
     * 
     * @return 缓存统计信息字符串
     */
    public String getCacheStats() {
        readWriteLock.readLock().lock();
        try {
            return String.format("Cache size: %d/%d, Hit count: %d, Miss count: %d",
                    cache.size(), cache.maxSize(), cache.hitCount(), cache.missCount());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}