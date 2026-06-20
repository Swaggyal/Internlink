package com.Internlink.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(length = 1000)
    private String logoUrl;

    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;

    private String country;
    private String city;
    private Integer numberOfStudents;

    @ElementCollection
    @CollectionTable(name = "university_academic_programs", joinColumns = @JoinColumn(name = "university_id"))
    @Column(name = "program")
    private List<String> academicPrograms;

    @Column(nullable = false)
    private String internshipCoordinatorName;

    private String internshipCoordinatorEmail;

    private String careerServicesContactName;
    private String departmentEmail;
    private String placementOfficeAddress;
    private String placementOfficePhone;
    private String placementOfficeHours;


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
