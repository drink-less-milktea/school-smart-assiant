package com.example.assisant.model.repository;

import com.example.assisant.model.*;

import java.util.List;

/**
 * 数据仓库接口
 * 
 * 提供统一的数据访问抽象层，隐藏具体的数据源实现细节。
 * 支持多种数据操作，包括CRUD、搜索、收藏等功能。
 * 
 * 设计原则：
 * - 单一职责：专注于数据访问逻辑
 * - 依赖倒置：依赖抽象而非具体实现
 * - 开闭原则：对扩展开放，对修改封闭
 * 
 * @author Campus AI Assistant Team
 * @version 1.0
 */
public interface DataRepository {

    // ==================== 课程数据操作 ====================

    /**
     * 获取所有课程
     * 
     * @return 课程列表，如果没有数据则返回空列表
     */
    List<Course> getAllCourses();

    /**
     * 根据课程ID获取课程
     * 
     * @param courseId 课程ID
     * @return 找到的课程，如果不存在则返回null
     */
    Course getCourseById(String courseId);

    /**
     * 保存课程（新增或更新）
     * 
     * @param course 要保存的课程对象
     * @return 保存是否成功
     */
    boolean saveCourse(Course course);

    /**
     * 删除课程
     * 
     * @param courseId 要删除的课程ID
     * @return 删除是否成功
     */
    boolean deleteCourse(String courseId);

    /**
     * 根据课程ID获取成绩列表
     * 
     * @param courseId 课程ID
     * @return 成绩列表
     */
    List<Grade> getGradesByCourse(String courseId);

    // ==================== 学习计划操作 ====================

    /**
     * 获取所有学习计划
     * 
     * @return 学习计划列表
     */
    List<StudyPlan> getAllStudyPlans();

    /**
     * 根据计划ID获取学习计划
     * 
     * @param planId 计划ID
     * @return 找到的学习计划，如果不存在则返回null
     */
    StudyPlan getStudyPlanById(String planId);

    /**
     * 保存学习计划（新增或更新）
     * 
     * @param plan 要保存的学习计划对象
     * @return 保存是否成功
     */
    boolean saveStudyPlan(StudyPlan plan);

    /**
     * 删除学习计划
     * 
     * @param planId 要删除的计划ID
     * @return 删除是否成功
     */
    boolean deleteStudyPlan(String planId);

    /**
     * 根据计划ID获取任务列表
     * 
     * @param planId 计划ID
     * @return 任务列表
     */
    List<StudyTask> getTasksByPlan(String planId);

    // ==================== 聊天记录操作 ====================

    /**
     * 获取所有聊天会话
     * 
     * @return 聊天会话列表
     */
    List<ChatSession> getAllChatSessions();

    /**
     * 根据会话ID获取聊天会话
     * 
     * @param sessionId 会话ID
     * @return 找到的聊天会话，如果不存在则返回null
     */
    ChatSession getChatSessionById(String sessionId);

    /**
     * 保存聊天会话（新增或更新）
     * 
     * @param session 要保存的聊天会话对象
     * @return 保存是否成功
     */
    boolean saveChatSession(ChatSession session);

    /**
     * 保存消息
     * 
     * @param message 要保存的消息对象
     * @return 保存是否成功
     */
    boolean saveMessage(Message message);

    /**
     * 删除聊天会话
     * 
     * @param sessionId 要删除的会话ID
     * @return 删除是否成功
     */
    boolean deleteChatSession(String sessionId);

    // ==================== 校园信息操作 ====================

    /**
     * 获取信息分类列表
     * 
     * @return 信息分类列表
     */
    List<InfoCategory> getInfoCategories();

    /**
     * 根据分类ID获取校园信息
     * 
     * @param categoryId 分类ID
     * @return 校园信息列表
     */
    List<CampusInfo> getInfoByCategory(String categoryId);

    /**
     * 搜索校园信息
     * 
     * @param keyword 搜索关键字
     * @return 匹配的校园信息列表
     */
    List<CampusInfo> searchCampusInfo(String keyword);

    /**
     * 获取收藏的校园信息
     * 
     * @return 收藏的校园信息列表
     */
    List<CampusInfo> getFavoriteInfo();

    /**
     * 切换收藏状态
     * 
     * @param infoId 信息ID
     * @return 操作是否成功
     */
    boolean toggleFavorite(String infoId);

    // ==================== 数据管理操作 ====================

    /**
     * 清除所有缓存数据
     * 
     * @return 清除是否成功
     */
    boolean clearAllCache();

    /**
     * 导出所有数据
     * 
     * @param exportPath 导出路径
     * @return 导出是否成功
     */
    boolean exportAllData(String exportPath);

    /**
     * 导入数据
     * 
     * @param importPath 导入路径
     * @return 导入是否成功
     */
    boolean importAllData(String importPath);

    /**
     * 获取数据统计信息
     * 
     * @return 数据统计对象
     */
    DataStatistics getDataStatistics();

    /**
     * 数据统计信息类
     */
    class DataStatistics {
        private int totalCourses;
        private int totalStudyPlans;
        private int totalChatSessions;
        private int totalCampusInfo;
        private long lastUpdateTime;

        // 构造函数
        public DataStatistics(int totalCourses, int totalStudyPlans,
                int totalChatSessions, int totalCampusInfo,
                long lastUpdateTime) {
            this.totalCourses = totalCourses;
            this.totalStudyPlans = totalStudyPlans;
            this.totalChatSessions = totalChatSessions;
            this.totalCampusInfo = totalCampusInfo;
            this.lastUpdateTime = lastUpdateTime;
        }

        // Getter方法
        public int getTotalCourses() {
            return totalCourses;
        }

        public int getTotalStudyPlans() {
            return totalStudyPlans;
        }

        public int getTotalChatSessions() {
            return totalChatSessions;
        }

        public int getTotalCampusInfo() {
            return totalCampusInfo;
        }

        public long getLastUpdateTime() {
            return lastUpdateTime;
        }

        // Setter方法
        public void setTotalCourses(int totalCourses) {
            this.totalCourses = totalCourses;
        }

        public void setTotalStudyPlans(int totalStudyPlans) {
            this.totalStudyPlans = totalStudyPlans;
        }

        public void setTotalChatSessions(int totalChatSessions) {
            this.totalChatSessions = totalChatSessions;
        }

        public void setTotalCampusInfo(int totalCampusInfo) {
            this.totalCampusInfo = totalCampusInfo;
        }

        public void setLastUpdateTime(long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }
    }

    /**
     * 信息分类类
     */
    class InfoCategory {
        private String categoryId;
        private String categoryName;
        private String iconResource;
        private String description;
        private int infoCount;

        // 构造函数
        public InfoCategory(String categoryId, String categoryName,
                String iconResource, String description, int infoCount) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.iconResource = iconResource;
            this.description = description;
            this.infoCount = infoCount;
        }

        // Getter方法
        public String getCategoryId() {
            return categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getIconResource() {
            return iconResource;
        }

        public String getDescription() {
            return description;
        }

        public int getInfoCount() {
            return infoCount;
        }

        // Setter方法
        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public void setIconResource(String iconResource) {
            this.iconResource = iconResource;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setInfoCount(int infoCount) {
            this.infoCount = infoCount;
        }
    }

    /**
     * 成绩类
     */
    class Grade {
        private String gradeId;
        private String courseId;
        private String assignmentName;
        private double score;
        private double totalScore;
        private String type;
        private String recordTime;
        private String feedback;

        // 构造函数
        public Grade(String gradeId, String courseId, String assignmentName,
                double score, double totalScore, String type,
                String recordTime, String feedback) {
            this.gradeId = gradeId;
            this.courseId = courseId;
            this.assignmentName = assignmentName;
            this.score = score;
            this.totalScore = totalScore;
            this.type = type;
            this.recordTime = recordTime;
            this.feedback = feedback;
        }

        // Getter方法
        public String getGradeId() {
            return gradeId;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getAssignmentName() {
            return assignmentName;
        }

        public double getScore() {
            return score;
        }

        public double getTotalScore() {
            return totalScore;
        }

        public String getType() {
            return type;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public String getFeedback() {
            return feedback;
        }

        // Setter方法
        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public void setAssignmentName(String assignmentName) {
            this.assignmentName = assignmentName;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public void setTotalScore(double totalScore) {
            this.totalScore = totalScore;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }
}