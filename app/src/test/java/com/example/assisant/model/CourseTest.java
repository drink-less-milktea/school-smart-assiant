package com.example.assisant.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Course实体类单元测试
 * 测试数据模型的完整性、序列化/反序列化功能、字段验证等
 */
public class CourseTest {

    private Gson gson;
    private Course testCourse;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        // 创建测试用的Course对象
        testCourse = new Course();
        testCourse.setCourseId("cs101_2024_spring");
        testCourse.setCourseName("Java程序设计");
        testCourse.setCourseCode("CS101");
        testCourse.setInstructor("张教授");
        testCourse.setCredits(3);
        testCourse.setClassroom("计算机楼101");
        testCourse.setSemester("2024-Spring");
        testCourse.setDescription("Java编程基础课程");

        // 创建课程时间
        List<CourseTime> times = new ArrayList<>();
        CourseTime time1 = new CourseTime();
        time1.setDayOfWeek("MONDAY");
        time1.setStartTime(800);
        time1.setEndTime(950);
        time1.setWeekStart(1);
        time1.setWeekEnd(16);
        times.add(time1);

        CourseTime time2 = new CourseTime();
        time2.setDayOfWeek("WEDNESDAY");
        time2.setStartTime(1400);
        time2.setEndTime(1550);
        time2.setWeekStart(1);
        time2.setWeekEnd(16);
        times.add(time2);

        testCourse.setTimes(times);

        // 创建作业列表
        List<Assignment> assignments = new ArrayList<>();
        Assignment assignment = new Assignment();
        assignment.setAssignmentId("hw1_cs101");
        assignment.setTitle("第一次作业");
        assignment.setDescription("基础语法练习");
        assignment.setDueDate("2024-03-15");
        assignment.setCompleted(false);
        assignments.add(assignment);
        testCourse.setAssignments(assignments);

        // 创建考试列表
        List<Exam> exams = new ArrayList<>();
        Exam exam = new Exam();
        exam.setExamId("midterm_cs101");
        exam.setTitle("期中考试");
        exam.setExamDate("2024-04-20");
        exam.setLocation("A楼201");
        exam.setDuration(120);
        exams.add(exam);
        testCourse.setExams(exams);
    }

    @Test
    public void testCourseConstructor() {
        // 测试默认构造函数
        Course course = new Course();
        assertNotNull("Course对象不应为null", course);
        assertNull("默认courseId应为null", course.getCourseId());
        assertNull("默认courseName应为null", course.getCourseName());
        assertEquals("默认credits应为0", 0, course.getCredits());
    }

    @Test
    public void testCourseGettersAndSetters() {
        // 测试基本字段的getter和setter
        assertEquals("courseId应匹配", "cs101_2024_spring", testCourse.getCourseId());
        assertEquals("courseName应匹配", "Java程序设计", testCourse.getCourseName());
        assertEquals("courseCode应匹配", "CS101", testCourse.getCourseCode());
        assertEquals("instructor应匹配", "张教授", testCourse.getInstructor());
        assertEquals("credits应匹配", 3, testCourse.getCredits());
        assertEquals("classroom应匹配", "计算机楼101", testCourse.getClassroom());
        assertEquals("semester应匹配", "2024-Spring", testCourse.getSemester());
        assertEquals("description应匹配", "Java编程基础课程", testCourse.getDescription());

        // 测试集合字段
        assertNotNull("times不应为null", testCourse.getTimes());
        assertEquals("times数量应为2", 2, testCourse.getTimes().size());
        assertNotNull("assignments不应为null", testCourse.getAssignments());
        assertEquals("assignments数量应为1", 1, testCourse.getAssignments().size());
        assertNotNull("exams不应为null", testCourse.getExams());
        assertEquals("exams数量应为1", 1, testCourse.getExams().size());
    }

    @Test
    public void testCourseEqualsAndHashCode() {
        // 创建相同的Course对象
        Course course1 = new Course();
        course1.setCourseId("cs101_2024_spring");
        course1.setCourseName("Java程序设计");

        Course course2 = new Course();
        course2.setCourseId("cs101_2024_spring");
        course2.setCourseName("Java程序设计");

        Course course3 = new Course();
        course3.setCourseId("cs102_2024_spring");
        course3.setCourseName("数据结构");

        // 测试equals方法
        assertEquals("相同内容的Course应相等", course1, course2);
        assertNotEquals("不同内容的Course应不相等", course1, course3);
        assertNotEquals("Course与null应不相等", course1, null);
        assertNotEquals("Course与其他类型应不相等", course1, "string");

        // 测试hashCode方法
        assertEquals("相同内容的Course应有相同hashCode", course1.hashCode(), course2.hashCode());
        assertNotEquals("不同内容的Course应有不同hashCode", course1.hashCode(), course3.hashCode());
    }

    @Test
    public void testCourseToString() {
        String courseString = testCourse.toString();
        assertNotNull("toString不应返回null", courseString);
        assertTrue("toString应包含courseId", courseString.contains("cs101_2024_spring"));
        assertTrue("toString应包含courseName", courseString.contains("Java程序设计"));
    }

    @Test
    public void testCourseJsonSerialization() {
        // 测试序列化
        String json = gson.toJson(testCourse);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含courseId", json.contains("cs101_2024_spring"));
        assertTrue("JSON应包含courseName", json.contains("Java程序设计"));
        assertTrue("JSON应包含times数组", json.contains("times"));
        assertTrue("JSON应包含assignments数组", json.contains("assignments"));
        assertTrue("JSON应包含exams数组", json.contains("exams"));
    }

    @Test
    public void testCourseJsonDeserialization() {
        // 先序列化
        String json = gson.toJson(testCourse);

        // 再反序列化
        Course deserializedCourse = gson.fromJson(json, Course.class);

        assertNotNull("反序列化结果不应为null", deserializedCourse);
        assertEquals("courseId应匹配", testCourse.getCourseId(), deserializedCourse.getCourseId());
        assertEquals("courseName应匹配", testCourse.getCourseName(), deserializedCourse.getCourseName());
        assertEquals("courseCode应匹配", testCourse.getCourseCode(), deserializedCourse.getCourseCode());
        assertEquals("instructor应匹配", testCourse.getInstructor(), deserializedCourse.getInstructor());
        assertEquals("credits应匹配", testCourse.getCredits(), deserializedCourse.getCredits());
        assertEquals("classroom应匹配", testCourse.getClassroom(), deserializedCourse.getClassroom());
        assertEquals("semester应匹配", testCourse.getSemester(), deserializedCourse.getSemester());
        assertEquals("description应匹配", testCourse.getDescription(), deserializedCourse.getDescription());

        // 验证集合字段
        assertEquals("times数量应匹配", testCourse.getTimes().size(), deserializedCourse.getTimes().size());
        assertEquals("assignments数量应匹配", testCourse.getAssignments().size(),
                deserializedCourse.getAssignments().size());
        assertEquals("exams数量应匹配", testCourse.getExams().size(), deserializedCourse.getExams().size());
    }

    @Test
    public void testCourseFieldValidation() {
        Course course = new Course();

        // 测试必填字段验证
        assertFalse("没有courseId的Course应无效", isValidCourse(course));

        course.setCourseId("cs101");
        assertFalse("没有courseName的Course应无效", isValidCourse(course));

        course.setCourseName("Java程序设计");
        assertFalse("没有有效学分的Course应无效", isValidCourse(course));

        course.setCredits(3); // 设置有效学分
        assertTrue("有courseId、courseName和有效学分的Course应有效", isValidCourse(course));

        // 测试边界条件
        course.setCredits(-1);
        assertFalse("负数学分的Course应无效", isValidCourse(course));

        course.setCredits(0);
        assertFalse("0学分的Course应无效", isValidCourse(course));

        course.setCredits(10);
        assertFalse("超过8学分的Course应无效", isValidCourse(course));

        course.setCredits(3);
        assertTrue("正常学分的Course应有效", isValidCourse(course));
    }

    @Test
    public void testCourseTimeValidation() {
        CourseTime time = new CourseTime();

        // 测试时间字段验证
        assertFalse("没有dayOfWeek的CourseTime应无效", isValidCourseTime(time));

        time.setDayOfWeek("MONDAY");
        assertFalse("没有startTime的CourseTime应无效", isValidCourseTime(time));

        time.setStartTime(800);
        time.setEndTime(750); // 结束时间早于开始时间
        assertFalse("结束时间早于开始时间的CourseTime应无效", isValidCourseTime(time));

        time.setEndTime(950);
        time.setWeekStart(5);
        time.setWeekEnd(3); // 结束周早于开始周
        assertFalse("结束周早于开始周的CourseTime应无效", isValidCourseTime(time));

        time.setWeekStart(1);
        time.setWeekEnd(16);
        assertTrue("正常的CourseTime应有效", isValidCourseTime(time));
    }

    @Test
    public void testAssignmentValidation() {
        Assignment assignment = new Assignment();

        // 测试作业字段验证
        assertFalse("没有assignmentId的Assignment应无效", isValidAssignment(assignment));

        assignment.setAssignmentId("hw1");
        assertFalse("没有title的Assignment应无效", isValidAssignment(assignment));

        assignment.setTitle("第一次作业");
        assertFalse("没有dueDate的Assignment应无效", isValidAssignment(assignment));

        assignment.setDueDate("invalid-date");
        assertFalse("无效日期格式的Assignment应无效", isValidAssignment(assignment));

        assignment.setDueDate("2024-03-15");
        assertTrue("正常的Assignment应有效", isValidAssignment(assignment));
    }

    @Test
    public void testExamValidation() {
        Exam exam = new Exam();

        // 测试考试字段验证
        assertFalse("没有examId的Exam应无效", isValidExam(exam));

        exam.setExamId("midterm");
        assertFalse("没有title的Exam应无效", isValidExam(exam));

        exam.setTitle("期中考试");
        assertFalse("没有examDate的Exam应无效", isValidExam(exam));

        exam.setExamDate("2024-04-20");
        exam.setDuration(-30); // 负数时长
        assertFalse("负数时长的Exam应无效", isValidExam(exam));

        exam.setDuration(120);
        assertTrue("正常的Exam应有效", isValidExam(exam));
    }

    // 辅助验证方法
    private boolean isValidCourse(Course course) {
        if (course.getCourseId() == null || course.getCourseId().trim().isEmpty()) {
            return false;
        }
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            return false;
        }
        if (course.getCredits() <= 0 || course.getCredits() > 8) {
            return false;
        }
        return true;
    }

    private boolean isValidCourseTime(CourseTime time) {
        if (time.getDayOfWeek() == null || time.getDayOfWeek().trim().isEmpty()) {
            return false;
        }
        if (time.getStartTime() <= 0 || time.getEndTime() <= 0) {
            return false;
        }
        if (time.getStartTime() >= time.getEndTime()) {
            return false;
        }
        if (time.getWeekStart() <= 0 || time.getWeekEnd() <= 0) {
            return false;
        }
        if (time.getWeekStart() > time.getWeekEnd()) {
            return false;
        }
        return true;
    }

    private boolean isValidAssignment(Assignment assignment) {
        if (assignment.getAssignmentId() == null || assignment.getAssignmentId().trim().isEmpty()) {
            return false;
        }
        if (assignment.getTitle() == null || assignment.getTitle().trim().isEmpty()) {
            return false;
        }
        if (assignment.getDueDate() == null || assignment.getDueDate().trim().isEmpty()) {
            return false;
        }
        // 简单的日期格式验证
        if (!assignment.getDueDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        return true;
    }

    private boolean isValidExam(Exam exam) {
        if (exam.getExamId() == null || exam.getExamId().trim().isEmpty()) {
            return false;
        }
        if (exam.getTitle() == null || exam.getTitle().trim().isEmpty()) {
            return false;
        }
        if (exam.getExamDate() == null || exam.getExamDate().trim().isEmpty()) {
            return false;
        }
        if (exam.getDuration() <= 0) {
            return false;
        }
        return true;
    }
}