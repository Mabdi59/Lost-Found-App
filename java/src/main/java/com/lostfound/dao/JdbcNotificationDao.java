package com.lostfound.dao;

import com.lostfound.exception.DaoException;
import com.lostfound.model.Notification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcNotificationDao implements NotificationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNotificationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Notification> getNotifications() {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT notification_id, message, is_read, user_id, created_at FROM notifications";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Notification notification = mapRowToNotification(results);
                notifications.add(notification);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return notifications;
    }

    @Override
    public Notification getNotificationById(int notificationId) {
        Notification notification = null;
        String sql = "SELECT notification_id, message, is_read, user_id, created_at FROM notifications WHERE notification_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, notificationId);
            if (results.next()) {
                notification = mapRowToNotification(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return notification;
    }

    @Override
    public Notification createNotification(Notification notification) {
        Notification newNotification = null;
        String sql = "INSERT INTO notifications (message, is_read, user_id, created_at) VALUES (?, ?, ?, ?) RETURNING notification_id";
        try {
            int newNotificationId = jdbcTemplate.queryForObject(sql, int.class, notification.getMessage(), notification.isRead(), notification.getUserId(), notification.getCreatedAt());
            newNotification = getNotificationById(newNotificationId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newNotification;
    }

    @Override
    public void updateNotification(Notification notification) {
        String sql = "UPDATE notifications SET message = ?, is_read = ?, user_id = ?, created_at = ? WHERE notification_id = ?";
        try {
            jdbcTemplate.update(sql, notification.getMessage(), notification.isRead(), notification.getUserId(), notification.getCreatedAt(), notification.getNotificationId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteNotification(int notificationId) {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";
        try {
            jdbcTemplate.update(sql, notificationId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Notification mapRowToNotification(SqlRowSet rs) {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notification_id"));
        notification.setMessage(rs.getString("message"));
        notification.setRead(rs.getBoolean("is_read"));
        notification.setUserId(rs.getInt("user_id"));
        notification.setCreatedAt(rs.getTimestamp("created_at"));
        return notification;
    }
}
