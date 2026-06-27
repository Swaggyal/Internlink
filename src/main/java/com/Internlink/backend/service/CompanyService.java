package com.Internlink.backend.service;

import com.Internlink.backend.entity.Company;
import com.Internlink.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company registerCompany(Company company) {
        if (companyRepository.existsByCompanyEmail(company.getCompanyEmail())) {
            throw new IllegalArgumentException("A company with this email already exists.");
        }
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getProfile(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    public Company updateProfile(Long id, Company updatedData) {
        Company existing = getProfile(id);
        existing.setCompanyName(updatedData.getCompanyName());
        existing.setCompanyEmail(updatedData.getCompanyEmail());
        existing.setCompanyPhone(updatedData.getCompanyPhone());
        existing.setIndustry(updatedData.getIndustry());
        existing.setHqLocation(updatedData.getHqLocation());
        existing.setDescription(updatedData.getDescription());
        existing.setWebsite(updatedData.getWebsite());
        existing.setCompanySize(updatedData.getCompanySize());
        return companyRepository.save(existing);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
