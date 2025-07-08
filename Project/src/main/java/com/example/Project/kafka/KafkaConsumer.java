package com.example.Project.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;




/*@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final String TARGET_TOPIC = "finalTopic";

    //listens to messages in intermediateTopic and forwards them to final topic
    @KafkaListener(topics = "intermediateTopic", groupId= "myGroup")
    public void consume(String message){

    LOGGER.info(String.format("Message received -> %s", message));

        //forward message
        kafkaTemplate.send(TARGET_TOPIC, message);
        LOGGER.info(String.format("Message sent to second topic : %s ", message));


    }
}*/
