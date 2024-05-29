package com.lostfound.controller;

import javax.validation.Valid;
import java.util.List;

import com.lostfound.dao.NotificationDao;
import com.lostfound.exception.DaoException;
import com.lostfound.model.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationsController {

    private final NotificationDao notificationDao;

    public NotificationsController(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @GetMapping
    public List<Notification> getNotifications() {
        try {
            return notificationDao.getNotifications();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve notifications.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable int id) {
        try {
            Notification notification = notificationDao.getNotificationById(id);
            if (notification == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found.");
            }
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve notification.");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notification createNotification(@Valid @RequestBody Notification notification) {
        try {
            return notificationDao.createNotification(notification);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create notification.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable int id, @Valid @RequestBody Notification notification) {
        try {
            Notification existingNotification = notificationDao.getNotificationById(id);
            if (existingNotification == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found.");
            }
            notification.setNotificationId(id);
            notificationDao.updateNotification(notification);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update notification.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable int id) {
        try {
            Notification existingNotification = notificationDao.getNotificationById(id);
            if (existingNotification == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found.");
            }
            notificationDao.deleteNotification(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete notification.");
        }
    }
}
