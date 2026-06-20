package com.Internlink.backend.repository;

import com.Internlink.backend.entity.UniversitySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UniversitySettingsRepository extends JpaRepository<UniversitySettings, Long> {

    Optional<UniversitySettings> findByUniversityId(Long universityId);
}
