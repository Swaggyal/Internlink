package com.Internlink.backend.service;

import com.Internlink.backend.entity.University;
import com.Internlink.backend.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public University registerUniversity(University university) {
        if (universityRepository.existsByUniversityEmail(university.getUniversityEmail())) {
            throw new IllegalArgumentException("A university with this email already exists.");
        }
        return universityRepository.save(university);
    }

    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    public University getUniversityById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found with id: " + id));
    }

    public University updateUniversity(Long id, University updatedData) {
        University existing = getUniversityById(id);
        existing.setUniversityName(updatedData.getUniversityName());
        existing.setUniversityEmail(updatedData.getUniversityEmail());
        existing.setUniversityPhone(updatedData.getUniversityPhone());
        existing.setWebsite(updatedData.getWebsite());
        existing.setCareerOfficerName(updatedData.getCareerOfficerName());
        existing.setCareerOfficerEmail(updatedData.getCareerOfficerEmail());
        return universityRepository.save(existing);
    }

    public void deleteUniversity(Long id) {
        universityRepository.deleteById(id);
    }
}
