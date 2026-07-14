package com.Internlink.backend.service;

import com.Internlink.backend.entity.Interview;
import com.Internlink.backend.entity.InterviewStatus;
import com.Internlink.backend.entity.Application;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.repository.InterviewRepository;
import com.Internlink.backend.repository.ApplicationRepository;
import com.Internlink.backend.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final InternshipRepository internshipRepository;

    // Schedule interview
    public Interview scheduleInterview(Long applicationId, Long internshipId, LocalDateTime scheduledDate, String interviewLink) {
        Optional<Application> appOpt = applicationRepository.findById(applicationId);
        Optional<Internship> internshipOpt = internshipRepository.findById(internshipId);

        if (appOpt.isEmpty() || internshipOpt.isEmpty()) {
            return null;
        }

        Interview interview = new Interview();
        interview.setApplication(appOpt.get());
        interview.setInternship(internshipOpt.get());
        interview.setScheduledDate(scheduledDate);
        interview.setInterviewLink(interviewLink);
        interview.setStatus(InterviewStatus.SCHEDULED);

        return interviewRepository.save(interview);
    }

    // Get interview by ID
    public Optional<Interview> getInterviewById(Long id) {
        return interviewRepository.findById(id);
    }

    // Get interviews by student
    public List<Interview> getInterviewsByStudent(Long studentId) {
        return interviewRepository.findByApplication_StudentId(studentId);
    }

    // Get scheduled interviews for student
    public List<Interview> getScheduledInterviewsForStudent(Long studentId) {
        return interviewRepository.findByApplication_StudentIdAndStatus(studentId, InterviewStatus.SCHEDULED);
    }

    // Get interviews by internship
    public List<Interview> getInterviewsByInternship(Long internshipId) {
        return interviewRepository.findByInternshipId(internshipId);
    }

    // Get interviews by application
    public List<Interview> getInterviewsByApplication(Long applicationId) {
        return interviewRepository.findByApplicationId(applicationId);
    }

    // Update interview status
    public Interview updateInterviewStatus(Long interviewId, InterviewStatus status) {
        Optional<Interview> interviewOpt = interviewRepository.findById(interviewId);
        if (interviewOpt.isPresent()) {
            Interview interview = interviewOpt.get();
            interview.setStatus(status);
            return interviewRepository.save(interview);
        }
        return null;
    }

    // Add notes to interview
    public Interview addNotes(Long interviewId, String notes) {
        Optional<Interview> interviewOpt = interviewRepository.findById(interviewId);
        if (interviewOpt.isPresent()) {
            Interview interview = interviewOpt.get();
            interview.setNotes(notes);
            return interviewRepository.save(interview);
        }
        return null;
    }

    // Cancel interview
    public Interview cancelInterview(Long interviewId) {
        return updateInterviewStatus(interviewId, InterviewStatus.CANCELLED);
    }

    // Mark as completed
    public Interview markAsCompleted(Long interviewId) {
        return updateInterviewStatus(interviewId, InterviewStatus.COMPLETED);
    }

    // Mark as no-show
    public Interview markAsNoShow(Long interviewId) {
        return updateInterviewStatus(interviewId, InterviewStatus.NO_SHOW);
    }

    // Get interviews by status
    public List<Interview> getInterviewsByStatus(InterviewStatus status) {
        return interviewRepository.findByStatus(status);
    }

    // Delete interview
    public boolean deleteInterview(Long interviewId) {
        if (interviewRepository.existsById(interviewId)) {
            interviewRepository.deleteById(interviewId);
            return true;
        }
        return false;
    }

    // Count interviews for internship
    public long countInterviewsForInternship(Long internshipId) {
        return interviewRepository.countByInternshipId(internshipId);
    }
}