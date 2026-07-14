package com.Internlink.backend.repository;

import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

    List<Internship> findByStatus(InternshipStatus status);

    List<Internship> findByCompanyName(String companyName);

    List<Internship> findByIndustry(String industry);

    List<Internship> findByIsRemote(boolean isRemote);

    List<Internship> findByTitleContainingIgnoreCase(String keyword);

    List<Internship> findByCompanyId(Long companyId);
    Optional<Internship> findByIdAndCompanyId(Long internshipId, Long companyId);

    List<Internship> findTop5ByStatusOrderByCreatedAtDesc(InternshipStatus status);

}