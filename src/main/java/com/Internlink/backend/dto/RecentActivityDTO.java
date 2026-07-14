package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentActivityDTO {

    private String type;

    private String message;

    private String time;
}