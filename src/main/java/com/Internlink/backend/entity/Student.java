package com.Internlink.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String program;

    @Column(nullable = false)
    private String level; // e.g., "100", "200", "300", "400"

    @Column(nullable = false)
    private String preferredLocation;

    @Column(length = 500)
    private String bio;

    /*@ElementCollection
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill")
    private List<String> skills; // e.g., ["Java", "Python", "SQL"]

     */

}
