package com.Internlink.backend.service;

import com.Internlink.backend.entity.Company;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import com.Internlink.backend.repository.CompanyRepository;
import com.Internlink.backend.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;

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



    public Internship createInternship(Long companyId, Internship internship) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        internship.setCompany(company);

        return internshipRepository.save(internship);
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

    public Internship updateInternship(
            Long companyId,
            Long internshipId,
            Internship updated) {

        Internship internship = internshipRepository
                .findByIdAndCompanyId(internshipId, companyId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        internship.setTitle(updated.getTitle());
        internship.setDescription(updated.getDescription());
        internship.setLocation(updated.getLocation());
        internship.setDuration(updated.getDuration());
        internship.setRequirements(updated.getRequirements());
        internship.setDeadline(updated.getDeadline());

        return internshipRepository.save(internship);
    }

    public void deleteInternship(Long companyId, Long internshipId) {

        Internship internship = internshipRepository
                .findByIdAndCompanyId(internshipId, companyId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        internshipRepository.delete(internship);
    }
}