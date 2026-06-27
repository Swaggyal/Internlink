package com.Internlink.backend.dto;

import com.Internlink.backend.entity.Company;
import com.Internlink.backend.entity.CompanySize;

public record CompanyProfileRequest(
        String companyName,
        String companyPhone,
        String industry,
        String location,
        String hqLocation,
        String description,
        String website,
        CompanySize companySize,
        String contactPersonName,
        String contactPersonPhone
) {

    public Company toEntity() {

        Company company = new Company();

        company.setCompanyName(companyName);
        company.setCompanyPhone(companyPhone);
        company.setIndustry(industry);
        company.setHqLocation(location);
        company.setDescription(description);
        company.setWebsite(website);
        company.setCompanySize(companySize);
        company.setContactPersonName(contactPersonName);
        company.setContactPersonPhone(contactPersonPhone);

        return company;
    }
}
