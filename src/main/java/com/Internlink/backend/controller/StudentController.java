package com.Internlink.backend.controller;

import com.Internlink.backend.dto.AcademicInfoRequest;
import com.Internlink.backend.dto.SkillsRequest;
import com.Internlink.backend.dto.CareerInterestsRequest;
import com.Internlink.backend.dto.PreferredLocationRequest;
import com.Internlink.backend.dto.ProfilePhotoRequest;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import  org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    // Get student profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get student profile by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        Optional<Student> student = studentService.getStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Step 1: Update Academic Information
    @PutMapping("/{id}/academic-info")
    public ResponseEntity<Student> updateAcademicInfo(
            @PathVariable Long id,
            @RequestBody AcademicInfoRequest request) {

        Student student = studentService.updateAcademicInfo(
                id,
                request.getUniversityName(),
                request.getProgram(),
                request.getLevelOfStudy(),
                request.getYearOfStudy()
        );

        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    // Step 2: Update Skills
    @PutMapping("/{id}/skills")
    public ResponseEntity<Student> updateSkills(
            @PathVariable Long id,
            @RequestBody SkillsRequest request) {

        Student student = studentService.updateSkills(id, request.getSkills());

        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    // Step 3: Update Career Interests
    @PutMapping("/{id}/career-interests")
    public ResponseEntity<Student> updateCareerInterests(
            @PathVariable Long id,
            @RequestBody CareerInterestsRequest request) {

        Student student = studentService.updateCareerInterests(id, request.getInterests());

        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    // Step 4: Update Preferred Location
    @PutMapping("/{id}/preferred-location")
    public ResponseEntity<Student> updatePreferredLocation(
            @PathVariable Long id,
            @RequestBody PreferredLocationRequest request) {

        Student student = studentService.updatePreferredLocation(
                id,
                request.getLocation(),
                request.getWorkSetup(),
                request.getWillingToRelocate()
        );

        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    // Step 5: Update Profile Photo
    @PutMapping("/{id}/profile-photo")
    public ResponseEntity<Student> updateProfilePhoto(
            @PathVariable Long id,
            @RequestBody ProfilePhotoRequest request) {

        Student student = studentService.updateProfilePhoto(id, request.getPhotoUrl());

        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}
