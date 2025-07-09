package com.example.Project.repository;

import com.example.Project.model.DailyLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;


public interface DailyTransferRepo extends MongoRepository<DailyLogs, String> {

    Optional<DailyLogs> findByRecordDate(String date);
    boolean existsByDate(LocalDate date);

}
