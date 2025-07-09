package com.example.Project.mongoservice;


import com.example.Project.model.DailyLogs;
import com.example.Project.repository.DailyTransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//handles logic for creating and updating records in MongoDB
@Service
public class DailyLogService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate= LocalDate.now();
    String dateString = localDate.format(formatter);

    @Autowired
    DailyTransferRepo dailyTransferRepo;

    //create new entry
    public void createNew(LocalDate date, int pulledCount, int pushedCount, int deferred)
    {
        DailyLogs dataLog= new DailyLogs();
        dataLog.setDate(dateString);
        dataLog.setDeffered(deferred);
        dataLog.setTimestamp( LocalDateTime.now());
        dataLog.setPulledCount(pulledCount);
        dataLog.setPushedCount(pushedCount);

        dailyTransferRepo.save(dataLog);
    }

    //update existing entry
    public void updateExisiting(LocalDate date, int pulledCount, int pushedCount, int deferred)
    {
       DailyLogs dataLog = dailyTransferRepo.findByRecordDate(dateString).get();
       dataLog.setPushedCount(pushedCount);
       dataLog.setDeffered(deferred);
       dataLog.setTimestamp(LocalDateTime.now());
       dataLog.setPulledCount(pulledCount);

       dailyTransferRepo.save(dataLog);

    }
}
