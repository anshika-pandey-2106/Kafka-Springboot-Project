package com.example.Project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("kafkastats")
public class DailyLogs {

    @Id
    String id;
    @Indexed(unique = true)
    String recordDate;
    int pulledCount;
    int pushedCount;
    int deffered;
    LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getPushedCount() {
        return pushedCount;
    }

    public void setPushedCount(int pushedCount) {
        this.pushedCount = pushedCount;
    }

    public int getPulledCount() {
        return pulledCount;
    }

    public void setPulledCount(int pulledCount) {
        this.pulledCount = pulledCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeffered() {
        return deffered;
    }

    public void setDeffered(int deffered) {
        this.deffered = deffered;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String date) {
        this.recordDate = date;
    }


}
