package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDashboardResponse {

    // University name
    private String universityName;

    // Total number of registered students
    private int totalStudents;

    // Total internships available on the platform
    private int availableInternships;

    // Number of applications submitted by students
    private int totalApplications;

    // Students who have secured internships
    private int placedStudents;

    // Recently registered students
    private List<StudentSummary> recentStudents;
}