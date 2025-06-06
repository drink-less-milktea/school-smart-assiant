package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * 课程信息实体类
 * 用于存储课程的基本信息、时间安排、作业和考试等数据
 */
public class Course {

    @SerializedName("courseId")
    private String courseId;

    @SerializedName("courseName")
    private String courseName;

    @SerializedName("courseCode")
    private String courseCode;

    @SerializedName("instructor")
    private String instructor;

    @SerializedName("credits")
    private int credits;

    @SerializedName("classroom")
    private String classroom;

    @SerializedName("semester")
    private String semester;

    @SerializedName("description")
    private String description;

    @SerializedName("times")
    private List<CourseTime> times;

    @SerializedName("assignments")
    private List<Assignment> assignments;

    @SerializedName("exams")
    private List<Exam> exams;

    /**
     * 默认构造函数
     */
    public Course() {
    }

    /**
     * 完整构造函数
     */
    public Course(String courseId, String courseName, String courseCode, String instructor,
            int credits, String classroom, String semester, String description,
            List<CourseTime> times, List<Assignment> assignments, List<Exam> exams) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructor = instructor;
        this.credits = credits;
        this.classroom = classroom;
        this.semester = semester;
        this.description = description;
        this.times = times;
        this.assignments = assignments;
        this.exams = exams;
    }

    // Getter和Setter方法
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CourseTime> getTimes() {
        return times;
    }

    public void setTimes(List<CourseTime> times) {
        this.times = times;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Course course = (Course) o;
        return credits == course.credits &&
                Objects.equals(courseId, course.courseId) &&
                Objects.equals(courseName, course.courseName) &&
                Objects.equals(courseCode, course.courseCode) &&
                Objects.equals(instructor, course.instructor) &&
                Objects.equals(classroom, course.classroom) &&
                Objects.equals(semester, course.semester) &&
                Objects.equals(description, course.description) &&
                Objects.equals(times, course.times) &&
                Objects.equals(assignments, course.assignments) &&
                Objects.equals(exams, course.exams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseCode, instructor, credits,
                classroom, semester, description, times, assignments, exams);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", instructor='" + instructor + '\'' +
                ", credits=" + credits +
                ", classroom='" + classroom + '\'' +
                ", semester='" + semester + '\'' +
                ", description='" + description + '\'' +
                ", times=" + times +
                ", assignments=" + assignments +
                ", exams=" + exams +
                '}';
    }
}