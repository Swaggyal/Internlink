package com.Internlink.backend.repository;

import com.Internlink.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStudentId(Long studentId);
    List<Notification> findByStudentIdAndIsReadFalse(Long studentId);
    long countByStudentIdAndIsReadFalse(Long studentId);
    List<Notification> findTop5ByStudentIdOrderByCreatedAtDesc(Long studentId);}