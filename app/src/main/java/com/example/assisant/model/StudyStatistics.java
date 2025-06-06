package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 学习统计实体类
 * 用于存储学习计划的统计数据
 */
public class StudyStatistics {

    @SerializedName("totalTasks")
    private int totalTasks;

    @SerializedName("completedTasks")
    private int completedTasks;

    @SerializedName("totalHours")
    private int totalHours;

    @SerializedName("spentHours")
    private int spentHours;

    @SerializedName("averageRating")
    private float averageRating;

    /**
     * 默认构造函数
     */
    public StudyStatistics() {
    }

    /**
     * 完整构造函数
     */
    public StudyStatistics(int totalTasks, int completedTasks, int totalHours, int spentHours, float averageRating) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.totalHours = totalHours;
        this.spentHours = spentHours;
        this.averageRating = averageRating;
    }

    // Getter和Setter方法
    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getSpentHours() {
        return spentHours;
    }

    public void setSpentHours(int spentHours) {
        this.spentHours = spentHours;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudyStatistics that = (StudyStatistics) o;
        return totalTasks == that.totalTasks &&
                completedTasks == that.completedTasks &&
                totalHours == that.totalHours &&
                spentHours == that.spentHours &&
                Float.compare(that.averageRating, averageRating) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTasks, completedTasks, totalHours, spentHours, averageRating);
    }

    @Override
    public String toString() {
        return "StudyStatistics{" +
                "totalTasks=" + totalTasks +
                ", completedTasks=" + completedTasks +
                ", totalHours=" + totalHours +
                ", spentHours=" + spentHours +
                ", averageRating=" + averageRating +
                '}';
    }
}