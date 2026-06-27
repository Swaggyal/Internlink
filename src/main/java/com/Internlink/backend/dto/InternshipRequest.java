package com.Internlink.backend.dto;

import com.Internlink.backend.entity.Internship;

import java.time.LocalDate;

public record InternshipRequest(
        String title,
        String description,
        String location,
        String duration,
        String requirements,
        LocalDate deadline
) {

    public Internship toEntity() {

        Internship internship = new Internship();

        internship.setTitle(title);
        internship.setDescription(description);
        internship.setLocation(location);
        internship.setDuration(duration);
        internship.setRequirements(requirements);
        internship.setDeadline(deadline);

        return internship;
    }
}