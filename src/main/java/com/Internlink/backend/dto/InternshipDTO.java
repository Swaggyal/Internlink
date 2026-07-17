package com.Internlink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InternshipDTO {
    private Long id;
    private String title;
    private String location;
    private String pay;
    private String status;
    private Integer applicationCount;


}
