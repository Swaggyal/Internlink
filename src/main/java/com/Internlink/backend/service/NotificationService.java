package com.Internlink.backend.service;

import com.Internlink.backend.entity.Notification;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.repository.NotificationRepository;
import com.Internlink.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final StudentRepository studentRepository;

    // Create notification
    public Notification createNotification(Long studentId, String message, String type) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) return null;

        Notification notification = new Notification();
        notification.setStudent(studentOpt.get());
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);

        return notificationRepository.save(notification);
    }

    // Get all notifications by student
    public List<Notification> getNotificationsByStudent(Long studentId) {
        return notificationRepository.findByStudentId(studentId);
    }

    // Get unread notifications
    public List<Notification> getUnreadNotifications(Long studentId) {
        return notificationRepository.findByStudentIdAndIsReadFalse(studentId);
    }

    // Mark as read
    public Notification markAsRead(Long notificationId) {
        Optional<Notification> notifOpt = notificationRepository.findById(notificationId);
        if (notifOpt.isPresent()) {
            Notification notification = notifOpt.get();
            notification.setIsRead(true);
            return notificationRepository.save(notification);
        }
        return null;
    }

    // Count unread
    public long countUnreadNotifications(Long studentId) {
        return notificationRepository.countByStudentIdAndIsReadFalse(studentId);
    }

    // Delete notification
    public boolean deleteNotification(Long notificationId) {
        if (notificationRepository.existsById(notificationId)) {
            notificationRepository.deleteById(notificationId);
            return true;
        }
        return false;
    }
}