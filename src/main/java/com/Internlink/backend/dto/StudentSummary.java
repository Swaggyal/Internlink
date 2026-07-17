package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSummary {

    private Long id;

    private String email;

    private String program;

    private String yearOfStudy;

    private Integer profileStrength;
}