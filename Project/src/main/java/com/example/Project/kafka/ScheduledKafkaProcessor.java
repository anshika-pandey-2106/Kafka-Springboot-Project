package com.example.Project.kafka;

import com.example.Project.model.DailyLogs;
import com.example.Project.mongoservice.DailyLogService;
import com.example.Project.repository.DailyTransferRepo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ScheduledKafkaProcessor {

    //Kafka consumer that reads messages with String key and value
    @Autowired
    private KafkaConsumer<String, String> manualKafkaConsumer;


    //Used to send the message to another topic
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    DailyLogService dailyLogService;
    @Autowired
    DailyTransferRepo dailyTransferRepo;

    private final Logger log = LoggerFactory.getLogger(ScheduledKafkaProcessor.class);

    @Scheduled(cron = "50 43 14 * * *", zone = "Asia/Kolkata") // 3:02 PM IST
    public void processMessages() {

        //subscribe to topic
        manualKafkaConsumer.subscribe(Collections.singletonList("intermediateTopic"));


        //Set<TopicPartition> partitions = manualKafkaConsumer.assignment();
        //log.info("Assigned partitions: {}", partitions);

        //to store all the messages pulled
        List<ConsumerRecord<String, String>> allMessages = new ArrayList<>();

        //run consumer for 100ms
        long endTime = System.currentTimeMillis() + (1 * 10 * 1000);


        //Pull all uncommitted messages
        while (System.currentTimeMillis() < endTime) {
            ConsumerRecords<String, String> records = manualKafkaConsumer.poll(Duration.ofSeconds(5));

            if (records.isEmpty())
            {
                log.info(String.format("empty"));
                break;
            }
            //Add pulled messages to ArrayList
            records.forEach(allMessages::add);
        }
        log.info(String.format("Pulled messages"));

        int pulledCount = allMessages.size();
        int pushedCount = 0;

        Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

        //Push messages one by one before time runs out
        for (ConsumerRecord<String, String> record : allMessages) {
            if (System.currentTimeMillis() >= endTime) break;

            try {
                kafkaTemplate.send("finalTopic", record.value()).get(); // Wait for send to complete
                pushedCount++;
                offsetsToCommit.put(
                        new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1)
                );
            } catch (Exception e) {
                log.error("Failed to push message", e);
            }
        }
        log.info(String.format("Pushed messages ")); //for debugging

        //Commit only pushed messages
        try {
            manualKafkaConsumer.commitSync(offsetsToCommit);
        } catch (Exception e) {
            log.error("Offset commit failed", e);
        }
        log.info(String.format("Committed offsets"));
        int deferred= pulledCount-pushedCount;

        //Mongodb
        //format the Date such that only date is stored (not time)
        //mongodb stores date with time and timezone,
        //so store only date as a string, easier to query
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate= LocalDate.now();
        String dateString = localDate.format(formatter);

        if(dailyTransferRepo.findByRecordDate(dateString).isPresent())
        {   //update existing record
            dailyLogService.updateExisiting(LocalDate.now(), pulledCount, pushedCount, deferred);
            log.info(String.format("Updated existing record"));
        } else{
            //create new record
            dailyLogService.createNew(LocalDate.now(), pulledCount, pushedCount, deferred);
            log.info(String.format("Created new record"));
        }

    }
}
