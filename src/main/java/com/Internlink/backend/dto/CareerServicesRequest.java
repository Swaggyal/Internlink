package com.Internlink.backend.dto;

import lombok.Data;

@Data
public class CareerServicesRequest {
    private String careerServicesContactName;
    private String departmentEmail;
    private String placementOfficeAddress;
    private String placementOfficePhone;
    private String placementOfficeHours;
    private String internshipCoordinatorName;
    private String internshipCoordinatorEmail;
}
