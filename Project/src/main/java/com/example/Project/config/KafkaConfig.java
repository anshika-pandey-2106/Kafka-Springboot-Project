package com.example.Project.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaConfig {

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Bean
    public KafkaConsumer<String, String> manualKafkaConsumer() {
        return (KafkaConsumer<String, String>) consumerFactory.createConsumer();
    }
}
