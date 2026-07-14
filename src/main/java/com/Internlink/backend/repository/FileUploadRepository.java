package com.Internlink.backend.repository;

import com.Internlink.backend.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

    // Find files by user ID
    List<FileUpload> findByUserId(Long userId);

    // Find files by user and type
    Optional<FileUpload> findFirstByUserIdAndFileType(Long userId, String fileType);
    // Find specific file
    Optional<FileUpload> findByIdAndUserId(Long fileId, Long userId);
}