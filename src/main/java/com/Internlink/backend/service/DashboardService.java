package com.Internlink.backend.service;

import com.Internlink.backend.dto.*;
import com.Internlink.backend.entity.*;
import com.Internlink.backend.repository.*;
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
    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;
    private final UniversityRepository universityRepository;


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

    // Get complete company dashboard
    public CompanyDashboardResponse getCompanyDashboard(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        List<Internship> postedInternships = internshipRepository.findByCompanyId(companyId);

        List<InternshipDTO> internshipDTOs = postedInternships.stream()
                .map(internship -> {
                    long appCount = applicationRepository.countByInternshipListingId(internship.getId());
                    return new InternshipDTO(
                            internship.getId(),
                            internship.getTitle(),
                            internship.getLocation(),
                            internship.getPay(),
                            internship.getStatus().toString(),
                            (int) appCount
                    );
                })
                .collect(Collectors.toList());

        List<Application> allApplications = postedInternships.stream()
                .flatMap(internship -> applicationRepository.findByInternshipListingId(internship.getId()).stream())
                .collect(Collectors.toList());

        long pendingCount = allApplications.stream()
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING)
                .count();

        long acceptedCount = allApplications.stream()
                .filter(app -> app.getStatus() == ApplicationStatus.ACCEPTED)
                .count();

        CompanyDashboardResponse response = new CompanyDashboardResponse();
        response.setCompanyName(company.getCompanyName());
        response.setTotalInternships(postedInternships.size());
        response.setTotalApplications(allApplications.size());
        response.setPostedInternships(internshipDTOs);
        response.setPendingApplications((int) pendingCount);
        response.setAcceptedApplications((int) acceptedCount);

        return response;
    }

    // Get complete university dashboard
    public UniversityDashboardResponse getUniversityDashboard(Long universityId) {

        // Fetch university from database
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new RuntimeException("University not found"));

        // Fetch students belonging to this university
        List<Student> students =
                studentRepository.findByUniversityName(university.getUniversityName());

        // Count internships that are currently open
        int availableInternships =
                internshipRepository.findByStatus(InternshipStatus.OPEN).size();

        // Count total applications submitted by these students
        int totalApplications = students.stream()
                .mapToInt(student ->
                        applicationRepository.findByStudentId(student.getId()).size())
                .sum();

        // Count students who have at least one accepted application
        int placedStudents = (int) students.stream()
                .filter(student ->
                        applicationRepository.findByStudentId(student.getId())
                                .stream()
                                .anyMatch(app ->
                                        app.getStatus() == ApplicationStatus.ACCEPTED))
                .count();

        // Sort students by newest first
        List<StudentSummary> recentStudents = students.stream()
                .sorted((s1, s2) -> s2.getCreatedAt().compareTo(s1.getCreatedAt()))
                .limit(5)
                .map(student -> new StudentSummary(
                        student.getId(),
                        student.getEmail(),
                        student.getProgram(),
                        student.getYearOfStudy(),
                        student.getProfileStrength()
                ))
                .toList();

        // Build response
        UniversityDashboardResponse response =
                new UniversityDashboardResponse();

        response.setUniversityName(university.getUniversityName());
        response.setTotalStudents(students.size());
        response.setAvailableInternships(availableInternships);
        response.setTotalApplications(totalApplications);
        response.setPlacedStudents(placedStudents);
        response.setRecentStudents(recentStudents);

        return response;
    }
}