package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * 学习任务实体类
 * 用于存储学习计划中的具体任务信息
 */
public class StudyTask {

    @SerializedName("taskId")
    private String taskId;

    @SerializedName("planId")
    private String planId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("priority")
    private String priority;

    @SerializedName("estimatedHours")
    private int estimatedHours;

    @SerializedName("dueDate")
    private String dueDate;

    @SerializedName("status")
    private String status;

    @SerializedName("tags")
    private List<String> tags;

    @SerializedName("relatedCourseId")
    private String relatedCourseId;

    /**
     * 默认构造函数
     */
    public StudyTask() {
    }

    /**
     * 完整构造函数
     */
    public StudyTask(String taskId, String planId, String title, String description,
            String priority, int estimatedHours, String dueDate, String status,
            List<String> tags, String relatedCourseId) {
        this.taskId = taskId;
        this.planId = planId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.dueDate = dueDate;
        this.status = status;
        this.tags = tags;
        this.relatedCourseId = relatedCourseId;
    }

    // Getter和Setter方法
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getRelatedCourseId() {
        return relatedCourseId;
    }

    public void setRelatedCourseId(String relatedCourseId) {
        this.relatedCourseId = relatedCourseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudyTask studyTask = (StudyTask) o;
        return estimatedHours == studyTask.estimatedHours &&
                Objects.equals(taskId, studyTask.taskId) &&
                Objects.equals(planId, studyTask.planId) &&
                Objects.equals(title, studyTask.title) &&
                Objects.equals(description, studyTask.description) &&
                Objects.equals(priority, studyTask.priority) &&
                Objects.equals(dueDate, studyTask.dueDate) &&
                Objects.equals(status, studyTask.status) &&
                Objects.equals(tags, studyTask.tags) &&
                Objects.equals(relatedCourseId, studyTask.relatedCourseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, planId, title, description, priority,
                estimatedHours, dueDate, status, tags, relatedCourseId);
    }

    @Override
    public String toString() {
        return "StudyTask{" +
                "taskId='" + taskId + '\'' +
                ", planId='" + planId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", estimatedHours=" + estimatedHours +
                ", dueDate='" + dueDate + '\'' +
                ", status='" + status + '\'' +
                ", tags=" + tags +
                ", relatedCourseId='" + relatedCourseId + '\'' +
                '}';
    }
}