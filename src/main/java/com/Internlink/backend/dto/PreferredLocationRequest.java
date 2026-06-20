package com.Internlink.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferredLocationRequest {
    private String location;
    private String workSetup;
    private Boolean willingToRelocate;
}