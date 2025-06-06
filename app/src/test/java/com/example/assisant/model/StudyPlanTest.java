package com.example.assisant.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudyPlan实体类单元测试
 * 测试学习计划数据模型的完整性、序列化/反序列化功能、字段验证等
 */
public class StudyPlanTest {

    private Gson gson;
    private StudyPlan testStudyPlan;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        // 创建测试用的StudyPlan对象
        testStudyPlan = new StudyPlan();
        testStudyPlan.setPlanId("plan_exam_2024_spring");
        testStudyPlan.setTitle("2024春季期末复习计划");
        testStudyPlan.setDescription("针对期末考试的系统复习安排");
        testStudyPlan.setType("EXAM_PREP");
        testStudyPlan.setStartDate("2024-05-01");
        testStudyPlan.setEndDate("2024-06-15");
        testStudyPlan.setStatus("ACTIVE");

        // 创建目标列表
        List<StudyGoal> goals = new ArrayList<>();
        StudyGoal goal = new StudyGoal();
        goal.setGoalId("goal_java_review");
        goal.setTitle("Java课程复习");
        goal.setTargetScore(90);
        goal.setCurrentProgress(45);
        goal.setPriority("HIGH");
        goals.add(goal);
        testStudyPlan.setGoals(goals);

        // 创建任务列表
        List<StudyTask> tasks = new ArrayList<>();
        StudyTask task = new StudyTask();
        task.setTaskId("task_001");
        task.setPlanId("plan_exam_2024_spring");
        task.setTitle("复习Java基础语法");
        task.setDescription("重新学习变量、循环、条件语句");
        task.setPriority("HIGH");
        task.setEstimatedHours(4);
        task.setDueDate("2024-05-05");
        task.setStatus("TODO");

        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("基础");
        tags.add("语法");
        task.setTags(tags);
        task.setRelatedCourseId("cs101_2024_spring");
        tasks.add(task);
        testStudyPlan.setTasks(tasks);

        // 创建统计信息
        StudyStatistics statistics = new StudyStatistics();
        statistics.setTotalTasks(20);
        statistics.setCompletedTasks(8);
        statistics.setTotalHours(80);
        statistics.setSpentHours(32);
        statistics.setAverageRating(4.2f);
        testStudyPlan.setStatistics(statistics);
    }

    @Test
    public void testStudyPlanConstructor() {
        // 测试默认构造函数
        StudyPlan plan = new StudyPlan();
        assertNotNull("StudyPlan对象不应为null", plan);
        assertNull("默认planId应为null", plan.getPlanId());
        assertNull("默认title应为null", plan.getTitle());
        assertNull("默认status应为null", plan.getStatus());
    }

    @Test
    public void testStudyPlanGettersAndSetters() {
        // 测试基本字段的getter和setter
        assertEquals("planId应匹配", "plan_exam_2024_spring", testStudyPlan.getPlanId());
        assertEquals("title应匹配", "2024春季期末复习计划", testStudyPlan.getTitle());
        assertEquals("description应匹配", "针对期末考试的系统复习安排", testStudyPlan.getDescription());
        assertEquals("type应匹配", "EXAM_PREP", testStudyPlan.getType());
        assertEquals("startDate应匹配", "2024-05-01", testStudyPlan.getStartDate());
        assertEquals("endDate应匹配", "2024-06-15", testStudyPlan.getEndDate());
        assertEquals("status应匹配", "ACTIVE", testStudyPlan.getStatus());

        // 测试集合字段
        assertNotNull("goals不应为null", testStudyPlan.getGoals());
        assertEquals("goals数量应为1", 1, testStudyPlan.getGoals().size());
        assertNotNull("tasks不应为null", testStudyPlan.getTasks());
        assertEquals("tasks数量应为1", 1, testStudyPlan.getTasks().size());
        assertNotNull("statistics不应为null", testStudyPlan.getStatistics());
    }

    @Test
    public void testStudyPlanEqualsAndHashCode() {
        // 创建相同的StudyPlan对象
        StudyPlan plan1 = new StudyPlan();
        plan1.setPlanId("plan_exam_2024_spring");
        plan1.setTitle("2024春季期末复习计划");

        StudyPlan plan2 = new StudyPlan();
        plan2.setPlanId("plan_exam_2024_spring");
        plan2.setTitle("2024春季期末复习计划");

        StudyPlan plan3 = new StudyPlan();
        plan3.setPlanId("plan_final_2024_spring");
        plan3.setTitle("2024春季期末考试计划");

        // 测试equals方法
        assertEquals("相同内容的StudyPlan应相等", plan1, plan2);
        assertNotEquals("不同内容的StudyPlan应不相等", plan1, plan3);
        assertNotEquals("StudyPlan与null应不相等", plan1, null);
        assertNotEquals("StudyPlan与其他类型应不相等", plan1, "string");

        // 测试hashCode方法
        assertEquals("相同内容的StudyPlan应有相同hashCode", plan1.hashCode(), plan2.hashCode());
        assertNotEquals("不同内容的StudyPlan应有不同hashCode", plan1.hashCode(), plan3.hashCode());
    }

    @Test
    public void testStudyPlanToString() {
        String planString = testStudyPlan.toString();
        assertNotNull("toString不应返回null", planString);
        assertTrue("toString应包含planId", planString.contains("plan_exam_2024_spring"));
        assertTrue("toString应包含title", planString.contains("2024春季期末复习计划"));
    }

    @Test
    public void testStudyPlanJsonSerialization() {
        // 测试序列化
        String json = gson.toJson(testStudyPlan);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含planId", json.contains("plan_exam_2024_spring"));
        assertTrue("JSON应包含title", json.contains("2024春季期末复习计划"));
        assertTrue("JSON应包含goals数组", json.contains("goals"));
        assertTrue("JSON应包含tasks数组", json.contains("tasks"));
        assertTrue("JSON应包含statistics对象", json.contains("statistics"));
    }

    @Test
    public void testStudyPlanJsonDeserialization() {
        // 先序列化
        String json = gson.toJson(testStudyPlan);

        // 再反序列化
        StudyPlan deserializedPlan = gson.fromJson(json, StudyPlan.class);

        assertNotNull("反序列化结果不应为null", deserializedPlan);
        assertEquals("planId应匹配", testStudyPlan.getPlanId(), deserializedPlan.getPlanId());
        assertEquals("title应匹配", testStudyPlan.getTitle(), deserializedPlan.getTitle());
        assertEquals("description应匹配", testStudyPlan.getDescription(), deserializedPlan.getDescription());
        assertEquals("type应匹配", testStudyPlan.getType(), deserializedPlan.getType());
        assertEquals("startDate应匹配", testStudyPlan.getStartDate(), deserializedPlan.getStartDate());
        assertEquals("endDate应匹配", testStudyPlan.getEndDate(), deserializedPlan.getEndDate());
        assertEquals("status应匹配", testStudyPlan.getStatus(), deserializedPlan.getStatus());

        // 验证集合字段
        assertEquals("goals数量应匹配", testStudyPlan.getGoals().size(), deserializedPlan.getGoals().size());
        assertEquals("tasks数量应匹配", testStudyPlan.getTasks().size(), deserializedPlan.getTasks().size());
        assertNotNull("statistics应不为null", deserializedPlan.getStatistics());
    }

    @Test
    public void testStudyPlanFieldValidation() {
        StudyPlan plan = new StudyPlan();

        // 测试必填字段验证
        assertFalse("没有planId的StudyPlan应无效", isValidStudyPlan(plan));

        plan.setPlanId("plan_001");
        assertFalse("没有title的StudyPlan应无效", isValidStudyPlan(plan));

        plan.setTitle("测试计划");
        assertFalse("没有startDate的StudyPlan应无效", isValidStudyPlan(plan));

        plan.setStartDate("2024-05-01");
        assertFalse("没有endDate的StudyPlan应无效", isValidStudyPlan(plan));

        plan.setEndDate("2024-04-30"); // 结束日期早于开始日期
        assertFalse("结束日期早于开始日期的StudyPlan应无效", isValidStudyPlan(plan));

        plan.setEndDate("2024-06-15");
        assertTrue("正常的StudyPlan应有效", isValidStudyPlan(plan));
    }

    @Test
    public void testStudyGoalValidation() {
        StudyGoal goal = new StudyGoal();

        // 测试目标字段验证
        assertFalse("没有goalId的StudyGoal应无效", isValidStudyGoal(goal));

        goal.setGoalId("goal_001");
        assertFalse("没有title的StudyGoal应无效", isValidStudyGoal(goal));

        goal.setTitle("学习目标");
        goal.setTargetScore(-10); // 负数目标分数
        assertFalse("负数目标分数的StudyGoal应无效", isValidStudyGoal(goal));

        goal.setTargetScore(110); // 超过100的目标分数
        assertFalse("超过100的目标分数的StudyGoal应无效", isValidStudyGoal(goal));

        goal.setTargetScore(90);
        goal.setCurrentProgress(-5); // 负数当前进度
        assertFalse("负数当前进度的StudyGoal应无效", isValidStudyGoal(goal));

        goal.setCurrentProgress(45);
        assertTrue("正常的StudyGoal应有效", isValidStudyGoal(goal));
    }

    @Test
    public void testStudyTaskValidation() {
        StudyTask task = new StudyTask();

        // 测试任务字段验证
        assertFalse("没有taskId的StudyTask应无效", isValidStudyTask(task));

        task.setTaskId("task_001");
        assertFalse("没有title的StudyTask应无效", isValidStudyTask(task));

        task.setTitle("学习任务");
        task.setEstimatedHours(-2); // 负数预估时间
        assertFalse("负数预估时间的StudyTask应无效", isValidStudyTask(task));

        task.setEstimatedHours(4);
        assertFalse("没有dueDate的StudyTask应无效", isValidStudyTask(task));

        task.setDueDate("invalid-date");
        assertFalse("无效日期格式的StudyTask应无效", isValidStudyTask(task));

        task.setDueDate("2024-05-05");
        assertTrue("正常的StudyTask应有效", isValidStudyTask(task));
    }

    @Test
    public void testStudyStatisticsValidation() {
        StudyStatistics statistics = new StudyStatistics();

        // 测试统计信息验证
        statistics.setTotalTasks(-5); // 负数总任务数
        assertFalse("负数总任务数的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setTotalTasks(20);
        statistics.setCompletedTasks(-3); // 负数已完成任务数
        assertFalse("负数已完成任务数的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setCompletedTasks(25); // 已完成任务数大于总任务数
        assertFalse("已完成任务数大于总任务数的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setCompletedTasks(8);
        statistics.setTotalHours(-10); // 负数总时间
        assertFalse("负数总时间的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setTotalHours(80);
        statistics.setSpentHours(-5); // 负数已花费时间
        assertFalse("负数已花费时间的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setSpentHours(90); // 已花费时间大于总时间
        assertFalse("已花费时间大于总时间的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setSpentHours(32);
        statistics.setAverageRating(-1.0f); // 负数平均评分
        assertFalse("负数平均评分的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setAverageRating(6.0f); // 超过5的平均评分
        assertFalse("超过5的平均评分的StudyStatistics应无效", isValidStudyStatistics(statistics));

        statistics.setAverageRating(4.2f);
        assertTrue("正常的StudyStatistics应有效", isValidStudyStatistics(statistics));
    }

    @Test
    public void testStudyPlanStatusTransition() {
        StudyPlan plan = new StudyPlan();
        plan.setPlanId("plan_001");
        plan.setTitle("测试计划");
        plan.setStartDate("2024-05-01");
        plan.setEndDate("2024-06-15");

        // 测试状态转换
        plan.setStatus("DRAFT");
        assertTrue("DRAFT状态应有效", isValidStatus(plan.getStatus()));

        plan.setStatus("ACTIVE");
        assertTrue("ACTIVE状态应有效", isValidStatus(plan.getStatus()));

        plan.setStatus("PAUSED");
        assertTrue("PAUSED状态应有效", isValidStatus(plan.getStatus()));

        plan.setStatus("COMPLETED");
        assertTrue("COMPLETED状态应有效", isValidStatus(plan.getStatus()));

        plan.setStatus("CANCELLED");
        assertTrue("CANCELLED状态应有效", isValidStatus(plan.getStatus()));

        plan.setStatus("INVALID_STATUS");
        assertFalse("无效状态应不被接受", isValidStatus(plan.getStatus()));
    }

    // 辅助验证方法
    private boolean isValidStudyPlan(StudyPlan plan) {
        if (plan.getPlanId() == null || plan.getPlanId().trim().isEmpty()) {
            return false;
        }
        if (plan.getTitle() == null || plan.getTitle().trim().isEmpty()) {
            return false;
        }
        if (plan.getStartDate() == null || plan.getStartDate().trim().isEmpty()) {
            return false;
        }
        if (plan.getEndDate() == null || plan.getEndDate().trim().isEmpty()) {
            return false;
        }
        // 简单的日期比较（假设日期格式为yyyy-MM-dd）
        if (plan.getStartDate().compareTo(plan.getEndDate()) > 0) {
            return false;
        }
        return true;
    }

    private boolean isValidStudyGoal(StudyGoal goal) {
        if (goal.getGoalId() == null || goal.getGoalId().trim().isEmpty()) {
            return false;
        }
        if (goal.getTitle() == null || goal.getTitle().trim().isEmpty()) {
            return false;
        }
        if (goal.getTargetScore() < 0 || goal.getTargetScore() > 100) {
            return false;
        }
        if (goal.getCurrentProgress() < 0) {
            return false;
        }
        return true;
    }

    private boolean isValidStudyTask(StudyTask task) {
        if (task.getTaskId() == null || task.getTaskId().trim().isEmpty()) {
            return false;
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return false;
        }
        if (task.getEstimatedHours() < 0) {
            return false;
        }
        if (task.getDueDate() == null || task.getDueDate().trim().isEmpty()) {
            return false;
        }
        // 简单的日期格式验证
        if (!task.getDueDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        return true;
    }

    private boolean isValidStudyStatistics(StudyStatistics statistics) {
        if (statistics.getTotalTasks() < 0) {
            return false;
        }
        if (statistics.getCompletedTasks() < 0) {
            return false;
        }
        if (statistics.getCompletedTasks() > statistics.getTotalTasks()) {
            return false;
        }
        if (statistics.getTotalHours() < 0) {
            return false;
        }
        if (statistics.getSpentHours() < 0) {
            return false;
        }
        if (statistics.getSpentHours() > statistics.getTotalHours()) {
            return false;
        }
        if (statistics.getAverageRating() < 0 || statistics.getAverageRating() > 5) {
            return false;
        }
        return true;
    }

    private boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }
        return status.equals("DRAFT") || status.equals("ACTIVE") ||
                status.equals("PAUSED") || status.equals("COMPLETED") ||
                status.equals("CANCELLED");
    }
}