package com.example.assisant.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * 校园服务实体类
 * 用于存储校园服务的相关信息
 */
public class CampusService {

    @SerializedName("serviceId")
    private String serviceId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;

    @SerializedName("location")
    private String location;

    @SerializedName("contact")
    private String contact;

    @SerializedName("available")
    private boolean available = true;

    @SerializedName("serviceHours")
    private List<String> serviceHours;

    /**
     * 默认构造函数
     */
    public CampusService() {
    }

    /**
     * 完整构造函数
     */
    public CampusService(String serviceId, String name, String description, String category,
            String location, String contact, boolean available, List<String> serviceHours) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.location = location;
        this.contact = contact;
        this.available = available;
        this.serviceHours = serviceHours;
    }

    // Getter和Setter方法
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(List<String> serviceHours) {
        this.serviceHours = serviceHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CampusService that = (CampusService) o;
        return available == that.available &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(category, that.category) &&
                Objects.equals(location, that.location) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(serviceHours, that.serviceHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, name, description, category, location, contact, available, serviceHours);
    }

    @Override
    public String toString() {
        return "CampusService{" +
                "serviceId='" + serviceId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", contact='" + contact + '\'' +
                ", available=" + available +
                ", serviceHours=" + serviceHours +
                '}';
    }
}