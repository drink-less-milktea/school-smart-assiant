package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 消息元数据实体类
 * 用于存储消息的附加信息，如AI模型、token数量等
 */
public class MessageMetadata {

    @SerializedName("model")
    private String model;

    @SerializedName("tokens")
    private int tokens;

    /**
     * 默认构造函数
     */
    public MessageMetadata() {
    }

    /**
     * 完整构造函数
     */
    public MessageMetadata(String model, int tokens) {
        this.model = model;
        this.tokens = tokens;
    }

    // Getter和Setter方法
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MessageMetadata that = (MessageMetadata) o;
        return tokens == that.tokens &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, tokens);
    }

    @Override
    public String toString() {
        return "MessageMetadata{" +
                "model='" + model + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}