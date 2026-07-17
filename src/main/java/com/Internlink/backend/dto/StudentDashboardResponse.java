package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboardResponse {

    // Student's email/name
    private String studentName;

    // Number of new recommended matches
    private int newMatches;

    // List of recommended internships based on skills match
    private List<RecommendedInternshipDTO> recommendedInternships;

    // List of recent activities/notifications
    private List<RecentActivityDTO> recentActivities;

    // List of internships saved by student
    private List<RecommendedInternshipDTO> savedInternships;

    // Total count of saved internships
    private Integer savedCount;
}