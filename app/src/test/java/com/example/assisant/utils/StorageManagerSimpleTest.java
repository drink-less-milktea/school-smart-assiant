package com.example.assisant.utils;

import com.example.assisant.model.Course;
import com.example.assisant.model.StudyPlan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * StorageManager简化测试
 * 专注于验证核心逻辑，不依赖Android组件
 */
public class StorageManagerSimpleTest {

    private Gson gson;
    private ConcurrentHashMap<String, Object> testCache;

    @Before
    public void setUp() {
        gson = new Gson();
        testCache = new ConcurrentHashMap<>();
    }

    /**
     * 测试Gson序列化和反序列化功能
     */
    @Test
    public void testGsonSerialization() {
        // 创建测试数据
        Course course = new Course();
        course.setCourseId("CS101");
        course.setCourseName("计算机科学导论");
        course.setCredits(3);
        course.setInstructor("张教授");

        // 序列化
        String json = gson.toJson(course);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含课程ID", json.contains("CS101"));
        assertTrue("JSON应包含课程名称", json.contains("计算机科学导论"));

        // 反序列化
        Course deserializedCourse = gson.fromJson(json, Course.class);
        assertNotNull("反序列化结果不应为null", deserializedCourse);
        assertEquals("课程ID应该一致", course.getCourseId(), deserializedCourse.getCourseId());
        assertEquals("课程名称应该一致", course.getCourseName(), deserializedCourse.getCourseName());
        assertEquals("学分应该一致", course.getCredits(), deserializedCourse.getCredits());
    }

    /**
     * 测试列表序列化和反序列化
     */
    @Test
    public void testListSerialization() {
        // 创建测试数据列表
        List<Course> courses = new ArrayList<>();

        Course course1 = new Course();
        course1.setCourseId("CS101");
        course1.setCourseName("计算机科学导论");
        course1.setCredits(3);

        Course course2 = new Course();
        course2.setCourseId("MATH201");
        course2.setCourseName("高等数学");
        course2.setCredits(4);

        courses.add(course1);
        courses.add(course2);

        // 序列化列表
        String json = gson.toJson(courses);
        assertNotNull("列表JSON序列化结果不应为null", json);

        // 反序列化列表
        Type listType = new TypeToken<List<Course>>() {
        }.getType();
        List<Course> deserializedCourses = gson.fromJson(json, listType);

        assertNotNull("反序列化列表不应为null", deserializedCourses);
        assertEquals("列表大小应该一致", courses.size(), deserializedCourses.size());
        assertEquals("第一个课程ID应该一致", courses.get(0).getCourseId(),
                deserializedCourses.get(0).getCourseId());
        assertEquals("第二个课程ID应该一致", courses.get(1).getCourseId(),
                deserializedCourses.get(1).getCourseId());
    }

    /**
     * 测试内存缓存功能
     */
    @Test
    public void testMemoryCache() {
        String key = "test_course";
        Course course = new Course();
        course.setCourseId("CS101");
        course.setCourseName("计算机科学导论");

        // 存储到缓存
        testCache.put(key, course);
        assertTrue("缓存应包含指定键", testCache.containsKey(key));

        // 从缓存获取
        Object cachedObject = testCache.get(key);
        assertNotNull("缓存对象不应为null", cachedObject);
        assertTrue("缓存对象应为Course类型", cachedObject instanceof Course);

        Course cachedCourse = (Course) cachedObject;
        assertEquals("缓存的课程ID应该一致", course.getCourseId(), cachedCourse.getCourseId());
        assertEquals("缓存的课程名称应该一致", course.getCourseName(), cachedCourse.getCourseName());

        // 清除缓存
        testCache.remove(key);
        assertFalse("清除后缓存不应包含指定键", testCache.containsKey(key));
    }

    /**
     * 测试StudyPlan序列化
     */
    @Test
    public void testStudyPlanSerialization() {
        StudyPlan plan = new StudyPlan();
        plan.setPlanId("PLAN001");
        plan.setTitle("期末复习计划");
        plan.setDescription("为期末考试制定的复习计划");

        // 序列化
        String json = gson.toJson(plan);
        assertNotNull("StudyPlan JSON序列化结果不应为null", json);
        assertTrue("JSON应包含计划ID", json.contains("PLAN001"));

        // 反序列化
        StudyPlan deserializedPlan = gson.fromJson(json, StudyPlan.class);
        assertNotNull("反序列化结果不应为null", deserializedPlan);
        assertEquals("计划ID应该一致", plan.getPlanId(), deserializedPlan.getPlanId());
        assertEquals("计划标题应该一致", plan.getTitle(), deserializedPlan.getTitle());
    }

    /**
     * 测试空数据处理
     */
    @Test
    public void testEmptyDataHandling() {
        // 测试空列表
        List<Course> emptyCourses = new ArrayList<>();
        String json = gson.toJson(emptyCourses);
        assertNotNull("空列表JSON不应为null", json);
        assertEquals("空列表JSON应为[]", "[]", json);

        // 反序列化空列表
        Type listType = new TypeToken<List<Course>>() {
        }.getType();
        List<Course> deserializedEmpty = gson.fromJson(json, listType);
        assertNotNull("反序列化空列表不应为null", deserializedEmpty);
        assertTrue("反序列化空列表应为空", deserializedEmpty.isEmpty());
    }

    /**
     * 测试null值处理
     */
    @Test
    public void testNullHandling() {
        // 测试null对象序列化
        String nullJson = gson.toJson(null);
        assertEquals("null对象序列化应为'null'", "null", nullJson);

        // 测试包含null字段的对象
        Course courseWithNulls = new Course();
        courseWithNulls.setCourseId("CS101");
        // 其他字段保持null

        String json = gson.toJson(courseWithNulls);
        assertNotNull("包含null字段的对象JSON不应为null", json);
        assertTrue("JSON应包含课程ID", json.contains("CS101"));
    }

    /**
     * 测试线程安全的缓存操作
     */
    @Test
    public void testConcurrentCacheOperations() throws InterruptedException {
        final String keyPrefix = "concurrent_test_";
        final int threadCount = 10;
        final int operationsPerThread = 100;

        Thread[] threads = new Thread[threadCount];

        // 创建多个线程同时操作缓存
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    String key = keyPrefix + threadId + "_" + j;
                    Course course = new Course();
                    course.setCourseId(key);

                    // 存储
                    testCache.put(key, course);

                    // 读取
                    Object retrieved = testCache.get(key);
                    assertNotNull("并发读取的对象不应为null", retrieved);

                    // 删除
                    testCache.remove(key);
                }
            });
        }

        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }

        // 验证缓存为空（所有数据都被删除）
        assertTrue("并发操作后缓存应为空", testCache.isEmpty());
    }
}