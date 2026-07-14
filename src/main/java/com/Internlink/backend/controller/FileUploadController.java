package com.Internlink.backend.controller;

import com.Internlink.backend.entity.FileUpload;
import com.Internlink.backend.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    // Upload resume
    @PostMapping("/upload/resume/{userId}")
    public ResponseEntity<FileUpload> uploadResume(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            FileUpload uploadedFile = fileUploadService.uploadFile(file, userId, "RESUME");
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Upload cover letter
    @PostMapping("/upload/cover-letter/{userId}")
    public ResponseEntity<FileUpload> uploadCoverLetter(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            FileUpload uploadedFile = fileUploadService.uploadFile(file, userId, "COVER_LETTER");
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get file by ID
    @GetMapping("/{fileId}")
    public ResponseEntity<FileUpload> getFile(@PathVariable Long fileId) {
        Optional<FileUpload> file = fileUploadService.getFileById(fileId);
        return file.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all files by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FileUpload>> getFilesByUser(@PathVariable Long userId) {
        List<FileUpload> files = fileUploadService.getFilesByUser(userId);
        return ResponseEntity.ok(files);
    }

    // Get resume by user
    @GetMapping("/resume/{userId}")
    public ResponseEntity<FileUpload> getResume(@PathVariable Long userId) {
        Optional<FileUpload> resume = fileUploadService.getResumeByUser(userId);
        return resume.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get cover letter by user
    @GetMapping("/cover-letter/{userId}")
    public ResponseEntity<FileUpload> getCoverLetter(@PathVariable Long userId) {
        Optional<FileUpload> coverLetter = fileUploadService.getCoverLetterByUser(userId);
        return coverLetter.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete file
    @DeleteMapping("/{fileId}/user/{userId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long fileId,
            @PathVariable Long userId) {
        try {
            if (fileUploadService.deleteFile(fileId, userId)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}