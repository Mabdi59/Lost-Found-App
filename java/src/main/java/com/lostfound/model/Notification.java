package com.lostfound.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Notification {

    private int notificationId;
    private String message;
    private boolean isRead;
    private int userId;
    private Timestamp createdAt;

    public Notification() { }

    public Notification(int notificationId, String message, boolean isRead, int userId, Timestamp createdAt) {
        this.notificationId = notificationId;
        this.message = message;
        this.isRead = isRead;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return notificationId == that.notificationId &&
                isRead == that.isRead &&
                userId == that.userId &&
                Objects.equals(message, that.message) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, message, isRead, userId, createdAt);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
