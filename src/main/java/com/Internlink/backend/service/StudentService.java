package com.Internlink.backend.service;

import com.Internlink.backend.entity.Student;
import com.Internlink.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Get student by email
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    // Update Academic Information (Step 1)
    public Student updateAcademicInfo(Long studentId, String university, String program, String level, String year) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setUniversityName(university);
            student.setProgram(program);
            student.setLevelOfStudy(level);
            student.setYearOfStudy(year);
            student.setProfileStrength(calculateProfileStrength(student));
            return studentRepository.save(student);
        }
        return null;
    }

    // Update Skills (Step 2)
    public Student updateSkills(Long studentId, List<String> skills) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setSkills(skills);
            student.setProfileStrength(calculateProfileStrength(student));
            return studentRepository.save(student);
        }
        return null;
    }

    // Update Career Interests (Step 3)
    public Student updateCareerInterests(Long studentId, List<String> interests) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setCareerInterests(interests);
            student.setProfileStrength(calculateProfileStrength(student));
            return studentRepository.save(student);
        }
        return null;
    }

    // Update Preferred Location (Step 4)
    public Student updatePreferredLocation(Long studentId, String location, String workSetup, Boolean willingToRelocate) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setPreferredLocation(location);
            student.setWorkSetup(workSetup);
            student.setWillingToRelocate(willingToRelocate);
            student.setProfileStrength(calculateProfileStrength(student));
            return studentRepository.save(student);
        }
        return null;
    }

    // Update Profile Photo (Step 5)
    public Student updateProfilePhoto(Long studentId, String photoUrl) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setProfilePhotoUrl(photoUrl);
            student.setProfileStrength(calculateProfileStrength(student));
            checkProfileCompletion(student);
            return studentRepository.save(student);
        }
        return null;
    }

    // Calculate profile strength percentage
    private Integer calculateProfileStrength(Student student) {
        int strength = 0;

        if (student.getUniversityName() != null) strength += 20;
        if (student.getSkills() != null && !student.getSkills().isEmpty()) strength += 20;
        if (student.getCareerInterests() != null && !student.getCareerInterests().isEmpty()) strength += 20;
        if (student.getPreferredLocation() != null) strength += 20;
        if (student.getProfilePhotoUrl() != null) strength += 20;

        return strength;
    }

    // Check if profile is complete
    private void checkProfileCompletion(Student student) {
        if (student.getUniversityName() != null &&
                student.getSkills() != null && !student.getSkills().isEmpty() &&
                student.getCareerInterests() != null && !student.getCareerInterests().isEmpty() &&
                student.getPreferredLocation() != null &&
                student.getProfilePhotoUrl() != null) {
            student.setProfileCompleted(true);
        }
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
