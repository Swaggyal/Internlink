package com.Internlink.backend.repository;

import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

    List<Internship> findByStatus(InternshipStatus status);

    List<Internship> findByCompanyName(String companyName);

    List<Internship> findByIndustry(String industry);

    List<Internship> findByIsRemote(boolean isRemote);

    List<Internship> findByTitleContainingIgnoreCase(String keyword);
}