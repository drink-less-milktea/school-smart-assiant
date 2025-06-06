package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 学习目标实体类
 * 用于存储学习计划中的具体目标信息
 */
public class StudyGoal {

    @SerializedName("goalId")
    private String goalId;

    @SerializedName("title")
    private String title;

    @SerializedName("targetScore")
    private int targetScore;

    @SerializedName("currentProgress")
    private int currentProgress;

    @SerializedName("priority")
    private String priority;

    /**
     * 默认构造函数
     */
    public StudyGoal() {
    }

    /**
     * 完整构造函数
     */
    public StudyGoal(String goalId, String title, int targetScore, int currentProgress, String priority) {
        this.goalId = goalId;
        this.title = title;
        this.targetScore = targetScore;
        this.currentProgress = currentProgress;
        this.priority = priority;
    }

    // Getter和Setter方法
    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudyGoal studyGoal = (StudyGoal) o;
        return targetScore == studyGoal.targetScore &&
                currentProgress == studyGoal.currentProgress &&
                Objects.equals(goalId, studyGoal.goalId) &&
                Objects.equals(title, studyGoal.title) &&
                Objects.equals(priority, studyGoal.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalId, title, targetScore, currentProgress, priority);
    }

    @Override
    public String toString() {
        return "StudyGoal{" +
                "goalId='" + goalId + '\'' +
                ", title='" + title + '\'' +
                ", targetScore=" + targetScore +
                ", currentProgress=" + currentProgress +
                ", priority='" + priority + '\'' +
                '}';
    }
}