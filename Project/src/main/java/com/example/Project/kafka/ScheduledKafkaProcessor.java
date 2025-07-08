package com.example.Project.kafka;

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
import java.util.*;

@Service
public class ScheduledKafkaProcessor {

    @Autowired
    private KafkaConsumer<String, String> manualKafkaConsumer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final Logger log = LoggerFactory.getLogger(ScheduledKafkaProcessor.class);

    @Scheduled(cron = "0 26 16 * * *", zone = "Asia/Kolkata") // 3:02 PM IST
    public void processMessages() {
        manualKafkaConsumer.subscribe(Collections.singletonList("intermediateTopic"));
        Set<TopicPartition> partitions = manualKafkaConsumer.assignment();
        log.info("Assigned partitions: {}", partitions);

        List<ConsumerRecord<String, String>> allMessages = new ArrayList<>();
        long endTime = System.currentTimeMillis() + (1 * 1 * 100); // 15 minutes
        log.info(String.format("step1"));
        // Step 1: Pull all uncommitted messages
        while (System.currentTimeMillis() < endTime) {
            ConsumerRecords<String, String> records = manualKafkaConsumer.poll(Duration.ofSeconds(5));
            log.info(String.format("pulled"));
            if (records.isEmpty())
            {
                log.info(String.format("empty"));
                break;
            }
            records.forEach(allMessages::add);
        }

        int pulledCount = allMessages.size();
        int pushedCount = 0;
        Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

        // Step 2: Push one by one until time runs out
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

        // Step 3: Commit only pushed messages
        try {
            manualKafkaConsumer.commitSync(offsetsToCommit);
        } catch (Exception e) {
            log.error("Offset commit failed", e);
        }

        log.info("Pulled: {}, Pushed: {}, Deferred: {}", pulledCount, pushedCount, pulledCount - pushedCount);
    }
}
