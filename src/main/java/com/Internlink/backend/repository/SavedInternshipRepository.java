package com.Internlink.backend.repository;

import com.Internlink.backend.entity.SavedInternship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedInternshipRepository extends JpaRepository<SavedInternship, Long> {

    // Find saved internships by student
    List<SavedInternship> findByStudentId(Long studentId);

    // Check if student saved internship
    Optional<SavedInternship> findByStudentIdAndInternshipId(Long studentId, Long internshipId);

    // Check if exists
    boolean existsByStudentIdAndInternshipId(Long studentId, Long internshipId);

    // Count saved by student
    long countByStudentId(Long studentId);
}