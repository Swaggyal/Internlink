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

public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String universityEmail;

    @Column(unique = true, nullable = false)
    private String UniversityPassword;

    @Column(nullable = false)
    private String universityPhone;

    @Column(length = 500)
    private String website;

    @Column(nullable = false)
    private String careerOfficerName; // Person managing internships

    @Column(nullable = false)
    private String careerOfficerEmail; // department email

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
