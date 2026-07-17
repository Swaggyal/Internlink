package com.Internlink.backend.controller;

import com.Internlink.backend.dto.StudentDashboardResponse;
import com.Internlink.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    // Get complete student dashboard with recommended internships, saved internships, and recent activities
    @GetMapping("/student/{studentId}")
    public ResponseEntity<StudentDashboardResponse> getStudentDashboard(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                dashboardService.getStudentDashboard(studentId)
        );
    }
}