package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * 学习计划实体类
 * 用于存储学习计划的基本信息、目标、任务和统计数据
 */
public class StudyPlan {

    @SerializedName("planId")
    private String planId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("status")
    private String status;

    @SerializedName("goals")
    private List<StudyGoal> goals;

    @SerializedName("tasks")
    private List<StudyTask> tasks;

    @SerializedName("statistics")
    private StudyStatistics statistics;

    /**
     * 默认构造函数
     */
    public StudyPlan() {
    }

    /**
     * 完整构造函数
     */
    public StudyPlan(String planId, String title, String description, String type,
            String startDate, String endDate, String status,
            List<StudyGoal> goals, List<StudyTask> tasks, StudyStatistics statistics) {
        this.planId = planId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.goals = goals;
        this.tasks = tasks;
        this.statistics = statistics;
    }

    // Getter和Setter方法
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StudyGoal> getGoals() {
        return goals;
    }

    public void setGoals(List<StudyGoal> goals) {
        this.goals = goals;
    }

    public List<StudyTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<StudyTask> tasks) {
        this.tasks = tasks;
    }

    public StudyStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(StudyStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudyPlan studyPlan = (StudyPlan) o;
        return Objects.equals(planId, studyPlan.planId) &&
                Objects.equals(title, studyPlan.title) &&
                Objects.equals(description, studyPlan.description) &&
                Objects.equals(type, studyPlan.type) &&
                Objects.equals(startDate, studyPlan.startDate) &&
                Objects.equals(endDate, studyPlan.endDate) &&
                Objects.equals(status, studyPlan.status) &&
                Objects.equals(goals, studyPlan.goals) &&
                Objects.equals(tasks, studyPlan.tasks) &&
                Objects.equals(statistics, studyPlan.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, title, description, type, startDate, endDate,
                status, goals, tasks, statistics);
    }

    @Override
    public String toString() {
        return "StudyPlan{" +
                "planId='" + planId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", goals=" + goals +
                ", tasks=" + tasks +
                ", statistics=" + statistics +
                '}';
    }
}