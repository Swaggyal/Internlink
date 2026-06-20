package com.Internlink.backend.repository;

import com.Internlink.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Find student by email
    Optional<Student> findByEmail(String email);

    // Find student by university
    java.util.List<Student> findByUniversityName(String universityName);

    // Find student by program
    java.util.List<Student> findByProgram(String program);
}
