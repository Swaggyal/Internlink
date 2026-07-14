package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboardResponse {

    private String studentName;

    private int newMatches;

    private List<RecommendedInternshipDTO> recommendedInternships;

    private List<RecentActivityDTO> recentActivities;
}