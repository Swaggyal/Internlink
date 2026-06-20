package com.Internlink.backend.service;

import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import com.Internlink.backend.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;

    public Internship createInternship(Internship internship) {
        internship.setStatus(InternshipStatus.OPEN);
        return internshipRepository.save(internship);
    }

    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Internship getInternshipById(Long id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + id));
    }

    public List<Internship> getOpenInternships() {
        return internshipRepository.findByStatus(InternshipStatus.OPEN);
    }

    public List<Internship> searchByKeyword(String keyword) {
        return internshipRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Internship> getByIndustry(String industry) {
        return internshipRepository.findByIndustry(industry);
    }

    public List<Internship> getRemoteInternships() {
        return internshipRepository.findByIsRemote(true);
    }

    public Internship updateInternship(Long id, Internship updatedData) {
        Internship existing = getInternshipById(id);
        existing.setTitle(updatedData.getTitle());
        existing.setCompanyName(updatedData.getCompanyName());
        existing.setLocation(updatedData.getLocation());
        existing.setPay(updatedData.getPay());
        existing.setDuration(updatedData.getDuration());
        existing.setDescription(updatedData.getDescription());
        existing.setRequiredSkills(updatedData.getRequiredSkills());
        existing.setIndustry(updatedData.getIndustry());
        existing.setRemote(updatedData.isRemote());
        existing.setStatus(updatedData.getStatus());
        return internshipRepository.save(existing);
    }

    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);
    }
}