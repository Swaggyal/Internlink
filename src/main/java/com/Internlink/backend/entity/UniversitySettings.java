package com.Internlink.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "university_settings")
public class UniversitySettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    // Preferences
    private String language;       // e.g. "English"
    private String appearance;     // e.g. "Light", "Dark"

    // Notifications
    private boolean emailNotifications;
    private boolean pushNotifications;

    // Privacy
    private boolean profileVisible;
    private boolean showStudentCount;

    // Feedback
    @Column(length = 1000)
    private String lastFeedback;

    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}