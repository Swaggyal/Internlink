package com.Internlink.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    // Academic Information
    private String universityName;

    private String program;

    private String levelOfStudy; // Diploma, Undergraduate, Postgraduate

    private String yearOfStudy; // e.g., "Year 3 (300 Level)"

    // Skills
    @ElementCollection
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill")
    private List<String> skills; // e.g., ["Python", "JavaScript", "Data Analysis"]

    // Career Interests
    @ElementCollection
    @CollectionTable(name = "student_interests", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "interest")
    private List<String> careerInterests; // e.g., ["Software Dev", "Data Science"]

    // Preferred Location
    private String preferredLocation; // e.g., "San Francisco, CA"

    private String workSetup; // Remote, Hybrid, On-site

    private Boolean willingToRelocate; // true/false

    // Profile
    private String profilePhotoUrl;

    private Boolean profileCompleted; // false until all steps done

    private Integer profileStrength; // 0-100 percentage
}