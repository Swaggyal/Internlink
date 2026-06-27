package com.Internlink.backend.service;

class StudentStatsResponse {
    private long appliedCount;
    private long interviewsCount;
    private long offersCount;

    public StudentStatsResponse(long appliedCount, long interviewsCount, long offersCount) {
        this.appliedCount = appliedCount;
        this.interviewsCount = interviewsCount;
        this.offersCount = offersCount;
    }

    // Getters
    public long getAppliedCount() { return appliedCount; }
    public long getInterviewsCount() { return interviewsCount; }
    public long getOffersCount() { return offersCount; }
}
