package com.Internlink.backend.controller;

import com.Internlink.backend.entity.SavedInternship;
import com.Internlink.backend.service.SavedInternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-internships")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SavedInternshipController {

    private final SavedInternshipService savedInternshipService;

    // Save internship
    @PostMapping("/student/{studentId}/internship/{internshipId}")
    public ResponseEntity<SavedInternship> saveInternship(
            @PathVariable Long studentId,
            @PathVariable Long internshipId) {
        SavedInternship saved = savedInternshipService.saveInternship(studentId, internshipId);
        if (saved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }
        return ResponseEntity.badRequest().build();
    }

    // Remove saved internship
    @DeleteMapping("/student/{studentId}/internship/{internshipId}")
    public ResponseEntity<Void> removeSavedInternship(
            @PathVariable Long studentId,
            @PathVariable Long internshipId) {
        if (savedInternshipService.removeSavedInternship(studentId, internshipId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get all saved internships
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SavedInternship>> getSavedInternships(@PathVariable Long studentId) {
        List<SavedInternship> saved = savedInternshipService.getSavedInternshipsByStudent(studentId);
        return ResponseEntity.ok(saved);
    }

    // Check if saved
    @GetMapping("/student/{studentId}/internship/{internshipId}/is-saved")
    public ResponseEntity<Boolean> isSaved(
            @PathVariable Long studentId,
            @PathVariable Long internshipId) {
        boolean saved = savedInternshipService.isSaved(studentId, internshipId);
        return ResponseEntity.ok(saved);
    }

    // Count saved
    @GetMapping("/student/{studentId}/count")
    public ResponseEntity<Long> countSaved(@PathVariable Long studentId) {
        long count = savedInternshipService.countSavedInternships(studentId);
        return ResponseEntity.ok(count);
    }
}