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
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(unique = true, nullable = false)
    private String companyEmail;

    @Column(nullable = false)
    private String companyPassword; // Hashed password

    @Column(nullable = false)
    private String companyPhone;

    @Column(nullable = false)
    private String industry;

    @Column(nullable = false)
    private String hqLocation; // Headquarters location

    @Column(length = 1000)
    private String description;

    @Column(length = 500)
    private String website;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

//    @Column(nullable = false)
//    private String contactPersonName; // HR contact at company
//
//    @Column(nullable = false)
//    private String contactPersonPhone;

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