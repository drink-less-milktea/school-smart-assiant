package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * 校园信息实体类
 * 用于存储校园相关信息，如通知、公告等
 */
public class CampusInfo {

    @SerializedName("infoId")
    private String infoId;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("category")
    private String category;

    @SerializedName("categoryId")
    private String categoryId;

    @SerializedName("type")
    private String type;

    @SerializedName("isFavorite")
    private boolean isFavorite;

    @SerializedName("publishTime")
    private String publishTime;

    @SerializedName("source")
    private String source;

    @SerializedName("important")
    private boolean important;

    @SerializedName("tags")
    private List<String> tags;

    /**
     * 默认构造函数
     */
    public CampusInfo() {
    }

    /**
     * 完整构造函数
     */
    public CampusInfo(String infoId, String title, String content, String category,
            String categoryId, String type, boolean isFavorite, String publishTime,
            String source, boolean important, List<String> tags) {
        this.infoId = infoId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.categoryId = categoryId;
        this.type = type;
        this.isFavorite = isFavorite;
        this.publishTime = publishTime;
        this.source = source;
        this.important = important;
        this.tags = tags;
    }

    // Getter和Setter方法
    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CampusInfo that = (CampusInfo) o;
        return important == that.important &&
                isFavorite == that.isFavorite &&
                Objects.equals(infoId, that.infoId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(category, that.category) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(publishTime, that.publishTime) &&
                Objects.equals(source, that.source) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infoId, title, content, category, categoryId, type, isFavorite, publishTime, source,
                important, tags);
    }

    @Override
    public String toString() {
        return "CampusInfo{" +
                "infoId='" + infoId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", type='" + type + '\'' +
                ", isFavorite=" + isFavorite +
                ", publishTime='" + publishTime + '\'' +
                ", source='" + source + '\'' +
                ", important=" + important +
                ", tags=" + tags +
                '}';
    }
}