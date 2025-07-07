package com.example.Project.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DailyTransferLogRepository extends MongoRepository<DailyTransferLog, String> {
}
