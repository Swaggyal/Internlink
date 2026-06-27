package com.Internlink.backend.service;

import com.Internlink.backend.entity.Application;
import com.Internlink.backend.entity.ApplicationStatus;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.repository.ApplicationRepository;
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
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;

    // Apply for an internship
    public Application applyForInternship(Long studentId, Long internshipId, String resumeUrl, String coverLetterUrl) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Internship> internshipOpt = internshipRepository.findById(internshipId);

        if (studentOpt.isEmpty() || internshipOpt.isEmpty()) {
            return null; // Student or internship not found
        }

        // Check if student already applied
        Optional<Application> existingApp = applicationRepository.findByStudentIdAndInternshipListingId(studentId, internshipId);
        if (existingApp.isPresent()) {
            return null; // Already applied
        }

        Application application = new Application();
        application.setStudent(studentOpt.get());
        application.setInternshipListing(internshipOpt.get());
        application.setResumeUrl(resumeUrl);
        application.setCoverLetterUrl(coverLetterUrl);
        application.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    // Get application by ID
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    // Get all applications by student
    public List<Application> getApplicationsByStudent(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    // Get all applications for an internship
    public List<Application> getApplicationsByInternship(Long internshipId) {
        return applicationRepository.findByInternshipListingId(internshipId);
    }

    // Get applications by status
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    // Get student's applications by status
    public List<Application> getStudentApplicationsByStatus(Long studentId, ApplicationStatus status) {
        return applicationRepository.findByStudentIdAndStatus(studentId, status);
    }

    // Get internship applications by status
    public List<Application> getInternshipApplicationsByStatus(Long internshipId, ApplicationStatus status) {
        return applicationRepository.findByInternshipListingIdAndStatus(internshipId, status);
    }

    // Update application status
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {
        Optional<Application> appOpt = applicationRepository.findById(applicationId);

        if (appOpt.isPresent()) {
            Application application = appOpt.get();
            application.setStatus(newStatus);
            return applicationRepository.save(application);
        }
        return null;
    }

    // Withdraw application
    public Application withdrawApplication(Long applicationId) {
        return updateApplicationStatus(applicationId, ApplicationStatus.WITHDRAWN);
    }

    // Check if student already applied
    public boolean hasStudentApplied(Long studentId, Long internshipId) {
        return applicationRepository.findByStudentIdAndInternshipListingId(studentId, internshipId).isPresent();
    }

    // Count applications for an internship
    public long countApplicationsForInternship(Long internshipId) {
        return applicationRepository.countByInternshipListingId(internshipId);
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    //Get all applicants for a company
    public List<Application> findByCompanyId(Long companyId) {

        return applicationRepository.findByCompanyId(companyId);
    }
}