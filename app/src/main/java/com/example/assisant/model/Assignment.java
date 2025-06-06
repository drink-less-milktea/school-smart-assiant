package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 作业实体类
 * 用于存储课程作业的相关信息
 */
public class Assignment {

    @SerializedName("assignmentId")
    private String assignmentId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("dueDate")
    private String dueDate;

    @SerializedName("completed")
    private boolean completed;

    /**
     * 默认构造函数
     */
    public Assignment() {
    }

    /**
     * 完整构造函数
     */
    public Assignment(String assignmentId, String title, String description, String dueDate, boolean completed) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    // Getter和Setter方法
    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Assignment that = (Assignment) o;
        return completed == that.completed &&
                Objects.equals(assignmentId, that.assignmentId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, title, description, dueDate, completed);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId='" + assignmentId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", completed=" + completed +
                '}';
    }
}