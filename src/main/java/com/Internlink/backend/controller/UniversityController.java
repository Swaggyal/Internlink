package com.Internlink.backend.controller;

import com.Internlink.backend.dto.CareerServicesRequest;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.entity.University;
import com.Internlink.backend.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PatchMapping("/{id}/institution-details")
    public ResponseEntity<University> updateInstitutionDetails(
            @PathVariable Long id, @RequestBody University university) {
        return ResponseEntity.ok(universityService.updateInstitutionDetails(id, university));
    }

    @PatchMapping("/{id}/career-services")
    public ResponseEntity<University> updateCareerServices(
            @PathVariable Long id, @RequestBody CareerServicesRequest request) {
        return ResponseEntity.ok(universityService.updateCareerServices(id, request));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudents(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getStudentsByUniversity(id));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getUniversityStats(id));
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

    @GetMapping("/{id}/internships")
    public ResponseEntity<List<Internship>> getInternships(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getInternshipsForUniversity(id));
    }
}