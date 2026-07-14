package com.Internlink.backend.controller;

import com.Internlink.backend.entity.Interview;
import com.Internlink.backend.entity.InterviewStatus;
import com.Internlink.backend.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;

    // Schedule interview
    @PostMapping
    public ResponseEntity<Interview> scheduleInterview(@RequestBody ScheduleInterviewRequest request) {
        Interview interview = interviewService.scheduleInterview(
                request.getApplicationId(),
                request.getInternshipId(),
                request.getScheduledDate(),
                request.getInterviewLink()
        );
        if (interview != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(interview);
        }
        return ResponseEntity.badRequest().build();
    }

    // Get interview by ID
    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterview(@PathVariable Long id) {
        return interviewService.getInterviewById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get interviews by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Interview>> getInterviewsByStudent(@PathVariable Long studentId) {
        List<Interview> interviews = interviewService.getInterviewsByStudent(studentId);
        return ResponseEntity.ok(interviews);
    }

    // Get scheduled interviews for student
    @GetMapping("/student/{studentId}/scheduled")
    public ResponseEntity<List<Interview>> getScheduledInterviewsForStudent(@PathVariable Long studentId) {
        List<Interview> interviews = interviewService.getScheduledInterviewsForStudent(studentId);
        return ResponseEntity.ok(interviews);
    }

    // Get interviews by internship
    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<Interview>> getInterviewsByInternship(@PathVariable Long internshipId) {
        List<Interview> interviews = interviewService.getInterviewsByInternship(internshipId);
        return ResponseEntity.ok(interviews);
    }

    // Get interviews by application
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<Interview>> getInterviewsByApplication(@PathVariable Long applicationId) {
        List<Interview> interviews = interviewService.getInterviewsByApplication(applicationId);
        return ResponseEntity.ok(interviews);
    }

    // Update interview status
    @PutMapping("/{id}/status")
    public ResponseEntity<Interview> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateInterviewStatusRequest request) {
        Interview updated = interviewService.updateInterviewStatus(id, request.getStatus());
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Add notes
    @PutMapping("/{id}/notes")
    public ResponseEntity<Interview> addNotes(
            @PathVariable Long id,
            @RequestBody AddNotesRequest request) {
        Interview updated = interviewService.addNotes(id, request.getNotes());
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Cancel interview
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Interview> cancelInterview(@PathVariable Long id) {
        Interview updated = interviewService.cancelInterview(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Mark as completed
    @PutMapping("/{id}/complete")
    public ResponseEntity<Interview> markAsCompleted(@PathVariable Long id) {
        Interview updated = interviewService.markAsCompleted(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Mark as no-show
    @PutMapping("/{id}/no-show")
    public ResponseEntity<Interview> markAsNoShow(@PathVariable Long id) {
        Interview updated = interviewService.markAsNoShow(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete interview
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        if (interviewService.deleteInterview(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get interviews by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Interview>> getByStatus(@PathVariable InterviewStatus status) {
        List<Interview> interviews = interviewService.getInterviewsByStatus(status);
        return ResponseEntity.ok(interviews);
    }

    // Count interviews for internship
    @GetMapping("/internship/{internshipId}/count")
    public ResponseEntity<Long> countInterviews(@PathVariable Long internshipId) {
        long count = interviewService.countInterviewsForInternship(internshipId);
        return ResponseEntity.ok(count);
    }
}

class ScheduleInterviewRequest {
    private Long applicationId;
    private Long internshipId;
    private java.time.LocalDateTime scheduledDate;
    private String interviewLink;

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public Long getInternshipId() { return internshipId; }
    public void setInternshipId(Long internshipId) { this.internshipId = internshipId; }
    public java.time.LocalDateTime getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(java.time.LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }
    public String getInterviewLink() { return interviewLink; }
    public void setInterviewLink(String interviewLink) { this.interviewLink = interviewLink; }
}

class UpdateInterviewStatusRequest {
    private InterviewStatus status;

    public InterviewStatus getStatus() { return status; }
    public void setStatus(InterviewStatus status) { this.status = status; }
}

class AddNotesRequest {
    private String notes;

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}