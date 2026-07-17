package com.Internlink.backend.service;

import com.Internlink.backend.dto.RecentActivityDTO;
import com.Internlink.backend.dto.RecommendedInternshipDTO;
import com.Internlink.backend.dto.StudentDashboardResponse;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import com.Internlink.backend.entity.SavedInternship;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.repository.InternshipRepository;
import com.Internlink.backend.repository.NotificationRepository;
import com.Internlink.backend.repository.SavedInternshipRepository;
import com.Internlink.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final NotificationRepository notificationRepository;
    private final SavedInternshipRepository savedInternshipRepository;

    // Get complete student dashboard with all data
    public StudentDashboardResponse getStudentDashboard(Long studentId) {

        // Fetch student from database
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Get top 5 open internships (recommended)
        List<Internship> internships =
                internshipRepository.findTop5ByStatusOrderByCreatedAtDesc(
                        InternshipStatus.OPEN);

        // Convert internships to recommended DTOs with match percentage
        List<RecommendedInternshipDTO> recommendedInternships =
                internships.stream()
                        .limit(5)
                        .map(i -> new RecommendedInternshipDTO(
                                i.getId(),
                                i.getTitle(),
                                i.getCompanyName(),
                                i.getLocation(),
                                i.getPay(),
                                i.getDuration(),
                                calculateMatch(student, i)
                        ))
                        .collect(Collectors.toList());

        // Get top 5 recent activities/notifications for student
        List<RecentActivityDTO> recentActivities =
                notificationRepository
                        .findTop5ByStudentIdOrderByCreatedAtDesc(studentId)
                        .stream()
                        .map(notification -> new RecentActivityDTO(
                                notification.getType(),
                                notification.getMessage(),
                                formatTime(notification.getCreatedAt())
                        ))
                        .toList();

        // Get all saved internships for student
        List<SavedInternship> savedInternships =
                savedInternshipRepository.findByStudentId(studentId);

        // Convert saved internships to DTOs
        List<RecommendedInternshipDTO> savedInternshipDTOs =
                savedInternships.stream()
                        .map(saved -> {
                            Internship internship = saved.getInternship();
                            return new RecommendedInternshipDTO(
                                    internship.getId(),
                                    internship.getTitle(),
                                    internship.getCompanyName(),
                                    internship.getLocation(),
                                    internship.getPay(),
                                    internship.getDuration(),
                                    calculateMatch(student, internship)
                            );
                        })
                        .collect(Collectors.toList());

        // Build dashboard response with all data
        StudentDashboardResponse response = new StudentDashboardResponse();
        response.setStudentName(student.getEmail());
        response.setNewMatches(recommendedInternships.size());
        response.setRecommendedInternships(recommendedInternships);
        response.setRecentActivities(recentActivities);
        response.setSavedInternships(savedInternshipDTOs);
        response.setSavedCount(savedInternships.size());

        return response;
    }

    // Format time difference between notification and now
    private String formatTime(LocalDateTime createdAt) {
        long hours = java.time.Duration
                .between(createdAt, LocalDateTime.now())
                .toHours();

        if (hours < 24) {
            return hours + "h";
        }

        return (hours / 24) + "d";
    }

    // Calculate skill match percentage between student and internship
    private int calculateMatch(Student student, Internship internship) {

        // Return 0 if no skills or requirements
        if (student.getSkills() == null
                || internship.getRequiredSkills() == null
                || internship.getRequiredSkills().isEmpty()) {
            return 0;
        }

        // Count matching skills
        long matches = internship.getRequiredSkills()
                .stream()
                .filter(student.getSkills()::contains)
                .count();

        // Calculate percentage match
        return (int) ((matches * 100) / internship.getRequiredSkills().size());
    }
}