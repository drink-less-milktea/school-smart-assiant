package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 课程时间实体类
 * 用于存储课程的具体时间安排信息
 */
public class CourseTime {

    @SerializedName("dayOfWeek")
    private String dayOfWeek;

    @SerializedName("startTime")
    private int startTime;

    @SerializedName("endTime")
    private int endTime;

    @SerializedName("weekStart")
    private int weekStart;

    @SerializedName("weekEnd")
    private int weekEnd;

    /**
     * 默认构造函数
     */
    public CourseTime() {
    }

    /**
     * 完整构造函数
     */
    public CourseTime(String dayOfWeek, int startTime, int endTime, int weekStart, int weekEnd) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
    }

    // Getter和Setter方法
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(int weekStart) {
        this.weekStart = weekStart;
    }

    public int getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(int weekEnd) {
        this.weekEnd = weekEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseTime that = (CourseTime) o;
        return startTime == that.startTime &&
                endTime == that.endTime &&
                weekStart == that.weekStart &&
                weekEnd == that.weekEnd &&
                Objects.equals(dayOfWeek, that.dayOfWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, startTime, endTime, weekStart, weekEnd);
    }

    @Override
    public String toString() {
        return "CourseTime{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", weekStart=" + weekStart +
                ", weekEnd=" + weekEnd +
                '}';
    }
}