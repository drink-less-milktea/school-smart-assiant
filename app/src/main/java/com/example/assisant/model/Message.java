package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 消息实体类
 * 用于存储AI对话中的消息信息
 */
public class Message {

    @SerializedName("messageId")
    private String messageId;

    @SerializedName("content")
    private String content;

    @SerializedName("type")
    private String type;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("status")
    private String status;

    @SerializedName("metadata")
    private MessageMetadata metadata;

    /**
     * 默认构造函数
     */
    public Message() {
    }

    /**
     * 完整构造函数
     */
    public Message(String messageId, String content, String type, String timestamp,
            String status, MessageMetadata metadata) {
        this.messageId = messageId;
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
        this.status = status;
        this.metadata = metadata;
    }

    // Getter和Setter方法
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MessageMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(MessageMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId) &&
                Objects.equals(content, message.content) &&
                Objects.equals(type, message.type) &&
                Objects.equals(timestamp, message.timestamp) &&
                Objects.equals(status, message.status) &&
                Objects.equals(metadata, message.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, content, type, timestamp, status, metadata);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", status='" + status + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}