package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 考试实体类
 * 用于存储课程考试的相关信息
 */
public class Exam {

    @SerializedName("examId")
    private String examId;

    @SerializedName("title")
    private String title;

    @SerializedName("examDate")
    private String examDate;

    @SerializedName("location")
    private String location;

    @SerializedName("duration")
    private int duration;

    /**
     * 默认构造函数
     */
    public Exam() {
    }

    /**
     * 完整构造函数
     */
    public Exam(String examId, String title, String examDate, String location, int duration) {
        this.examId = examId;
        this.title = title;
        this.examDate = examDate;
        this.location = location;
        this.duration = duration;
    }

    // Getter和Setter方法
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Exam exam = (Exam) o;
        return duration == exam.duration &&
                Objects.equals(examId, exam.examId) &&
                Objects.equals(title, exam.title) &&
                Objects.equals(examDate, exam.examDate) &&
                Objects.equals(location, exam.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, title, examDate, location, duration);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId='" + examId + '\'' +
                ", title='" + title + '\'' +
                ", examDate='" + examDate + '\'' +
                ", location='" + location + '\'' +
                ", duration=" + duration +
                '}';
    }
}