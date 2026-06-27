package com.Internlink.backend.repository;

import com.Internlink.backend.entity.Application;
import com.Internlink.backend.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find applications by student ID
    List<Application> findByStudentId(Long studentId);

    // Find applications by internship ID
    List<Application> findByInternshipListingId(Long internshipId);

    // Find application by student and internship (check if already applied)
    Optional<Application> findByStudentIdAndInternshipListingId(Long studentId, Long internshipId);

    // Find applications by status
    List<Application> findByStatus(ApplicationStatus status);

    // Find applications by student and status
    List<Application> findByStudentIdAndStatus(Long studentId, ApplicationStatus status);

    // Find applications by internship and status
    List<Application> findByInternshipListingIdAndStatus(Long internshipId, ApplicationStatus status);

    // Count applications for an internship
    long countByInternshipListingId(Long internshipId);

<<<<<<< HEAD
    long countByStudentId(Long studentId);
    long countByStudentIdAndStatus(Long studentId, ApplicationStatus status);


}
=======
    @Query("SELECT a FROM Application a WHERE a.internshipListing.company.id = :companyId")
    List<Application> findByCompanyId(@Param("companyId") Long companyId);
}
>>>>>>> 8f3d157df79001d2f3aaddf1332bb59222401417
