package com.Internlink.backend.controller;

import com.Internlink.backend.dto.CareerServicesRequest;
import com.Internlink.backend.entity.University;
import com.Internlink.backend.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping
    public ResponseEntity<University> register(@RequestBody University university) {
        University created = universityService.registerUniversity(university);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<University>> getAll() {
        return ResponseEntity.ok(universityService.getAllUniversities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<University> getById(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getUniversityById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<University> update(@PathVariable Long id, @RequestBody University university) {
        return ResponseEntity.ok(universityService.updateUniversity(id, university));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/onboarding/career-services")
    public ResponseEntity<University> updateCareerServices(
            @PathVariable Long id,
            @RequestBody CareerServicesRequest request) {
        return ResponseEntity.ok(universityService.updateCareerServices(id, request));
    }
}