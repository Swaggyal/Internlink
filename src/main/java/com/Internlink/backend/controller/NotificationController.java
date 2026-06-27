package com.Internlink.backend.controller;

import com.Internlink.backend.entity.Notification;
import com.Internlink.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    // Create notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody CreateNotificationRequest request) {
        Notification notification = notificationService.createNotification(
                request.getStudentId(),
                request.getMessage(),
                request.getType()
        );
        if (notification != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        }
        return ResponseEntity.badRequest().build();
    }

    // Get all notifications
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long studentId) {
        List<Notification> notifications = notificationService.getNotificationsByStudent(studentId);
        return ResponseEntity.ok(notifications);
    }

    // Get unread notifications
    @GetMapping("/student/{studentId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long studentId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(studentId);
        return ResponseEntity.ok(notifications);
    }

    // Count unread
    @GetMapping("/student/{studentId}/unread-count")
    public ResponseEntity<Long> countUnread(@PathVariable Long studentId) {
        long count = notificationService.countUnreadNotifications(studentId);
        return ResponseEntity.ok(count);
    }

    // Mark as read
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (notificationService.deleteNotification(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

class CreateNotificationRequest {
    private Long studentId;
    private String message;
    private String type;

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}