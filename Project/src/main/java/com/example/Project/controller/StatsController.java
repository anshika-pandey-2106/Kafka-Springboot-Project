package com.example.Project.controller;


import com.example.Project.kafka.ScheduledKafkaProcessor;
import com.example.Project.model.DailyLogs;
import com.example.Project.mongoservice.DailyLogService;
import com.example.Project.repository.DailyTransferRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/kafka/home/stats")
public class StatsController {
    private DailyLogService dailyLogService;
    private final Logger log = LoggerFactory.getLogger(StatsController.class);
    @Autowired
    private DailyTransferRepo dailyTransferRepo;

    //endpoint for single date
    @CrossOrigin(origins= "http://localhost:3000")
    @GetMapping
    public ResponseEntity<DailyLogs> data(@RequestParam("date") String dateParam){
        log.info(dateParam);

        Optional<DailyLogs> rec= dailyTransferRepo.findByRecordDate(dateParam);
        if(!rec.isPresent()){
            return ResponseEntity.ok(null);
        }
        log.info(String.format(String.valueOf(rec.get().getRecordDate())));
        return ResponseEntity.ok(rec.get());
    }




   }


