package com.example.Project.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
@Service
public class KafkaReceiverService {

    @KafkaListener(topics = "intermediateTopic", groupId = "myGroup", autoStartup = "false")
    public List<String> pullMessages() {
        // This method should be customized to pull messages manually
        // You might need to use KafkaConsumer API directly instead of @KafkaListener
        return new ArrayList<>(); // Replace with actual logic
    }
}
*/