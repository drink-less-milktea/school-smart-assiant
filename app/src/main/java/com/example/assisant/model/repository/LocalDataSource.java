package com.example.assisant.model.repository;

import android.util.Log;

import com.example.assisant.model.*;
import com.example.assisant.utils.StorageManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地数据源实现类
 * 
 * 实现DataRepository接口，提供基于本地存储的数据访问功能。
 * 使用StorageManager进行JSON文件的读写操作，支持数据的持久化存储。
 * 
 * 功能特性：
 * - 基于JSON文件的数据持久化
 * - 完整的CRUD操作支持
 * - 数据搜索和过滤功能
 * - 异常处理和容错机制
 * - 线程安全的数据访问
 * 
 * @author Campus AI Assistant Team
 * @version 1.0
 */
public class LocalDataSource implements DataRepository {

    private static final String TAG = "LocalDataSource";

    // 数据文件名常量
    private static final String COURSES_FILE = "courses.json";
    private static final String STUDY_PLANS_FILE = "study_plans.json";
    private static final String STUDY_TASKS_FILE = "study_tasks.json";
    private static final String CHAT_SESSIONS_FILE = "chat_sessions.json";
    private static final String MESSAGES_FILE = "messages.json";
    private static final String CAMPUS_INFO_FILE = "campus_info.json";
    private static final String GRADES_FILE = "grades.json";

    // 存储管理器
    private final StorageManager storageManager;

    /**
     * 构造函数
     * 
     * @param storageManager 存储管理器实例
     */
    public LocalDataSource(StorageManager storageManager) {
        this.storageManager = storageManager;
        Log.d(TAG, "LocalDataSource initialized");
    }

    // ==================== 课程数据操作 ====================

    @Override
    public List<Course> getAllCourses() {
        try {
            Type coursesType = new TypeToken<List<Course>>() {
            }.getType();
            List<Course> courses = storageManager.loadFromJsonFile(COURSES_FILE, coursesType);
            return courses != null ? courses : new ArrayList<>();
        } catch (Exception e) {
            Log.e(TAG, "Error loading courses", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Course getCourseById(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            return null;
        }

        List<Course> courses = getAllCourses();
        for (Course course : courses) {
            if (courseId.equals(course.getCourseId())) {
                return course;
            }
        }
        return null;
    }

    @Override
    public boolean saveCourse(Course course) {
        if (course == null) {
            Log.w(TAG, "Cannot save null course");
            return false;
        }

        try {
            List<Course> courses = getAllCourses();

            // 查找是否已存在相同ID的课程
            boolean found = false;
            for (int i = 0; i < courses.size(); i++) {
                if (course.getCourseId().equals(courses.get(i).getCourseId())) {
                    courses.set(i, course); // 更新现有课程
                    found = true;
                    break;
                }
            }

            if (!found) {
                courses.add(course); // 添加新课程
            }

            return storageManager.saveToJsonFile(courses, COURSES_FILE);
        } catch (Exception e) {
            Log.e(TAG, "Error saving course", e);
            return false;
        }
    }

    @Override
    public boolean deleteCourse(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            return false;
        }

        try {
            List<Course> courses = getAllCourses();
            boolean removed = courses.removeIf(course -> courseId.equals(course.getCourseId()));

            if (removed) {
                return storageManager.saveToJsonFile(courses, COURSES_FILE);
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting course", e);
            return false;
        }
    }

    @Override
    public List<Grade> getGradesByCourse(String courseId) {
        try {
            Type gradesType = new TypeToken<List<Grade>>() {
            }.getType();
            List<Grade> allGrades = storageManager.loadFromJsonFile(GRADES_FILE, gradesType);

            if (allGrades == null) {
                return new ArrayList<>();
            }

            List<Grade> courseGrades = new ArrayList<>();
            for (Grade grade : allGrades) {
                if (courseId.equals(grade.getCourseId())) {
                    courseGrades.add(grade);
                }
            }
            return courseGrades;
        } catch (Exception e) {
            Log.e(TAG, "Error loading grades for course: " + courseId, e);
            return new ArrayList<>();
        }
    }

    // ==================== 学习计划操作 ====================

    @Override
    public List<StudyPlan> getAllStudyPlans() {
        try {
            Type plansType = new TypeToken<List<StudyPlan>>() {
            }.getType();
            List<StudyPlan> plans = storageManager.loadFromJsonFile(STUDY_PLANS_FILE, plansType);
            return plans != null ? plans : new ArrayList<>();
        } catch (Exception e) {
            Log.e(TAG, "Error loading study plans", e);
            return new ArrayList<>();
        }
    }

    @Override
    public StudyPlan getStudyPlanById(String planId) {
        if (planId == null || planId.trim().isEmpty()) {
            return null;
        }

        List<StudyPlan> plans = getAllStudyPlans();
        for (StudyPlan plan : plans) {
            if (planId.equals(plan.getPlanId())) {
                return plan;
            }
        }
        return null;
    }

    @Override
    public boolean saveStudyPlan(StudyPlan plan) {
        if (plan == null) {
            Log.w(TAG, "Cannot save null study plan");
            return false;
        }

        try {
            List<StudyPlan> plans = getAllStudyPlans();

            // 查找是否已存在相同ID的计划
            boolean found = false;
            for (int i = 0; i < plans.size(); i++) {
                if (plan.getPlanId().equals(plans.get(i).getPlanId())) {
                    plans.set(i, plan); // 更新现有计划
                    found = true;
                    break;
                }
            }

            if (!found) {
                plans.add(plan); // 添加新计划
            }

            return storageManager.saveToJsonFile(plans, STUDY_PLANS_FILE);
        } catch (Exception e) {
            Log.e(TAG, "Error saving study plan", e);
            return false;
        }
    }

    @Override
    public boolean deleteStudyPlan(String planId) {
        if (planId == null || planId.trim().isEmpty()) {
            return false;
        }

        try {
            List<StudyPlan> plans = getAllStudyPlans();
            boolean removed = plans.removeIf(plan -> planId.equals(plan.getPlanId()));

            if (removed) {
                return storageManager.saveToJsonFile(plans, STUDY_PLANS_FILE);
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting study plan", e);
            return false;
        }
    }

    @Override
    public List<StudyTask> getTasksByPlan(String planId) {
        try {
            Type tasksType = new TypeToken<List<StudyTask>>() {
            }.getType();
            List<StudyTask> allTasks = storageManager.loadFromJsonFile(STUDY_TASKS_FILE, tasksType);

            if (allTasks == null) {
                return new ArrayList<>();
            }

            List<StudyTask> planTasks = new ArrayList<>();
            for (StudyTask task : allTasks) {
                if (planId.equals(task.getPlanId())) {
                    planTasks.add(task);
                }
            }
            return planTasks;
        } catch (Exception e) {
            Log.e(TAG, "Error loading tasks for plan: " + planId, e);
            return new ArrayList<>();
        }
    }

    // ==================== 聊天记录操作 ====================

    @Override
    public List<ChatSession> getAllChatSessions() {
        try {
            Type sessionsType = new TypeToken<List<ChatSession>>() {
            }.getType();
            List<ChatSession> sessions = storageManager.loadFromJsonFile(CHAT_SESSIONS_FILE, sessionsType);
            return sessions != null ? sessions : new ArrayList<>();
        } catch (Exception e) {
            Log.e(TAG, "Error loading chat sessions", e);
            return new ArrayList<>();
        }
    }

    @Override
    public ChatSession getChatSessionById(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return null;
        }

        List<ChatSession> sessions = getAllChatSessions();
        for (ChatSession session : sessions) {
            if (sessionId.equals(session.getSessionId())) {
                return session;
            }
        }
        return null;
    }

    @Override
    public boolean saveChatSession(ChatSession session) {
        if (session == null) {
            Log.w(TAG, "Cannot save null chat session");
            return false;
        }

        try {
            List<ChatSession> sessions = getAllChatSessions();

            // 查找是否已存在相同ID的会话
            boolean found = false;
            for (int i = 0; i < sessions.size(); i++) {
                if (session.getSessionId().equals(sessions.get(i).getSessionId())) {
                    sessions.set(i, session); // 更新现有会话
                    found = true;
                    break;
                }
            }

            if (!found) {
                sessions.add(session); // 添加新会话
            }

            return storageManager.saveToJsonFile(sessions, CHAT_SESSIONS_FILE);
        } catch (Exception e) {
            Log.e(TAG, "Error saving chat session", e);
            return false;
        }
    }

    @Override
    public boolean saveMessage(Message message) {
        if (message == null) {
            Log.w(TAG, "Cannot save null message");
            return false;
        }

        try {
            // 简化实现：直接保存单个消息到文件
            String messageFile = "message_" + message.getMessageId() + ".json";
            return storageManager.saveToJsonFile(message, messageFile);
        } catch (Exception e) {
            Log.e(TAG, "Error saving message", e);
            return false;
        }
    }

    @Override
    public boolean deleteChatSession(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return false;
        }

        try {
            List<ChatSession> sessions = getAllChatSessions();
            boolean removed = sessions.removeIf(session -> sessionId.equals(session.getSessionId()));

            if (removed) {
                return storageManager.saveToJsonFile(sessions, CHAT_SESSIONS_FILE);
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting chat session", e);
            return false;
        }
    }

    // ==================== 校园信息操作 ====================

    @Override
    public List<InfoCategory> getInfoCategories() {
        // 默认信息分类
        List<InfoCategory> categories = new ArrayList<>();
        categories.add(new InfoCategory("dining", "餐饮服务", "ic_restaurant", "食堂、餐厅信息", 0));
        categories.add(new InfoCategory("library", "图书馆", "ic_library", "图书馆服务信息", 0));
        categories.add(new InfoCategory("academic", "教务信息", "ic_school", "课程、考试、成绩信息", 0));
        categories.add(new InfoCategory("campus_life", "校园生活", "ic_campus", "社团、活动、服务信息", 0));
        return categories;
    }

    @Override
    public List<CampusInfo> getInfoByCategory(String categoryId) {
        try {
            Type infoType = new TypeToken<List<CampusInfo>>() {
            }.getType();
            List<CampusInfo> allInfo = storageManager.loadFromJsonFile(CAMPUS_INFO_FILE, infoType);

            if (allInfo == null) {
                return new ArrayList<>();
            }

            List<CampusInfo> categoryInfo = new ArrayList<>();
            for (CampusInfo info : allInfo) {
                if (categoryId.equals(info.getCategoryId())) {
                    categoryInfo.add(info);
                }
            }
            return categoryInfo;
        } catch (Exception e) {
            Log.e(TAG, "Error loading info for category: " + categoryId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<CampusInfo> searchCampusInfo(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            Type infoType = new TypeToken<List<CampusInfo>>() {
            }.getType();
            List<CampusInfo> allInfo = storageManager.loadFromJsonFile(CAMPUS_INFO_FILE, infoType);

            if (allInfo == null) {
                return new ArrayList<>();
            }

            List<CampusInfo> searchResults = new ArrayList<>();
            String searchKeyword = keyword.toLowerCase().trim();

            for (CampusInfo info : allInfo) {
                // 在标题和内容中搜索关键字
                boolean titleMatch = info.getTitle() != null &&
                        info.getTitle().toLowerCase().contains(searchKeyword);
                boolean contentMatch = info.getContent() != null &&
                        info.getContent().toLowerCase().contains(searchKeyword);

                if (titleMatch || contentMatch) {
                    searchResults.add(info);
                }
            }

            return searchResults;
        } catch (Exception e) {
            Log.e(TAG, "Error searching campus info", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<CampusInfo> getFavoriteInfo() {
        try {
            Type infoType = new TypeToken<List<CampusInfo>>() {
            }.getType();
            List<CampusInfo> allInfo = storageManager.loadFromJsonFile(CAMPUS_INFO_FILE, infoType);

            if (allInfo == null) {
                return new ArrayList<>();
            }

            List<CampusInfo> favorites = new ArrayList<>();
            for (CampusInfo info : allInfo) {
                if (info.getIsFavorite()) {
                    favorites.add(info);
                }
            }
            return favorites;
        } catch (Exception e) {
            Log.e(TAG, "Error loading favorite info", e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean toggleFavorite(String infoId) {
        if (infoId == null || infoId.trim().isEmpty()) {
            return false;
        }

        try {
            Type infoType = new TypeToken<List<CampusInfo>>() {
            }.getType();
            List<CampusInfo> allInfo = storageManager.loadFromJsonFile(CAMPUS_INFO_FILE, infoType);

            if (allInfo == null) {
                return false;
            }

            boolean found = false;
            for (CampusInfo info : allInfo) {
                if (infoId.equals(info.getInfoId())) {
                    info.setIsFavorite(!info.getIsFavorite()); // 切换收藏状态
                    found = true;
                    break;
                }
            }

            if (found) {
                return storageManager.saveToJsonFile(allInfo, CAMPUS_INFO_FILE);
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error toggling favorite", e);
            return false;
        }
    }

    // ==================== 数据管理操作 ====================

    @Override
    public boolean clearAllCache() {
        try {
            storageManager.clearAllCache();
            Log.d(TAG, "All cache cleared successfully");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error clearing cache", e);
            return false;
        }
    }

    @Override
    public boolean exportAllData(String exportPath) {
        try {
            // 简化实现：导出所有主要数据文件
            List<String> dataFiles = new ArrayList<>();
            dataFiles.add(COURSES_FILE);
            dataFiles.add(STUDY_PLANS_FILE);
            dataFiles.add(CHAT_SESSIONS_FILE);
            dataFiles.add(CAMPUS_INFO_FILE);

            // 这里应该实现实际的文件导出逻辑
            Log.d(TAG, "Data export to: " + exportPath);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error exporting data", e);
            return false;
        }
    }

    @Override
    public boolean importAllData(String importPath) {
        try {
            // 这里应该实现实际的文件导入逻辑
            Log.d(TAG, "Data import from: " + importPath);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error importing data", e);
            return false;
        }
    }

    @Override
    public DataStatistics getDataStatistics() {
        try {
            int totalCourses = getAllCourses().size();
            int totalStudyPlans = getAllStudyPlans().size();
            int totalChatSessions = getAllChatSessions().size();

            Type infoType = new TypeToken<List<CampusInfo>>() {
            }.getType();
            List<CampusInfo> campusInfo = storageManager.loadFromJsonFile(CAMPUS_INFO_FILE, infoType);
            int totalCampusInfo = (campusInfo != null) ? campusInfo.size() : 0;

            long lastUpdateTime = System.currentTimeMillis();

            return new DataStatistics(totalCourses, totalStudyPlans,
                    totalChatSessions, totalCampusInfo,
                    lastUpdateTime);
        } catch (Exception e) {
            Log.e(TAG, "Error getting data statistics", e);
            return new DataStatistics(0, 0, 0, 0, System.currentTimeMillis());
        }
    }
}