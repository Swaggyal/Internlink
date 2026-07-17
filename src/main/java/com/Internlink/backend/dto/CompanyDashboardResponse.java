package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDashboardResponse {

    // Company name
    private String companyName;

    // Total internships posted
    private Integer totalInternships;

    // Total applications received
    private Integer totalApplications;

    // List of company's internships
    private List<InternshipDTO> postedInternships;

    // Pending applications count
    private Integer pendingApplications;

    // Accepted applications count
    private Integer acceptedApplications;
}
