package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * 聊天会话实体类
 * 用于存储AI对话会话的信息
 */
public class ChatSession {

    @SerializedName("sessionId")
    private String sessionId;

    @SerializedName("title")
    private String title;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("lastUpdateTime")
    private String lastUpdateTime;

    @SerializedName("messages")
    private List<Message> messages;

    /**
     * 默认构造函数
     */
    public ChatSession() {
    }

    /**
     * 完整构造函数
     */
    public ChatSession(String sessionId, String title, String createTime,
            String lastUpdateTime, List<Message> messages) {
        this.sessionId = sessionId;
        this.title = title;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.messages = messages;
    }

    // Getter和Setter方法
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ChatSession that = (ChatSession) o;
        return Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(lastUpdateTime, that.lastUpdateTime) &&
                Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, title, createTime, lastUpdateTime, messages);
    }

    @Override
    public String toString() {
        return "ChatSession{" +
                "sessionId='" + sessionId + '\'' +
                ", title='" + title + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", messages=" + messages +
                '}';
    }
}