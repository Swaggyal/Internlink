package com.Internlink.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicInfoRequest {
    private String universityName;
    private String program;
    private String levelOfStudy;
    private String yearOfStudy;
}