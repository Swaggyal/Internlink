package com.Internlink.backend.controller;

import com.Internlink.backend.entity.Application;
import com.Internlink.backend.entity.ApplicationStatus;
import com.Internlink.backend.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    // Apply for an internship
    @PostMapping
    public ResponseEntity<Application> applyForInternship(
            @RequestBody ApplyRequest request) {

        Application application = applicationService.applyForInternship(
                request.getStudentId(),
                request.getInternshipId(),
                request.getResumeUrl(),
                request.getCoverLetterUrl()
        );

        if (application != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(application);
        }
        return ResponseEntity.badRequest().build();
    }

    // Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        Optional<Application> application = applicationService.getApplicationById(id);
        return application.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all applications by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Application>> getApplicationsByStudent(@PathVariable Long studentId) {
        List<Application> applications = applicationService.getApplicationsByStudent(studentId);
        return ResponseEntity.ok(applications);
    }

    // Get all applications for an internship
    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<Application>> getApplicationsByInternship(@PathVariable Long internshipId) {
        List<Application> applications = applicationService.getApplicationsByInternship(internshipId);
        return ResponseEntity.ok(applications);
    }

    // Get applications by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable ApplicationStatus status) {
        List<Application> applications = applicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }

    // Get student's applications by status
    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<Application>> getStudentApplicationsByStatus(
            @PathVariable Long studentId,
            @PathVariable ApplicationStatus status) {
        List<Application> applications = applicationService.getStudentApplicationsByStatus(studentId, status);
        return ResponseEntity.ok(applications);
    }

    // Update application status
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {

        Application application = applicationService.updateApplicationStatus(id, request.getStatus());

        if (application != null) {
            return ResponseEntity.ok(application);
        }
        return ResponseEntity.notFound().build();
    }

    // Withdraw application
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Application> withdrawApplication(@PathVariable Long id) {
        Application application = applicationService.withdrawApplication(id);

        if (application != null) {
            return ResponseEntity.ok(application);
        }
        return ResponseEntity.notFound().build();
    }

    // Check if student already applied
    @GetMapping("/check/{studentId}/{internshipId}")
    public ResponseEntity<Boolean> hasStudentApplied(
            @PathVariable Long studentId,
            @PathVariable Long internshipId) {
        boolean applied = applicationService.hasStudentApplied(studentId, internshipId);
        return ResponseEntity.ok(applied);
    }

    // Get all applications
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
}

// DTOs
class ApplyRequest {
    private Long studentId;
    private Long internshipId;
    private String resumeUrl;
    private String coverLetterUrl;

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getInternshipId() { return internshipId; }
    public void setInternshipId(Long internshipId) { this.internshipId = internshipId; }
    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }
    public String getCoverLetterUrl() { return coverLetterUrl; }
    public void setCoverLetterUrl(String coverLetterUrl) { this.coverLetterUrl = coverLetterUrl; }
}

class UpdateStatusRequest {
    private ApplicationStatus status;

    // Getters and Setters
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}