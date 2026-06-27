package com.Internlink.backend.controller;

import com.Internlink.backend.dto.CompanyProfileRequest;
import com.Internlink.backend.entity.Company;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.dto.InternshipRequest;
import com.Internlink.backend.service.ApplicationService;
import com.Internlink.backend.service.CompanyService;
import com.Internlink.backend.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController{

    private final CompanyService companyService;
    private final InternshipService internshipService;
    private final ApplicationService applicationService;



    @PostMapping
    public ResponseEntity<Company> register(@RequestBody Company company) {
        Company created = companyService.registerCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getProfile(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) {
        return ResponseEntity.ok(companyService.updateProfile(id, company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/internships")
    public ResponseEntity<?> createInternship(
            @PathVariable Long id,
            @RequestBody InternshipRequest request) {

        Internship internship = internshipService.createInternship(id, request.toEntity());

        return ResponseEntity.ok(internship);
    }

    @PutMapping("/{id}/internships/{internshipId}")
    public ResponseEntity<?> updateInternship(
            @PathVariable Long id,
            @PathVariable Long internshipId,
            @RequestBody InternshipRequest request) {

        return ResponseEntity.ok(
                internshipService.updateInternship(
                        id,
                        internshipId,
                        request.toEntity()
                )
        );
    }

    @DeleteMapping("/{id}/internships/{internshipId}")
    public ResponseEntity<Void> deleteInternship(
            @PathVariable Long id,
            @PathVariable Long internshipId) {

        internshipService.deleteInternship(id, internshipId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/applications")
    public ResponseEntity<?> getApplications(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                applicationService.findByCompanyId(id)
        );
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getProfile(
            @PathVariable Long id) {

        return ResponseEntity.ok(companyService.getProfile(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @RequestBody CompanyProfileRequest request) {

        return ResponseEntity.ok(
                companyService.updateProfile(id, request.toEntity())
        );
    }
}
