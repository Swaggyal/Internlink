package com.Internlink.backend.service;

import com.Internlink.backend.dto.CareerServicesRequest;
import com.Internlink.backend.entity.Internship;
import com.Internlink.backend.entity.InternshipStatus;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.entity.University;
import com.Internlink.backend.repository.InternshipRepository;
import com.Internlink.backend.repository.StudentRepository;
import com.Internlink.backend.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;


    public University registerUniversity(University university) {
        if (universityRepository.existsByUniversityEmail(university.getUniversityEmail())) {
            throw new IllegalArgumentException("A university with this email already exists.");
        }
        return universityRepository.save(university);
    }

    public University updateInstitutionDetails(Long id, University updatedData) {
        University existing = getUniversityById(id);
        existing.setInstitutionType(updatedData.getInstitutionType());
        existing.setCountry(updatedData.getCountry());
        existing.setCity(updatedData.getCity());
        existing.setNumberOfStudents(updatedData.getNumberOfStudents());
        existing.setAcademicPrograms(updatedData.getAcademicPrograms());
        return universityRepository.save(existing);
    }

    public University updateCareerServices(Long id, CareerServicesRequest request) {
        University existing = getUniversityById(id);
        existing.setCareerServicesContactName(request.getCareerServicesContactName());
        existing.setDepartmentEmail(request.getDepartmentEmail());
        existing.setPlacementOfficeAddress(request.getPlacementOfficeAddress());
        existing.setPlacementOfficePhone(request.getPlacementOfficePhone());
        existing.setPlacementOfficeHours(request.getPlacementOfficeHours());
        existing.setInternshipCoordinatorName(request.getInternshipCoordinatorName());
        existing.setInternshipCoordinatorEmail(request.getInternshipCoordinatorEmail());
        return universityRepository.save(existing);
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
        existing.setLogoUrl(updatedData.getLogoUrl());
        return universityRepository.save(existing);
    }

    public void deleteUniversity(Long id) {
        universityRepository.deleteById(id);
    }

    public List<Student> getStudentsByUniversity(Long id) {
        University university = getUniversityById(id);
        return studentRepository.findByUniversityName(university.getUniversityName());
    }

    public Map<String, Object> getUniversityStats(Long id) {
        University university = getUniversityById(id);
        List<Student> students = studentRepository.findByUniversityName(university.getUniversityName());
        Map<String, Object> stats = new HashMap<>();
        stats.put("universityName", university.getUniversityName());
        stats.put("totalStudents", students.size());
        stats.put("academicPrograms", university.getAcademicPrograms());
        stats.put("city", university.getCity());
        stats.put("country", university.getCountry());
        return stats;
    }

    public List<Internship> getInternshipsForUniversity(Long universityId) {
        // Get all open internships that students can view
        return internshipRepository.findByStatus(InternshipStatus.OPEN);
    }
}