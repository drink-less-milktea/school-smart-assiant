package com.example.assisant;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.assisant.model.*;
import com.example.assisant.model.repository.*;
import com.example.assisant.utils.StorageManager;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * 数据存储层集成测试
 * 
 * 验证整个数据存储层的协同工作，包括：
 * - 实体类的创建和序列化
 * - StorageManager的文件读写操作
 * - Repository的数据管理功能
 * - 端到端的数据流完整性
 * 
 * @author Campus AI Assistant Team
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class DataStorageIntegrationTest {

    private Context context;
    private StorageManager storageManager;
    private DataRepository dataRepository;
    private static final String TEST_PREFIX = "test_";

    @Before
    public void setUp() {
        // 获取测试上下文
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // 初始化存储管理器
        storageManager = StorageManager.getInstance(context);

        // 初始化数据仓库
        dataRepository = DataRepositoryImpl.getInstance(storageManager);

        // 清理之前的测试数据
        cleanupTestData();
    }

    @After
    public void tearDown() {
        // 清理测试数据
        cleanupTestData();
    }

    /**
     * 测试完整的数据流：实体创建 -> Repository存储 -> StorageManager持久化 -> 数据读取
     */
    @Test
    public void testCompleteDataFlow() {
        // Step 1: 创建测试实体
        Course testCourse = createTestCourse();
        StudyPlan testPlan = createTestStudyPlan();

        // Step 2: 通过Repository保存数据
        assertTrue("Course save should succeed", dataRepository.saveCourse(testCourse));
        assertTrue("StudyPlan save should succeed", dataRepository.saveStudyPlan(testPlan));

        // Step 3: 验证数据是否正确保存
        Course retrievedCourse = dataRepository.getCourseById(testCourse.getCourseId());
        StudyPlan retrievedPlan = dataRepository.getStudyPlanById(testPlan.getPlanId());

        assertNotNull("Retrieved course should not be null", retrievedCourse);
        assertNotNull("Retrieved plan should not be null", retrievedPlan);

        // Step 4: 验证数据完整性
        assertEquals("Course ID should match", testCourse.getCourseId(), retrievedCourse.getCourseId());
        assertEquals("Course name should match", testCourse.getCourseName(), retrievedCourse.getCourseName());
        assertEquals("Plan ID should match", testPlan.getPlanId(), retrievedPlan.getPlanId());
        assertEquals("Plan title should match", testPlan.getTitle(), retrievedPlan.getTitle());
    }

    /**
     * 测试数据一致性：多次读写操作的数据一致性
     */
    @Test
    public void testDataConsistency() {
        Course originalCourse = createTestCourse();

        // 第一次保存
        assertTrue("First save should succeed", dataRepository.saveCourse(originalCourse));
        Course firstRead = dataRepository.getCourseById(originalCourse.getCourseId());
        assertNotNull("First read should not be null", firstRead);

        // 修改并再次保存
        originalCourse.setCourseName("Updated Course Name");
        assertTrue("Update save should succeed", dataRepository.saveCourse(originalCourse));

        // 验证更新后的数据
        Course secondRead = dataRepository.getCourseById(originalCourse.getCourseId());
        assertNotNull("Second read should not be null", secondRead);
        assertEquals("Updated course name should match", "Updated Course Name", secondRead.getCourseName());

        // 验证数据确实被更新而不是重复
        List<Course> allCourses = dataRepository.getAllCourses();
        int count = 0;
        for (Course course : allCourses) {
            if (course.getCourseId().equals(originalCourse.getCourseId())) {
                count++;
            }
        }
        assertEquals("Should have only one course with the same ID", 1, count);
    }

    /**
     * 测试缓存机制的工作情况
     */
    @Test
    public void testCachingMechanism() {
        Course testCourse = createTestCourse();
        String cacheKey = TEST_PREFIX + "cache_test";

        // 测试缓存存储
        storageManager.cacheData(cacheKey, testCourse);
        Course cachedCourse = storageManager.getCachedData(cacheKey, Course.class);

        assertNotNull("Cached course should not be null", cachedCourse);
        assertEquals("Cached course ID should match", testCourse.getCourseId(), cachedCourse.getCourseId());

        // 测试缓存清理
        storageManager.clearCache(cacheKey);
        Course clearedCache = storageManager.getCachedData(cacheKey, Course.class);
        assertNull("Cleared cache should be null", clearedCache);
    }

    /**
     * 测试大量数据的基础性能
     */
    @Test
    public void testBasicPerformance() {
        long startTime = System.currentTimeMillis();

        // 创建并保存多个课程
        for (int i = 0; i < 10; i++) {
            Course course = createTestCourse();
            course.setCourseId(TEST_PREFIX + "course_" + i);
            course.setCourseName("Test Course " + i);
            assertTrue("Course " + i + " save should succeed", dataRepository.saveCourse(course));
        }

        // 读取所有课程
        List<Course> allCourses = dataRepository.getAllCourses();
        assertTrue("Should have at least 10 courses", allCourses.size() >= 10);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 基础性能检查：10个课程操作应该在合理时间内完成（<5秒）
        assertTrue("Performance test should complete within 5 seconds", duration < 5000);
    }

    /**
     * 测试异常恢复：数据损坏情况的处理
     */
    @Test
    public void testExceptionRecovery() {
        Course testCourse = createTestCourse();

        // 正常保存
        assertTrue("Normal save should succeed", dataRepository.saveCourse(testCourse));

        // 尝试保存null数据（应该处理异常）
        boolean nullSaveResult = dataRepository.saveCourse(null);
        assertFalse("Null save should fail gracefully", nullSaveResult);

        // 验证原有数据仍然存在
        Course retrievedCourse = dataRepository.getCourseById(testCourse.getCourseId());
        assertNotNull("Original course should still exist", retrievedCourse);
        assertEquals("Original course data should be intact", testCourse.getCourseId(), retrievedCourse.getCourseId());
    }

    /**
     * 测试SharedPreferences集成
     */
    @Test
    public void testSharedPreferencesIntegration() {
        String testKey = TEST_PREFIX + "prefs_test";
        String testValue = "test_preference_value";

        // 保存到SharedPreferences
        assertTrue("Preference save should succeed", storageManager.savePreference(testKey, testValue));

        // 读取并验证
        String retrievedValue = storageManager.getPreference(testKey, "default");
        assertEquals("Retrieved preference should match", testValue, retrievedValue);

        // 清理
        assertTrue("Preference save should succeed", storageManager.savePreference(testKey, ""));
    }

    // ==================== 辅助方法 ====================

    /**
     * 创建测试课程对象
     */
    private Course createTestCourse() {
        Course course = new Course();
        course.setCourseId(TEST_PREFIX + UUID.randomUUID().toString());
        course.setCourseName("Test Course");
        course.setInstructor("Test Instructor");
        course.setCourseCode("TEST101");
        course.setCredits(3);
        course.setSemester("2024春季");
        course.setDescription("This is a test course");
        return course;
    }

    /**
     * 创建测试学习计划对象
     */
    private StudyPlan createTestStudyPlan() {
        StudyPlan plan = new StudyPlan();
        plan.setPlanId(TEST_PREFIX + UUID.randomUUID().toString());
        plan.setTitle("Test Study Plan");
        plan.setDescription("This is a test study plan");
        plan.setStartDate("2024-01-01");
        plan.setEndDate("2024-06-30");
        plan.setStatus("active");
        return plan;
    }

    /**
     * 清理测试数据
     */
    private void cleanupTestData() {
        try {
            // 清理SharedPreferences中的测试数据
            SharedPreferences prefs = context.getSharedPreferences("campus_assistant_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // 删除所有以test_前缀的键
            for (String key : prefs.getAll().keySet()) {
                if (key.startsWith(TEST_PREFIX)) {
                    editor.remove(key);
                }
            }
            editor.apply();

            // 清理内存缓存
            storageManager.clearAllCache();

            // 清理测试数据（通过Repository）
            List<Course> courses = dataRepository.getAllCourses();
            for (Course course : courses) {
                if (course.getCourseId().startsWith(TEST_PREFIX)) {
                    dataRepository.deleteCourse(course.getCourseId());
                }
            }

            List<StudyPlan> plans = dataRepository.getAllStudyPlans();
            for (StudyPlan plan : plans) {
                if (plan.getPlanId().startsWith(TEST_PREFIX)) {
                    dataRepository.deleteStudyPlan(plan.getPlanId());
                }
            }

        } catch (Exception e) {
            // 忽略清理过程中的异常
        }
    }
}