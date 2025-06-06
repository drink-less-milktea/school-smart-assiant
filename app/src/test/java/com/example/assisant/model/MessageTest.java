package com.example.assisant.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Message实体类单元测试
 * 测试AI对话消息数据模型的完整性、序列化/反序列化功能、字段验证等
 */
public class MessageTest {

    private Gson gson;
    private Message testMessage;
    private ChatSession testChatSession;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        // 创建测试用的Message对象
        testMessage = new Message();
        testMessage.setMessageId("msg_001");
        testMessage.setContent("如何提高Java编程能力？");
        testMessage.setType("USER");
        testMessage.setTimestamp("2024-06-01 09:00:00");
        testMessage.setStatus("SENT");

        // 创建测试用的ChatSession对象
        testChatSession = new ChatSession();
        testChatSession.setSessionId("session_20240601_001");
        testChatSession.setTitle("学习问题咨询");
        testChatSession.setCreateTime("2024-06-01 09:00:00");
        testChatSession.setLastUpdateTime("2024-06-01 09:30:00");

        List<Message> messages = new ArrayList<>();

        // 用户消息
        Message userMessage = new Message();
        userMessage.setMessageId("msg_001");
        userMessage.setContent("如何提高Java编程能力？");
        userMessage.setType("USER");
        userMessage.setTimestamp("2024-06-01 09:00:00");
        userMessage.setStatus("SENT");
        messages.add(userMessage);

        // AI回复消息
        Message aiMessage = new Message();
        aiMessage.setMessageId("msg_002");
        aiMessage.setContent("提高Java编程能力可以从以下几个方面入手：\n1. 多练习基础语法\n2. 学习设计模式\n3. 参与开源项目\n4. 阅读优秀代码");
        aiMessage.setType("AI");
        aiMessage.setTimestamp("2024-06-01 09:00:15");
        aiMessage.setStatus("SENT");

        MessageMetadata metadata = new MessageMetadata();
        metadata.setModel("gpt-3.5-turbo");
        metadata.setTokens(125);
        aiMessage.setMetadata(metadata);
        messages.add(aiMessage);

        testChatSession.setMessages(messages);
    }

    @Test
    public void testMessageConstructor() {
        // 测试默认构造函数
        Message message = new Message();
        assertNotNull("Message对象不应为null", message);
        assertNull("默认messageId应为null", message.getMessageId());
        assertNull("默认content应为null", message.getContent());
        assertNull("默认type应为null", message.getType());
        assertNull("默认status应为null", message.getStatus());
    }

    @Test
    public void testMessageGettersAndSetters() {
        // 测试基本字段的getter和setter
        assertEquals("messageId应匹配", "msg_001", testMessage.getMessageId());
        assertEquals("content应匹配", "如何提高Java编程能力？", testMessage.getContent());
        assertEquals("type应匹配", "USER", testMessage.getType());
        assertEquals("timestamp应匹配", "2024-06-01 09:00:00", testMessage.getTimestamp());
        assertEquals("status应匹配", "SENT", testMessage.getStatus());
    }

    @Test
    public void testMessageEqualsAndHashCode() {
        // 创建相同的Message对象
        Message message1 = new Message();
        message1.setMessageId("msg_001");
        message1.setContent("如何提高Java编程能力？");
        message1.setType("USER");

        Message message2 = new Message();
        message2.setMessageId("msg_001");
        message2.setContent("如何提高Java编程能力？");
        message2.setType("USER");

        Message message3 = new Message();
        message3.setMessageId("msg_002");
        message3.setContent("这是另一条消息");
        message3.setType("AI");

        // 测试equals方法
        assertEquals("相同内容的Message应相等", message1, message2);
        assertNotEquals("不同内容的Message应不相等", message1, message3);
        assertNotEquals("Message与null应不相等", message1, null);
        assertNotEquals("Message与其他类型应不相等", message1, "string");

        // 测试hashCode方法
        assertEquals("相同内容的Message应有相同hashCode", message1.hashCode(), message2.hashCode());
        assertNotEquals("不同内容的Message应有不同hashCode", message1.hashCode(), message3.hashCode());
    }

    @Test
    public void testMessageToString() {
        String messageString = testMessage.toString();
        assertNotNull("toString不应返回null", messageString);
        assertTrue("toString应包含messageId", messageString.contains("msg_001"));
        assertTrue("toString应包含content", messageString.contains("如何提高Java编程能力？"));
    }

    @Test
    public void testMessageJsonSerialization() {
        // 测试序列化
        String json = gson.toJson(testMessage);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含messageId", json.contains("msg_001"));
        assertTrue("JSON应包含content", json.contains("如何提高Java编程能力？"));
        assertTrue("JSON应包含type", json.contains("USER"));
        assertTrue("JSON应包含timestamp", json.contains("2024-06-01 09:00:00"));
        assertTrue("JSON应包含status", json.contains("SENT"));
    }

    @Test
    public void testMessageJsonDeserialization() {
        // 先序列化
        String json = gson.toJson(testMessage);

        // 再反序列化
        Message deserializedMessage = gson.fromJson(json, Message.class);

        assertNotNull("反序列化结果不应为null", deserializedMessage);
        assertEquals("messageId应匹配", testMessage.getMessageId(), deserializedMessage.getMessageId());
        assertEquals("content应匹配", testMessage.getContent(), deserializedMessage.getContent());
        assertEquals("type应匹配", testMessage.getType(), deserializedMessage.getType());
        assertEquals("timestamp应匹配", testMessage.getTimestamp(), deserializedMessage.getTimestamp());
        assertEquals("status应匹配", testMessage.getStatus(), deserializedMessage.getStatus());
    }

    @Test
    public void testChatSessionConstructor() {
        // 测试默认构造函数
        ChatSession session = new ChatSession();
        assertNotNull("ChatSession对象不应为null", session);
        assertNull("默认sessionId应为null", session.getSessionId());
        assertNull("默认title应为null", session.getTitle());
        assertNull("默认messages应为null", session.getMessages());
    }

    @Test
    public void testChatSessionGettersAndSetters() {
        // 测试基本字段的getter和setter
        assertEquals("sessionId应匹配", "session_20240601_001", testChatSession.getSessionId());
        assertEquals("title应匹配", "学习问题咨询", testChatSession.getTitle());
        assertEquals("createTime应匹配", "2024-06-01 09:00:00", testChatSession.getCreateTime());
        assertEquals("lastUpdateTime应匹配", "2024-06-01 09:30:00", testChatSession.getLastUpdateTime());

        // 测试集合字段
        assertNotNull("messages不应为null", testChatSession.getMessages());
        assertEquals("messages数量应为2", 2, testChatSession.getMessages().size());
    }

    @Test
    public void testChatSessionJsonSerialization() {
        // 测试序列化
        String json = gson.toJson(testChatSession);
        assertNotNull("JSON序列化结果不应为null", json);
        assertTrue("JSON应包含sessionId", json.contains("session_20240601_001"));
        assertTrue("JSON应包含title", json.contains("学习问题咨询"));
        assertTrue("JSON应包含messages数组", json.contains("messages"));
        assertTrue("JSON应包含createTime", json.contains("2024-06-01 09:00:00"));
        assertTrue("JSON应包含lastUpdateTime", json.contains("2024-06-01 09:30:00"));
    }

    @Test
    public void testChatSessionJsonDeserialization() {
        // 先序列化
        String json = gson.toJson(testChatSession);

        // 再反序列化
        ChatSession deserializedSession = gson.fromJson(json, ChatSession.class);

        assertNotNull("反序列化结果不应为null", deserializedSession);
        assertEquals("sessionId应匹配", testChatSession.getSessionId(), deserializedSession.getSessionId());
        assertEquals("title应匹配", testChatSession.getTitle(), deserializedSession.getTitle());
        assertEquals("createTime应匹配", testChatSession.getCreateTime(), deserializedSession.getCreateTime());
        assertEquals("lastUpdateTime应匹配", testChatSession.getLastUpdateTime(), deserializedSession.getLastUpdateTime());

        // 验证集合字段
        assertEquals("messages数量应匹配", testChatSession.getMessages().size(), deserializedSession.getMessages().size());
    }

    @Test
    public void testMessageFieldValidation() {
        Message message = new Message();

        // 测试必填字段验证
        assertFalse("没有messageId的Message应无效", isValidMessage(message));

        message.setMessageId("msg_001");
        assertFalse("没有content的Message应无效", isValidMessage(message));

        message.setContent("测试消息内容");
        assertFalse("没有type的Message应无效", isValidMessage(message));

        message.setType("INVALID_TYPE");
        assertFalse("无效type的Message应无效", isValidMessage(message));

        message.setType("USER");
        assertFalse("没有timestamp的Message应无效", isValidMessage(message));

        message.setTimestamp("invalid-timestamp");
        assertFalse("无效timestamp格式的Message应无效", isValidMessage(message));

        message.setTimestamp("2024-06-01 09:00:00");
        assertTrue("正常的Message应有效", isValidMessage(message));
    }

    @Test
    public void testMessageTypeValidation() {
        Message message = new Message();
        message.setMessageId("msg_001");
        message.setContent("测试消息");
        message.setTimestamp("2024-06-01 09:00:00");

        // 测试消息类型验证
        message.setType("USER");
        assertTrue("USER类型应有效", isValidMessageType(message.getType()));

        message.setType("AI");
        assertTrue("AI类型应有效", isValidMessageType(message.getType()));

        message.setType("SYSTEM");
        assertTrue("SYSTEM类型应有效", isValidMessageType(message.getType()));

        message.setType("INVALID");
        assertFalse("无效类型应不被接受", isValidMessageType(message.getType()));

        message.setType(null);
        assertFalse("null类型应不被接受", isValidMessageType(message.getType()));
    }

    @Test
    public void testMessageStatusValidation() {
        Message message = new Message();
        message.setMessageId("msg_001");
        message.setContent("测试消息");
        message.setType("USER");
        message.setTimestamp("2024-06-01 09:00:00");

        // 测试消息状态验证
        message.setStatus("SENDING");
        assertTrue("SENDING状态应有效", isValidMessageStatus(message.getStatus()));

        message.setStatus("SENT");
        assertTrue("SENT状态应有效", isValidMessageStatus(message.getStatus()));

        message.setStatus("DELIVERED");
        assertTrue("DELIVERED状态应有效", isValidMessageStatus(message.getStatus()));

        message.setStatus("FAILED");
        assertTrue("FAILED状态应有效", isValidMessageStatus(message.getStatus()));

        message.setStatus("INVALID_STATUS");
        assertFalse("无效状态应不被接受", isValidMessageStatus(message.getStatus()));
    }

    @Test
    public void testChatSessionFieldValidation() {
        ChatSession session = new ChatSession();

        // 测试必填字段验证
        assertFalse("没有sessionId的ChatSession应无效", isValidChatSession(session));

        session.setSessionId("session_001");
        assertFalse("没有title的ChatSession应无效", isValidChatSession(session));

        session.setTitle("测试会话");
        assertFalse("没有createTime的ChatSession应无效", isValidChatSession(session));

        session.setCreateTime("invalid-time");
        assertFalse("无效createTime格式的ChatSession应无效", isValidChatSession(session));

        session.setCreateTime("2024-06-01 09:00:00");
        assertTrue("正常的ChatSession应有效", isValidChatSession(session));
    }

    @Test
    public void testMessageMetadataValidation() {
        MessageMetadata metadata = new MessageMetadata();

        // 测试元数据验证
        metadata.setTokens(-10); // 负数token
        assertFalse("负数tokens的MessageMetadata应无效", isValidMessageMetadata(metadata));

        metadata.setTokens(125);
        metadata.setModel(""); // 空模型名
        assertFalse("空模型名的MessageMetadata应无效", isValidMessageMetadata(metadata));

        metadata.setModel("gpt-3.5-turbo");
        assertTrue("正常的MessageMetadata应有效", isValidMessageMetadata(metadata));
    }

    @Test
    public void testMessageContentLengthValidation() {
        Message message = new Message();
        message.setMessageId("msg_001");
        message.setType("USER");
        message.setTimestamp("2024-06-01 09:00:00");

        // 测试内容长度验证
        message.setContent(""); // 空内容
        assertFalse("空内容的Message应无效", isValidMessageContent(message.getContent()));

        message.setContent("   "); // 只有空格的内容
        assertFalse("只有空格的Message应无效", isValidMessageContent(message.getContent()));

        // 创建超长内容（假设限制为10000字符）
        StringBuilder longContent = new StringBuilder();
        for (int i = 0; i < 10001; i++) {
            longContent.append("a");
        }
        message.setContent(longContent.toString());
        assertFalse("超长内容的Message应无效", isValidMessageContent(message.getContent()));

        message.setContent("正常长度的消息内容");
        assertTrue("正常内容的Message应有效", isValidMessageContent(message.getContent()));
    }

    // 辅助验证方法
    private boolean isValidMessage(Message message) {
        if (message.getMessageId() == null || message.getMessageId().trim().isEmpty()) {
            return false;
        }
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            return false;
        }
        if (!isValidMessageType(message.getType())) {
            return false;
        }
        if (message.getTimestamp() == null || message.getTimestamp().trim().isEmpty()) {
            return false;
        }
        // 简单的时间戳格式验证
        if (!message.getTimestamp().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            return false;
        }
        return true;
    }

    private boolean isValidMessageType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("USER") || type.equals("AI") || type.equals("SYSTEM");
    }

    private boolean isValidMessageStatus(String status) {
        if (status == null) {
            return false;
        }
        return status.equals("SENDING") || status.equals("SENT") ||
                status.equals("DELIVERED") || status.equals("FAILED");
    }

    private boolean isValidChatSession(ChatSession session) {
        if (session.getSessionId() == null || session.getSessionId().trim().isEmpty()) {
            return false;
        }
        if (session.getTitle() == null || session.getTitle().trim().isEmpty()) {
            return false;
        }
        if (session.getCreateTime() == null || session.getCreateTime().trim().isEmpty()) {
            return false;
        }
        // 简单的时间格式验证
        if (!session.getCreateTime().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            return false;
        }
        return true;
    }

    private boolean isValidMessageMetadata(MessageMetadata metadata) {
        if (metadata.getTokens() < 0) {
            return false;
        }
        if (metadata.getModel() == null || metadata.getModel().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidMessageContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }
        // 假设消息内容长度限制为10000字符
        if (content.length() > 10000) {
            return false;
        }
        return true;
    }
}