package com.Internlink.backend.service;

import com.Internlink.backend.entity.SavedInternship;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.repository.SavedInternshipRepository;
import com.Internlink.backend.repository.StudentRepository;
import com.Internlink.backend.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedInternshipService {

    private final SavedInternshipRepository savedInternshipRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;

    // Save internship
    public SavedInternship saveInternship(Long studentId, Long internshipId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Internship> internshipOpt = internshipRepository.findById(internshipId);

        if (studentOpt.isEmpty() || internshipOpt.isEmpty()) {
            return null;
        }

        // Check if already saved
        if (savedInternshipRepository.existsByStudentIdAndInternshipId(studentId, internshipId)) {
            return null;
        }

        SavedInternship saved = new SavedInternship();
        saved.setStudent(studentOpt.get());
        saved.setInternship(internshipOpt.get());

        return savedInternshipRepository.save(saved);
    }

    // Remove saved internship
    public boolean removeSavedInternship(Long studentId, Long internshipId) {
        Optional<SavedInternship> saved = savedInternshipRepository.findByStudentIdAndInternshipId(studentId, internshipId);
        if (saved.isPresent()) {
            savedInternshipRepository.delete(saved.get());
            return true;
        }
        return false;
    }

    // Get all saved internships by student
    public List<SavedInternship> getSavedInternshipsByStudent(Long studentId) {
        return savedInternshipRepository.findByStudentId(studentId);
    }

    // Check if internship is saved
    public boolean isSaved(Long studentId, Long internshipId) {
        return savedInternshipRepository.existsByStudentIdAndInternshipId(studentId, internshipId);
    }

    // Count saved internships
    public long countSavedInternships(Long studentId) {
        return savedInternshipRepository.countByStudentId(studentId);
    }
}