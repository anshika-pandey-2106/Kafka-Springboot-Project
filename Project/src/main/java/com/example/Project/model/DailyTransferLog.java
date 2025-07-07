package com.example.Project.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("daily_transfer_log")
public class DailyTransferLog {
    @Id
    private String date; // Format: yyyy-MM-dd
    private int pulledCount;
    private int forwardedCount;
    private int remainingCount;
    private LocalDateTime timestamp;

    // Getters and setters

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(int remainingCount) {
        this.remainingCount = remainingCount;
    }

    public int getPulledCount() {
        return pulledCount;
    }

    public void setPulledCount(int pulledCount) {
        this.pulledCount = pulledCount;
    }

    public int getForwardedCount() {
        return forwardedCount;
    }

    public void setForwardedCount(int forwardedCount) {
        this.forwardedCount = forwardedCount;
    }
}
