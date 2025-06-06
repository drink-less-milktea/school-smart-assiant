package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 校园活动实体类
 * 用于存储校园活动的相关信息
 */
public class CampusEvent {

    @SerializedName("eventId")
    private String eventId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("location")
    private String location;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("organizer")
    private String organizer;

    @SerializedName("capacity")
    private int capacity;

    @SerializedName("registered")
    private int registered;

    @SerializedName("registrationRequired")
    private boolean registrationRequired;

    @SerializedName("status")
    private String status;

    /**
     * 默认构造函数
     */
    public CampusEvent() {
    }

    /**
     * 完整构造函数
     */
    public CampusEvent(String eventId, String title, String description, String location,
            String startTime, String endTime, String organizer, int capacity,
            int registered, boolean registrationRequired, String status) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.capacity = capacity;
        this.registered = registered;
        this.registrationRequired = registrationRequired;
        this.status = status;
    }

    // Getter和Setter方法
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public boolean isRegistrationRequired() {
        return registrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CampusEvent that = (CampusEvent) o;
        return capacity == that.capacity &&
                registered == that.registered &&
                registrationRequired == that.registrationRequired &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(organizer, that.organizer) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, title, description, location, startTime, endTime,
                organizer, capacity, registered, registrationRequired, status);
    }

    @Override
    public String toString() {
        return "CampusEvent{" +
                "eventId='" + eventId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", organizer='" + organizer + '\'' +
                ", capacity=" + capacity +
                ", registered=" + registered +
                ", registrationRequired=" + registrationRequired +
                ", status='" + status + '\'' +
                '}';
    }
}