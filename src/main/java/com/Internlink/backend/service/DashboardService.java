package com.Internlink.backend.service;

import com.Internlink.backend.dto.RecentActivityDTO;
import com.Internlink.backend.dto.RecommendedInternshipDTO;
import com.Internlink.backend.dto.StudentDashboardResponse;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.repository.InternshipRepository;
import com.Internlink.backend.repository.NotificationRepository;
import com.Internlink.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.catalina.manager.StatusTransformer.formatTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final NotificationRepository notificationRepository;

    public StudentDashboardResponse getStudentDashboard(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Internship> internships =
                internshipRepository.findTop5ByStatusOrderByCreatedAtDesc(
                        InternshipStatus.OPEN);

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

        StudentDashboardResponse response = new StudentDashboardResponse();

        response.setStudentName(student.getEmail());

        response.setNewMatches(recommendedInternships.size());

        response.setRecommendedInternships(recommendedInternships);

        response.setRecentActivities(recentActivities);

        return response;
    }

    private String formatTime(LocalDateTime createdAt) {

        long hours = java.time.Duration
                .between(createdAt, LocalDateTime.now())
                .toHours();

        if (hours < 24) {
            return hours + "h";
        }

        return (hours / 24) + "d";
    }

    private int calculateMatch(Student student, Internship internship) {

        if (student.getSkills() == null
                || internship.getRequiredSkills() == null
                || internship.getRequiredSkills().isEmpty()) {
            return 0;
        }

        long matches = internship.getRequiredSkills()
                .stream()
                .filter(student.getSkills()::contains)
                .count();

        return (int) ((matches * 100) / internship.getRequiredSkills().size());
    }
}