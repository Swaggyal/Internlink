
package com.Internlink.backend.repository;

import com.Internlink.backend.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    Optional<University> findByUniversityEmail(String universityEmail);

    Optional<University> findByUniversityName(String universityName);

    boolean existsByUniversityEmail(String universityEmail);
}