package com.example.assisant.presenter.course;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import com.example.assisant.base.BaseContract;
import com.example.assisant.model.Course;
import com.example.assisant.model.repository.DataRepository;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Arrays;

/**
 * Unit tests for CourseContract interface
 * Validates the completeness and correctness of interface design
 * Uses reflection to verify interface structure and method signatures
 */
public class CourseContractTest {

    private Class<?> courseContractClass;
    private Class<?> viewInterfaceClass;
    private Class<?> presenterInterfaceClass;

    @Before
    public void setUp() throws Exception {
        // Load CourseContract class and inner interfaces
        courseContractClass = Class.forName("com.example.assisant.presenter.course.CourseContract");

        // Get inner interfaces
        Class<?>[] innerClasses = courseContractClass.getDeclaredClasses();
        for (Class<?> innerClass : innerClasses) {
            if (innerClass.getSimpleName().equals("View")) {
                viewInterfaceClass = innerClass;
            } else if (innerClass.getSimpleName().equals("Presenter")) {
                presenterInterfaceClass = innerClass;
            }
        }
    }

    @Test
    public void testCourseContractClassExists() {
        assertNotNull("CourseContract class should exist", courseContractClass);
        assertTrue("CourseContract should be an interface", courseContractClass.isInterface());
    }

    @Test
    public void testViewInterfaceExists() {
        assertNotNull("CourseContract.View interface should exist", viewInterfaceClass);
        assertTrue("View should be an interface", viewInterfaceClass.isInterface());
    }

    @Test
    public void testPresenterInterfaceExists() {
        assertNotNull("CourseContract.Presenter interface should exist", presenterInterfaceClass);
        assertTrue("Presenter should be an interface", presenterInterfaceClass.isInterface());
    }

    @Test
    public void testViewInterfaceExtendsBaseContractView() {
        assertNotNull("View interface should exist", viewInterfaceClass);

        // Check if View extends BaseContract.View
        Type[] interfaces = viewInterfaceClass.getGenericInterfaces();
        boolean extendsBaseContractView = false;

        for (Type interfaceType : interfaces) {
            if (interfaceType.toString().contains("BaseContract$View")) {
                extendsBaseContractView = true;
                break;
            }
        }

        assertTrue("CourseContract.View should extend BaseContract.View", extendsBaseContractView);
    }

    @Test
    public void testPresenterInterfaceExtendsBaseContractPresenter() {
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        // Check if Presenter extends BaseContract.Presenter
        Type[] interfaces = presenterInterfaceClass.getGenericInterfaces();
        boolean extendsBaseContractPresenter = false;

        for (Type interfaceType : interfaces) {
            if (interfaceType instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) interfaceType;
                if (paramType.getRawType().toString().contains("BaseContract$Presenter")) {
                    extendsBaseContractPresenter = true;
                    break;
                }
            }
        }

        assertTrue("CourseContract.Presenter should extend BaseContract.Presenter", extendsBaseContractPresenter);
    }

    @Test
    public void testViewInterfaceHasDisplayCourseListMethod() {
        assertNotNull("View interface should exist", viewInterfaceClass);

        Method displayCourseListMethod = null;
        try {
            displayCourseListMethod = viewInterfaceClass.getMethod("displayCourseList", List.class);
        } catch (NoSuchMethodException e) {
            fail("View interface should have displayCourseList(List<Course>) method");
        }

        assertNotNull("displayCourseList method should exist", displayCourseListMethod);
        assertEquals("displayCourseList should return void", void.class, displayCourseListMethod.getReturnType());
    }

    @Test
    public void testViewInterfaceHasShowCourseDetailMethod() {
        assertNotNull("View interface should exist", viewInterfaceClass);

        Method showCourseDetailMethod = null;
        try {
            showCourseDetailMethod = viewInterfaceClass.getMethod("showCourseDetail", Course.class);
        } catch (NoSuchMethodException e) {
            fail("View interface should have showCourseDetail(Course) method");
        }

        assertNotNull("showCourseDetail method should exist", showCourseDetailMethod);
        assertEquals("showCourseDetail should return void", void.class, showCourseDetailMethod.getReturnType());
    }

    @Test
    public void testViewInterfaceHasShowScheduleMethod() {
        assertNotNull("View interface should exist", viewInterfaceClass);

        Method showScheduleMethod = null;
        try {
            showScheduleMethod = viewInterfaceClass.getMethod("showSchedule", List.class);
        } catch (NoSuchMethodException e) {
            fail("View interface should have showSchedule(List<Course>) method");
        }

        assertNotNull("showSchedule method should exist", showScheduleMethod);
        assertEquals("showSchedule should return void", void.class, showScheduleMethod.getReturnType());
    }

    @Test
    public void testViewInterfaceHasShowGradeStatisticsMethod() {
        assertNotNull("View interface should exist", viewInterfaceClass);

        boolean hasGradeStatisticsMethod = false;
        Method[] methods = viewInterfaceClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals("showGradeStatistics")) {
                hasGradeStatisticsMethod = true;
                assertEquals("showGradeStatistics should return void", void.class, method.getReturnType());
                break;
            }
        }

        assertTrue("View interface should have showGradeStatistics method", hasGradeStatisticsMethod);
    }

    @Test
    public void testPresenterInterfaceHasLoadCoursesMethod() {
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        Method loadCoursesMethod = null;
        try {
            loadCoursesMethod = presenterInterfaceClass.getMethod("loadCourses");
        } catch (NoSuchMethodException e) {
            fail("Presenter interface should have loadCourses() method");
        }

        assertNotNull("loadCourses method should exist", loadCoursesMethod);
        assertEquals("loadCourses should return void", void.class, loadCoursesMethod.getReturnType());
        assertEquals("loadCourses should have no parameters", 0, loadCoursesMethod.getParameterCount());
    }

    @Test
    public void testPresenterInterfaceHasSaveCourseMethod() {
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        Method saveCourseMethod = null;
        try {
            saveCourseMethod = presenterInterfaceClass.getMethod("saveCourse", Course.class);
        } catch (NoSuchMethodException e) {
            fail("Presenter interface should have saveCourse(Course) method");
        }

        assertNotNull("saveCourse method should exist", saveCourseMethod);
        assertEquals("saveCourse should return void", void.class, saveCourseMethod.getReturnType());
    }

    @Test
    public void testPresenterInterfaceHasDeleteCourseMethod() {
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        Method deleteCourseMethod = null;
        try {
            deleteCourseMethod = presenterInterfaceClass.getMethod("deleteCourse", String.class);
        } catch (NoSuchMethodException e) {
            fail("Presenter interface should have deleteCourse(String) method");
        }

        assertNotNull("deleteCourse method should exist", deleteCourseMethod);
        assertEquals("deleteCourse should return void", void.class, deleteCourseMethod.getReturnType());
    }

    @Test
    public void testPresenterInterfaceHasLoadScheduleMethod() {
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        Method loadScheduleMethod = null;
        try {
            loadScheduleMethod = presenterInterfaceClass.getMethod("loadSchedule");
        } catch (NoSuchMethodException e) {
            fail("Presenter interface should have loadSchedule() method");
        }

        assertNotNull("loadSchedule method should exist", loadScheduleMethod);
        assertEquals("loadSchedule should return void", void.class, loadScheduleMethod.getReturnType());
        assertEquals("loadSchedule should have no parameters", 0, loadScheduleMethod.getParameterCount());
    }

    @Test
    public void testPresenterInterfaceHasCalculateGradeStatisticsMethod() {
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        Method calculateGradeStatisticsMethod = null;
        try {
            calculateGradeStatisticsMethod = presenterInterfaceClass.getMethod("calculateGradeStatistics");
        } catch (NoSuchMethodException e) {
            fail("Presenter interface should have calculateGradeStatistics() method");
        }

        assertNotNull("calculateGradeStatistics method should exist", calculateGradeStatisticsMethod);
        assertEquals("calculateGradeStatistics should return void", void.class,
                calculateGradeStatisticsMethod.getReturnType());
        assertEquals("calculateGradeStatistics should have no parameters", 0,
                calculateGradeStatisticsMethod.getParameterCount());
    }

    @Test
    public void testViewInterfaceMethodCount() {
        assertNotNull("View interface should exist", viewInterfaceClass);

        // Get all declared methods (excluding inherited ones)
        Method[] declaredMethods = viewInterfaceClass.getDeclaredMethods();

        // Expected View methods: displayCourseList, showCourseDetail, showSchedule,
        // showGradeStatistics
        assertTrue("View interface should have at least 4 declared methods", declaredMethods.length >= 4);

        // Verify expected method names exist
        List<String> expectedMethods = Arrays.asList(
                "displayCourseList", "showCourseDetail", "showSchedule", "showGradeStatistics");

        for (String expectedMethod : expectedMethods) {
            boolean methodExists = false;
            for (Method method : declaredMethods) {
                if (method.getName().equals(expectedMethod)) {
                    methodExists = true;
                    break;
                }
            }
            assertTrue("View interface should have " + expectedMethod + " method", methodExists);
        }
    }

    @Test
    public void testPresenterInterfaceMethodCount() {
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        // Get all declared methods (excluding inherited ones)
        Method[] declaredMethods = presenterInterfaceClass.getDeclaredMethods();

        // Expected Presenter methods: loadCourses, saveCourse, deleteCourse,
        // loadSchedule, calculateGradeStatistics
        assertTrue("Presenter interface should have at least 5 declared methods", declaredMethods.length >= 5);

        // Verify expected method names exist
        List<String> expectedMethods = Arrays.asList(
                "loadCourses", "saveCourse", "deleteCourse", "loadSchedule", "calculateGradeStatistics");

        for (String expectedMethod : expectedMethods) {
            boolean methodExists = false;
            for (Method method : declaredMethods) {
                if (method.getName().equals(expectedMethod)) {
                    methodExists = true;
                    break;
                }
            }
            assertTrue("Presenter interface should have " + expectedMethod + " method", methodExists);
        }
    }

    @Test
    public void testInterfaceDesignConsistency() {
        assertNotNull("CourseContract class should exist", courseContractClass);
        assertNotNull("View interface should exist", viewInterfaceClass);
        assertNotNull("Presenter interface should exist", presenterInterfaceClass);

        // Verify that both View and Presenter are inner interfaces of CourseContract
        assertTrue("View should be inner interface of CourseContract",
                viewInterfaceClass.getDeclaringClass().equals(courseContractClass));
        assertTrue("Presenter should be inner interface of CourseContract",
                presenterInterfaceClass.getDeclaringClass().equals(courseContractClass));

        // Verify interface modifiers
        assertTrue("View interface should be public",
                java.lang.reflect.Modifier.isPublic(viewInterfaceClass.getModifiers()));
        assertTrue("Presenter interface should be public",
                java.lang.reflect.Modifier.isPublic(presenterInterfaceClass.getModifiers()));
    }
}