package com.Internlink.backend.controller;

import com.Internlink.backend.entity.UniversitySettings;
import com.Internlink.backend.service.UniversitySettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/universities/{universityId}/settings")
@RequiredArgsConstructor
public class UniversitySettingsController {

    private final UniversitySettingsService settingsService;

    // Get settings
    @GetMapping
    public ResponseEntity<UniversitySettings> getSettings(@PathVariable Long universityId) {
        return ResponseEntity.ok(settingsService.getSettings(universityId));
    }

    // Update preferences (language, appearance, notifications, privacy)
    @PutMapping
    public ResponseEntity<UniversitySettings> updateSettings(
            @PathVariable Long universityId,
            @RequestBody UniversitySettings settings) {
        return ResponseEntity.ok(settingsService.updateSettings(universityId, settings));
    }

    // Send feedback
    @PostMapping("/feedback")
    public ResponseEntity<UniversitySettings> sendFeedback(
            @PathVariable Long universityId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(settingsService.sendFeedback(universityId, body.get("feedback")));
    }
}
