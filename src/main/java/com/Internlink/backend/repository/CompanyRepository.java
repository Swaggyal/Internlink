package com.Internlink.backend.repository;

import com.Internlink.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findById(Long id);

    Optional<Company> findByCompanyEmail(String companyEmail);

    Optional<Company> findByCompanyName(String companyName);

    boolean existsByCompanyEmail(String companyEmail);
}
