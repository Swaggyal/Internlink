package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedInternshipDTO {

    private Long internshipId;

    private String title;

    private String companyName;

    private String location;

    private String salary;

    private String duration;

    private int matchPercentage;
}