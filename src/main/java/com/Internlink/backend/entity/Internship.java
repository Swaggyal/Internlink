package com.Internlink.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "internships")
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String pay;

    @Column(nullable = false)
    private String duration;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String requirements;

    private LocalDate deadline;

    @ElementCollection
    @CollectionTable(name = "internship_required_skills",
            joinColumns = @JoinColumn(name = "internship_id"))
    @Column(name = "skill")
    private List<String> requiredSkills;

    @Column(nullable = false)
    private String industry;

    private boolean isRemote;

    @Enumerated(EnumType.STRING)
    private InternshipStatus status;

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