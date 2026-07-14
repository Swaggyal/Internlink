package com.Internlink.backend.service;

import com.Internlink.backend.entity.FileUpload;
import com.Internlink.backend.repository.FileUploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileUploadRepository fileUploadRepository;

    @Value("${file.upload-dir:uploads/}")
    private String uploadDir;

    // Upload file
    public FileUpload uploadFile(MultipartFile file, Long userId, String fileType) throws IOException {
        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Create uploads directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Save file
        Files.write(filePath, file.getBytes());

        // Save metadata to database
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(file.getOriginalFilename());
        fileUpload.setFilePath(filePath.toString());
        fileUpload.setFileType(fileType);
        fileUpload.setFileSize(file.getSize());
        fileUpload.setUserId(userId);

        return fileUploadRepository.save(fileUpload);
    }

    // Get file by ID
    public Optional<FileUpload> getFileById(Long fileId) {
        return fileUploadRepository.findById(fileId);
    }

    // Get files by user
    public List<FileUpload> getFilesByUser(Long userId) {
        return fileUploadRepository.findByUserId(userId);
    }

    // Get resume by user
    public Optional<FileUpload> getResumeByUser(Long userId) {
        return fileUploadRepository.findFirstByUserIdAndFileType(userId, "RESUME");
    }

    // Get cover letter by user
    public Optional<FileUpload> getCoverLetterByUser(Long userId) {
        return fileUploadRepository.findFirstByUserIdAndFileType(userId, "COVER_LETTER");
    }

    // Delete file
    public boolean deleteFile(Long fileId, Long userId) throws IOException {
        Optional<FileUpload> fileOpt = fileUploadRepository.findByIdAndUserId(fileId, userId);
        if (fileOpt.isPresent()) {
            FileUpload file = fileOpt.get();
            Files.deleteIfExists(Paths.get(file.getFilePath()));
            fileUploadRepository.delete(file);
            return true;
        }
        return false;
    }
}