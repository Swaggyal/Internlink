package com.Internlink.backend.service;

import com.Internlink.backend.entity.University;
import com.Internlink.backend.entity.UniversitySettings;
import com.Internlink.backend.repository.UniversityRepository;
import com.Internlink.backend.repository.UniversitySettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversitySettingsService {

    private final UniversitySettingsRepository settingsRepository;
    private final UniversityRepository universityRepository;

    public UniversitySettings getSettings(Long universityId) {
        return settingsRepository.findByUniversityId(universityId)
                .orElseGet(() -> createDefaultSettings(universityId));
    }

    public UniversitySettings updateSettings(Long universityId, UniversitySettings updatedSettings) {
        UniversitySettings existing = getSettings(universityId);
        existing.setLanguage(updatedSettings.getLanguage());
        existing.setAppearance(updatedSettings.getAppearance());
        existing.setEmailNotifications(updatedSettings.isEmailNotifications());
        existing.setPushNotifications(updatedSettings.isPushNotifications());
        existing.setProfileVisible(updatedSettings.isProfileVisible());
        existing.setShowStudentCount(updatedSettings.isShowStudentCount());
        return settingsRepository.save(existing);
    }

    public UniversitySettings sendFeedback(Long universityId, String feedback) {
        UniversitySettings existing = getSettings(universityId);
        existing.setLastFeedback(feedback);
        return settingsRepository.save(existing);
    }

    private UniversitySettings createDefaultSettings(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new RuntimeException("University not found with id: " + universityId));
        UniversitySettings settings = new UniversitySettings();
        settings.setUniversity(university);
        settings.setLanguage("English");
        settings.setAppearance("Light");
        settings.setEmailNotifications(true);
        settings.setPushNotifications(true);
        settings.setProfileVisible(true);
        settings.setShowStudentCount(true);
        return settingsRepository.save(settings);
    }
}
