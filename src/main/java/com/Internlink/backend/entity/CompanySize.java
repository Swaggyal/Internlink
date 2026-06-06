package com.Internlink.backend.entity;

public enum CompanySize {
    SIZE_1_10("1-10 employees"),
    SIZE_11_50("11-50 employees"),
    SIZE_51_200("51-200 employees"),
    SIZE_200_PLUS("200+ employees");

    private final String description;

    CompanySize(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}