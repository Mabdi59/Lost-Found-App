package com.lostfound.dao;

import com.lostfound.model.Notification;

import java.util.List;

public interface NotificationDao {

    List<Notification> getNotifications();

    Notification getNotificationById(int id);

    Notification createNotification(Notification notification);

    void updateNotification(Notification notification);

    void deleteNotification(int id);
}
