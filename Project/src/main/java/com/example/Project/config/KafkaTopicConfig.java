package com.example.Project.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    //creates the intermediate topic
    @Bean
    public NewTopic intermediateTopic(){
        return TopicBuilder.name("intermediateTopic").build();
    }
    @Bean NewTopic finalTopic(){
        return TopicBuilder.name("finalTopic").build();
    }
}
