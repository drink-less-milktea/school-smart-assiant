package com.example.assisant.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CampusInfo实体类单元测试
 * 测试校园信息数据模型的完整性、序列化/反序列化功能、字段验证等
 */
public class CampusInfoTest {

    private Gson gson;
    private CampusInfo testCampusInfo;
    private CampusEvent testCampusEvent;
    private CampusService testCampusService;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        // 创建测试用的CampusInfo对象
        testCampusInfo = new CampusInfo();
        testCampusInfo.setInfoId("info_001");
        testCampusInfo.setTitle("图书馆开放时间调整通知");
        testCampusInfo.setContent("因系统维护，图书馆将于本周末暂停开放，请同学们合理安排学习时间。");
        testCampusInfo.setCategory("NOTICE");
        testCampusInfo.setPublishTime("2024-06-01 10:00:00");
        testCampusInfo.setSource("图书馆管理处");
        testCampusInfo.setImportant(true);

        List<String> tags = new ArrayList<>();
        tags.add("图书馆");
        tags.add("开放时间");
        tags.add("通知");
        testCampusInfo.setTags(tags);

        // 创建测试用的CampusEvent对象
        testCampusEvent = new CampusEvent();
        testCampusEvent.setEventId("event_001");
        testCampusEvent.setTitle("2024年春季学术讲座");
        testCampusEvent.setDescription("邀请知名学者进行人工智能前沿技术分享");
        testCampusEvent.setLocation("学术报告厅A101");
        testCampusEvent.setStartTime("2024-06-15 14:00:00");
        testCampusEvent.setEndTime("2024-06-15 16:00:00");
        testCampusEvent.setOrganizer("计算机学院");
        testCampusEvent.setCapacity(200);
        testCampusEvent.setRegistered(85);
        testCampusEvent.setRegistrationRequired(true);
        testCampusEvent.setStatus("OPEN");

        // 创建测试用的CampusService对象
        testCampusService = new CampusService();
        testCampusService.setServiceId("service_001");
        testCampusService.setName("校园卡充值");
        testCampusService.setDescription("提供校园卡在线充值服务");
        testCampusService.setCategory("FINANCIAL");
        testCampusService.setLocation("学生服务中心一楼");
        testCampusService.setContact("010-12345678");
        testCampusService.setAvailable(true);

        List<String> serviceHours = new ArrayList<>();
        serviceHours.add("周一至周五: 8:00-17:00");
        serviceHours.add("周六: 9:00-12:00");
        serviceHours.add("周日: 休息");
        testCampusService.setServiceHours(serviceHours);
    }

    @Test
    public void testCampusInfoConstructor() {
        // 测试默认构造函数
        CampusInfo info = new CampusInfo();
        assertNotNull("CampusInfo对象不应为null", info);
        assertNull("默认infoId应为null", info.getInfoId());
        assertNull("默认title应为null", info.getTitle());
        assertNull("默认category应为null", info.getCategory());
        assertFalse("默认important应为false", info.isImportant());
    }

    @Test
    public void testCampusInfoGettersAndSetters() {
        // 测试基本字段的getter和setter
        assertEquals("infoId应匹配", "info_001", testCampusInfo.getInfoId());
        assertEquals("title应匹配", "图书馆开放时间调整通知", testCampusInfo.getTitle());
        assertEquals("content应匹配", "因系统维护，图书馆将于本周末暂停开放，请同学们合理安排学习时间。", testCampusInfo.getContent());
        assertEquals("category应匹配", "NOTICE", testCampusInfo.getCategory());
        assertEquals("publishTime应匹配", "2024-06-01 10:00:00", testCampusInfo.getPublishTime());
        assertEquals("source应匹配", "图书馆管理处", testCampusInfo.getSource());
        assertTrue("important应为true", testCampusInfo.isImportant());

        // 测试集合字段
        assertNotNull("tags不应为null", testCampusInfo.getTags());
        assertEquals("tags数量应为3", 3, testCampusInfo.getTags().size());
        assertTrue("tags应包含'图书馆'", testCampusInfo.getTags().contains("图书馆"));
    }

    @Test
    public void testCampusInfoEqualsAndHashCode() {
        // 创建相同的CampusInfo对象
        CampusInfo info1 = new CampusInfo();
        info1.setInfoId("info_001");
        info1.setTitle("图书馆开放时间调整通知");
        info1.setCategory("NOTICE");

        CampusInfo info2 = new CampusInfo();
        info2.setInfoId("info_001");
        info2.setTitle("图书馆开放时间调整通知");
        info2.setCategory("NOTICE");

        CampusInfo info3 = new CampusInfo();
        info3.setInfoId("info_002");
        info3.setTitle("食堂菜单更新");
        info3.setCategory("DINING");

        // 测试equals方法
        assertEquals("相同内容的CampusInfo应相等", info1, info2);
        assertNotEquals("不同内容的CampusInfo应不相等", info1, info3);
        assertNotEquals("CampusInfo与null应不相等", info1, null);
        assertNotEquals("CampusInfo与其他类型应不相等", info1, "string");

        // 测试hashCode方法
        assertEquals("相同内容的CampusInfo应有相同hashCode", info1.hashCode(), info2.hashCode());
        assertNotEquals("不同内容的CampusInfo应有不同hashCode", info1.hashCode(), info3.hashCode());
    }

    @Test
    public void testCampusInfoToString() {
        String infoString = testCampusInfo.toString();
        assertNotNull("toString不应返回null", infoString);
        assertTrue("toString应包含infoId", infoString.contains("info_001"));
        assertTrue("toString应包含title", infoString.contains("图书馆开放时间调整通知"));
    }

    @Test
    public void testCampusInfoJsonSerialization() {
        // 测试序列化
        String json = gson.toJson(testCampusInfo);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含infoId", json.contains("info_001"));
        assertTrue("JSON应包含title", json.contains("图书馆开放时间调整通知"));
        assertTrue("JSON应包含category", json.contains("NOTICE"));
        assertTrue("JSON应包含tags数组", json.contains("tags"));
        assertTrue("JSON应包含important", json.contains("important"));
    }

    @Test
    public void testCampusInfoJsonDeserialization() {
        // 先序列化
        String json = gson.toJson(testCampusInfo);

        // 再反序列化
        CampusInfo deserializedInfo = gson.fromJson(json, CampusInfo.class);

        assertNotNull("反序列化结果不应为null", deserializedInfo);
        assertEquals("infoId应匹配", testCampusInfo.getInfoId(), deserializedInfo.getInfoId());
        assertEquals("title应匹配", testCampusInfo.getTitle(), deserializedInfo.getTitle());
        assertEquals("content应匹配", testCampusInfo.getContent(), deserializedInfo.getContent());
        assertEquals("category应匹配", testCampusInfo.getCategory(), deserializedInfo.getCategory());
        assertEquals("publishTime应匹配", testCampusInfo.getPublishTime(), deserializedInfo.getPublishTime());
        assertEquals("source应匹配", testCampusInfo.getSource(), deserializedInfo.getSource());
        assertEquals("important应匹配", testCampusInfo.isImportant(), deserializedInfo.isImportant());

        // 验证集合字段
        assertEquals("tags数量应匹配", testCampusInfo.getTags().size(), deserializedInfo.getTags().size());
    }

    @Test
    public void testCampusEventConstructor() {
        // 测试默认构造函数
        CampusEvent event = new CampusEvent();
        assertNotNull("CampusEvent对象不应为null", event);
        assertNull("默认eventId应为null", event.getEventId());
        assertNull("默认title应为null", event.getTitle());
        assertEquals("默认capacity应为0", 0, event.getCapacity());
        assertEquals("默认registered应为0", 0, event.getRegistered());
        assertFalse("默认registrationRequired应为false", event.isRegistrationRequired());
    }

    @Test
    public void testCampusEventGettersAndSetters() {
        // 测试基本字段的getter和setter
        assertEquals("eventId应匹配", "event_001", testCampusEvent.getEventId());
        assertEquals("title应匹配", "2024年春季学术讲座", testCampusEvent.getTitle());
        assertEquals("description应匹配", "邀请知名学者进行人工智能前沿技术分享", testCampusEvent.getDescription());
        assertEquals("location应匹配", "学术报告厅A101", testCampusEvent.getLocation());
        assertEquals("startTime应匹配", "2024-06-15 14:00:00", testCampusEvent.getStartTime());
        assertEquals("endTime应匹配", "2024-06-15 16:00:00", testCampusEvent.getEndTime());
        assertEquals("organizer应匹配", "计算机学院", testCampusEvent.getOrganizer());
        assertEquals("capacity应匹配", 200, testCampusEvent.getCapacity());
        assertEquals("registered应匹配", 85, testCampusEvent.getRegistered());
        assertTrue("registrationRequired应为true", testCampusEvent.isRegistrationRequired());
        assertEquals("status应匹配", "OPEN", testCampusEvent.getStatus());
    }

    @Test
    public void testCampusEventJsonSerialization() {
        // 测试序列化
        String json = gson.toJson(testCampusEvent);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含eventId", json.contains("event_001"));
        assertTrue("JSON应包含title", json.contains("2024年春季学术讲座"));
        assertTrue("JSON应包含location", json.contains("学术报告厅A101"));
        assertTrue("JSON应包含capacity", json.contains("200"));
        assertTrue("JSON应包含registered", json.contains("85"));
    }

    @Test
    public void testCampusEventJsonDeserialization() {
        // 先序列化
        String json = gson.toJson(testCampusEvent);

        // 再反序列化
        CampusEvent deserializedEvent = gson.fromJson(json, CampusEvent.class);

        assertNotNull("反序列化结果不应为null", deserializedEvent);
        assertEquals("eventId应匹配", testCampusEvent.getEventId(), deserializedEvent.getEventId());
        assertEquals("title应匹配", testCampusEvent.getTitle(), deserializedEvent.getTitle());
        assertEquals("description应匹配", testCampusEvent.getDescription(), deserializedEvent.getDescription());
        assertEquals("location应匹配", testCampusEvent.getLocation(), deserializedEvent.getLocation());
        assertEquals("startTime应匹配", testCampusEvent.getStartTime(), deserializedEvent.getStartTime());
        assertEquals("endTime应匹配", testCampusEvent.getEndTime(), deserializedEvent.getEndTime());
        assertEquals("organizer应匹配", testCampusEvent.getOrganizer(), deserializedEvent.getOrganizer());
        assertEquals("capacity应匹配", testCampusEvent.getCapacity(), deserializedEvent.getCapacity());
        assertEquals("registered应匹配", testCampusEvent.getRegistered(), deserializedEvent.getRegistered());
        assertEquals("registrationRequired应匹配", testCampusEvent.isRegistrationRequired(),
                deserializedEvent.isRegistrationRequired());
        assertEquals("status应匹配", testCampusEvent.getStatus(), deserializedEvent.getStatus());
    }

    @Test
    public void testCampusServiceConstructor() {
        // 测试默认构造函数
        CampusService service = new CampusService();
        assertNotNull("CampusService对象不应为null", service);
        assertNull("默认serviceId应为null", service.getServiceId());
        assertNull("默认name应为null", service.getName());
        assertNull("默认category应为null", service.getCategory());
        assertTrue("默认available应为true", service.isAvailable());
    }

    @Test
    public void testCampusServiceGettersAndSetters() {
        // 测试基本字段的getter和setter
        assertEquals("serviceId应匹配", "service_001", testCampusService.getServiceId());
        assertEquals("name应匹配", "校园卡充值", testCampusService.getName());
        assertEquals("description应匹配", "提供校园卡在线充值服务", testCampusService.getDescription());
        assertEquals("category应匹配", "FINANCIAL", testCampusService.getCategory());
        assertEquals("location应匹配", "学生服务中心一楼", testCampusService.getLocation());
        assertEquals("contact应匹配", "010-12345678", testCampusService.getContact());
        assertTrue("available应为true", testCampusService.isAvailable());

        // 测试集合字段
        assertNotNull("serviceHours不应为null", testCampusService.getServiceHours());
        assertEquals("serviceHours数量应为3", 3, testCampusService.getServiceHours().size());
        assertTrue("serviceHours应包含工作日时间", testCampusService.getServiceHours().contains("周一至周五: 8:00-17:00"));
    }

    @Test
    public void testCampusServiceJsonSerialization() {
        // 测试序列化
        String json = gson.toJson(testCampusService);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含serviceId", json.contains("service_001"));
        assertTrue("JSON应包含name", json.contains("校园卡充值"));
        assertTrue("JSON应包含category", json.contains("FINANCIAL"));
        assertTrue("JSON应包含serviceHours数组", json.contains("serviceHours"));
        assertTrue("JSON应包含available", json.contains("available"));
    }

    @Test
    public void testCampusServiceJsonDeserialization() {
        // 先序列化
        String json = gson.toJson(testCampusService);

        // 再反序列化
        CampusService deserializedService = gson.fromJson(json, CampusService.class);

        assertNotNull("反序列化结果不应为null", deserializedService);
        assertEquals("serviceId应匹配", testCampusService.getServiceId(), deserializedService.getServiceId());
        assertEquals("name应匹配", testCampusService.getName(), deserializedService.getName());
        assertEquals("description应匹配", testCampusService.getDescription(), deserializedService.getDescription());
        assertEquals("category应匹配", testCampusService.getCategory(), deserializedService.getCategory());
        assertEquals("location应匹配", testCampusService.getLocation(), deserializedService.getLocation());
        assertEquals("contact应匹配", testCampusService.getContact(), deserializedService.getContact());
        assertEquals("available应匹配", testCampusService.isAvailable(), deserializedService.isAvailable());

        // 验证集合字段
        assertEquals("serviceHours数量应匹配", testCampusService.getServiceHours().size(),
                deserializedService.getServiceHours().size());
    }

    @Test
    public void testCampusInfoFieldValidation() {
        CampusInfo info = new CampusInfo();

        // 测试必填字段验证
        assertFalse("没有infoId的CampusInfo应无效", isValidCampusInfo(info));

        info.setInfoId("info_001");
        assertFalse("没有title的CampusInfo应无效", isValidCampusInfo(info));

        info.setTitle("测试通知");
        assertFalse("没有content的CampusInfo应无效", isValidCampusInfo(info));

        info.setContent("测试内容");
        assertFalse("没有category的CampusInfo应无效", isValidCampusInfo(info));

        info.setCategory("INVALID_CATEGORY");
        assertFalse("无效category的CampusInfo应无效", isValidCampusInfo(info));

        info.setCategory("NOTICE");
        assertFalse("没有publishTime的CampusInfo应无效", isValidCampusInfo(info));

        info.setPublishTime("invalid-time");
        assertFalse("无效publishTime格式的CampusInfo应无效", isValidCampusInfo(info));

        info.setPublishTime("2024-06-01 10:00:00");
        assertTrue("正常的CampusInfo应有效", isValidCampusInfo(info));
    }

    @Test
    public void testCampusInfoCategoryValidation() {
        CampusInfo info = new CampusInfo();
        info.setInfoId("info_001");
        info.setTitle("测试通知");
        info.setContent("测试内容");
        info.setPublishTime("2024-06-01 10:00:00");

        // 测试分类验证
        info.setCategory("NOTICE");
        assertTrue("NOTICE分类应有效", isValidCampusInfoCategory(info.getCategory()));

        info.setCategory("ACADEMIC");
        assertTrue("ACADEMIC分类应有效", isValidCampusInfoCategory(info.getCategory()));

        info.setCategory("DINING");
        assertTrue("DINING分类应有效", isValidCampusInfoCategory(info.getCategory()));

        info.setCategory("TRANSPORT");
        assertTrue("TRANSPORT分类应有效", isValidCampusInfoCategory(info.getCategory()));

        info.setCategory("INVALID");
        assertFalse("无效分类应不被接受", isValidCampusInfoCategory(info.getCategory()));

        info.setCategory(null);
        assertFalse("null分类应不被接受", isValidCampusInfoCategory(info.getCategory()));
    }

    @Test
    public void testCampusEventFieldValidation() {
        CampusEvent event = new CampusEvent();

        // 测试必填字段验证
        assertFalse("没有eventId的CampusEvent应无效", isValidCampusEvent(event));

        event.setEventId("event_001");
        assertFalse("没有title的CampusEvent应无效", isValidCampusEvent(event));

        event.setTitle("测试活动");
        assertFalse("没有startTime的CampusEvent应无效", isValidCampusEvent(event));

        event.setStartTime("2024-06-15 14:00:00");
        assertFalse("没有endTime的CampusEvent应无效", isValidCampusEvent(event));

        event.setEndTime("2024-06-15 13:00:00"); // 结束时间早于开始时间
        assertFalse("结束时间早于开始时间的CampusEvent应无效", isValidCampusEvent(event));

        event.setEndTime("2024-06-15 16:00:00");
        event.setCapacity(-10); // 负数容量
        assertFalse("负数容量的CampusEvent应无效", isValidCampusEvent(event));

        event.setCapacity(200);
        event.setRegistered(250); // 报名人数超过容量
        assertFalse("报名人数超过容量的CampusEvent应无效", isValidCampusEvent(event));

        event.setRegistered(85);
        assertTrue("正常的CampusEvent应有效", isValidCampusEvent(event));
    }

    @Test
    public void testCampusEventStatusValidation() {
        CampusEvent event = new CampusEvent();
        event.setEventId("event_001");
        event.setTitle("测试活动");
        event.setStartTime("2024-06-15 14:00:00");
        event.setEndTime("2024-06-15 16:00:00");
        event.setCapacity(200);
        event.setRegistered(85);

        // 测试状态验证
        event.setStatus("OPEN");
        assertTrue("OPEN状态应有效", isValidCampusEventStatus(event.getStatus()));

        event.setStatus("CLOSED");
        assertTrue("CLOSED状态应有效", isValidCampusEventStatus(event.getStatus()));

        event.setStatus("CANCELLED");
        assertTrue("CANCELLED状态应有效", isValidCampusEventStatus(event.getStatus()));

        event.setStatus("COMPLETED");
        assertTrue("COMPLETED状态应有效", isValidCampusEventStatus(event.getStatus()));

        event.setStatus("INVALID_STATUS");
        assertFalse("无效状态应不被接受", isValidCampusEventStatus(event.getStatus()));
    }

    @Test
    public void testCampusServiceFieldValidation() {
        CampusService service = new CampusService();

        // 测试必填字段验证
        assertFalse("没有serviceId的CampusService应无效", isValidCampusService(service));

        service.setServiceId("service_001");
        assertFalse("没有name的CampusService应无效", isValidCampusService(service));

        service.setName("测试服务");
        assertFalse("没有category的CampusService应无效", isValidCampusService(service));

        service.setCategory("INVALID_CATEGORY");
        assertFalse("无效category的CampusService应无效", isValidCampusService(service));

        service.setCategory("FINANCIAL");
        assertTrue("正常的CampusService应有效", isValidCampusService(service));
    }

    @Test
    public void testCampusServiceCategoryValidation() {
        CampusService service = new CampusService();
        service.setServiceId("service_001");
        service.setName("测试服务");

        // 测试服务分类验证
        service.setCategory("FINANCIAL");
        assertTrue("FINANCIAL分类应有效", isValidCampusServiceCategory(service.getCategory()));

        service.setCategory("ACADEMIC");
        assertTrue("ACADEMIC分类应有效", isValidCampusServiceCategory(service.getCategory()));

        service.setCategory("LIFE");
        assertTrue("LIFE分类应有效", isValidCampusServiceCategory(service.getCategory()));

        service.setCategory("HEALTH");
        assertTrue("HEALTH分类应有效", isValidCampusServiceCategory(service.getCategory()));

        service.setCategory("INVALID");
        assertFalse("无效分类应不被接受", isValidCampusServiceCategory(service.getCategory()));

        service.setCategory(null);
        assertFalse("null分类应不被接受", isValidCampusServiceCategory(service.getCategory()));
    }

    // 辅助验证方法
    private boolean isValidCampusInfo(CampusInfo info) {
        if (info.getInfoId() == null || info.getInfoId().trim().isEmpty()) {
            return false;
        }
        if (info.getTitle() == null || info.getTitle().trim().isEmpty()) {
            return false;
        }
        if (info.getContent() == null || info.getContent().trim().isEmpty()) {
            return false;
        }
        if (!isValidCampusInfoCategory(info.getCategory())) {
            return false;
        }
        if (info.getPublishTime() == null || info.getPublishTime().trim().isEmpty()) {
            return false;
        }
        // 简单的时间格式验证
        if (!info.getPublishTime().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            return false;
        }
        return true;
    }

    private boolean isValidCampusInfoCategory(String category) {
        if (category == null) {
            return false;
        }
        return category.equals("NOTICE") || category.equals("ACADEMIC") ||
                category.equals("DINING") || category.equals("TRANSPORT") ||
                category.equals("ACTIVITY") || category.equals("OTHER");
    }

    private boolean isValidCampusEvent(CampusEvent event) {
        if (event.getEventId() == null || event.getEventId().trim().isEmpty()) {
            return false;
        }
        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
            return false;
        }
        if (event.getStartTime() == null || event.getStartTime().trim().isEmpty()) {
            return false;
        }
        if (event.getEndTime() == null || event.getEndTime().trim().isEmpty()) {
            return false;
        }
        // 简单的时间比较（假设时间格式为yyyy-MM-dd HH:mm:ss）
        if (event.getStartTime().compareTo(event.getEndTime()) >= 0) {
            return false;
        }
        if (event.getCapacity() < 0) {
            return false;
        }
        if (event.getRegistered() < 0 || event.getRegistered() > event.getCapacity()) {
            return false;
        }
        return true;
    }

    private boolean isValidCampusEventStatus(String status) {
        if (status == null) {
            return false;
        }
        return status.equals("OPEN") || status.equals("CLOSED") ||
                status.equals("CANCELLED") || status.equals("COMPLETED");
    }

    private boolean isValidCampusService(CampusService service) {
        if (service.getServiceId() == null || service.getServiceId().trim().isEmpty()) {
            return false;
        }
        if (service.getName() == null || service.getName().trim().isEmpty()) {
            return false;
        }
        if (!isValidCampusServiceCategory(service.getCategory())) {
            return false;
        }
        return true;
    }

    private boolean isValidCampusServiceCategory(String category) {
        if (category == null) {
            return false;
        }
        return category.equals("FINANCIAL") || category.equals("ACADEMIC") ||
                category.equals("LIFE") || category.equals("HEALTH") ||
                category.equals("TRANSPORT") || category.equals("OTHER");
    }
}