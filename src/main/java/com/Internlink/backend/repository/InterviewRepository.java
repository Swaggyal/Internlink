package com.Internlink.backend.repository;

import com.Internlink.backend.entity.Interview;
import com.Internlink.backend.entity.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // Find interviews by application
    List<Interview> findByApplicationId(Long applicationId);

    // Find interviews by internship
    List<Interview> findByInternshipId(Long internshipId);

    // Find interviews by student (through application)
    List<Interview> findByApplication_StudentId(Long studentId);

    // Find interviews by status
    List<Interview> findByStatus(InterviewStatus status);

    // Find scheduled interviews for student
    List<Interview> findByApplication_StudentIdAndStatus(Long studentId, InterviewStatus status);

    // Find specific interview
    Optional<Interview> findByIdAndApplication_StudentId(Long interviewId, Long studentId);

    // Count interviews for internship
    long countByInternshipId(Long internshipId);
}