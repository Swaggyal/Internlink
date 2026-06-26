package com.Internlink.backend.entity;

import com.Internlink.backend.entity.ApplicationStatus;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internshipListing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status; // PENDING, ACCEPTED, REJECTED, WITHDRAWN

    private String resumeUrl;
    private String coverLetterUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = ApplicationStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}