package com.Internlink.backend.controller;

import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import com.Internlink.backend.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InternshipController {

    private final InternshipService internshipService;

    // Create internship
    @PostMapping
    public ResponseEntity<Internship> createInternship(@RequestBody Internship internship) {
        Internship created = internshipService.createInternship(internship);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Create internship for company
    @PostMapping("/company/{companyId}")
    public ResponseEntity<Internship> createInternshipForCompany(
            @PathVariable Long companyId,
            @RequestBody Internship internship) {
        Internship created = internshipService.createInternship(companyId, internship);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get all internships
    @GetMapping
    public ResponseEntity<List<Internship>> getAllInternships() {
        List<Internship> internships = internshipService.getAllInternships();
        return ResponseEntity.ok(internships);
    }

    // Get internship by ID
    @GetMapping("/{id}")
    public ResponseEntity<Internship> getInternshipById(@PathVariable Long id) {
        try {
            Internship internship = internshipService.getInternshipById(id);
            return ResponseEntity.ok(internship);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get open internships
    @GetMapping("/open")
    public ResponseEntity<List<Internship>> getOpenInternships() {
        List<Internship> internships = internshipService.getOpenInternships();
        return ResponseEntity.ok(internships);
    }

    // Search by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Internship>> searchByKeyword(@RequestParam String keyword) {
        List<Internship> internships = internshipService.searchByKeyword(keyword);
        return ResponseEntity.ok(internships);
    }

    // Get by industry
    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<Internship>> getByIndustry(@PathVariable String industry) {
        List<Internship> internships = internshipService.getByIndustry(industry);
        return ResponseEntity.ok(internships);
    }

    // Get remote internships
    @GetMapping("/remote")
    public ResponseEntity<List<Internship>> getRemoteInternships() {
        List<Internship> internships = internshipService.getRemoteInternships();
        return ResponseEntity.ok(internships);
    }

    // Update internship
    @PutMapping("/{id}")
    public ResponseEntity<Internship> updateInternship(
            @PathVariable Long id,
            @RequestBody Internship updatedData) {
        try {
            Internship updated = internshipService.updateInternship(id, updatedData);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update internship for company
    @PutMapping("/company/{companyId}/internship/{internshipId}")
    public ResponseEntity<Internship> updateInternshipForCompany(
            @PathVariable Long companyId,
            @PathVariable Long internshipId,
            @RequestBody Internship updated) {
        try {
            Internship result = internshipService.updateInternship(companyId, internshipId, updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete internship
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long id) {
        try {
            internshipService.deleteInternship(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete internship for company
    @DeleteMapping("/company/{companyId}/internship/{internshipId}")
    public ResponseEntity<Void> deleteInternshipForCompany(
            @PathVariable Long companyId,
            @PathVariable Long internshipId) {
        try {
            internshipService.deleteInternship(companyId, internshipId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}