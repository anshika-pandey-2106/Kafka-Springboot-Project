package com.example.Project.controller;


import com.example.Project.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class MessageController {

    private KafkaProducer kafkaProducer;

    @Autowired
    public MessageController(KafkaProducer kafkaProducer)
    {
        this.kafkaProducer=kafkaProducer;
    }

    //http:localhost:8080/kafka/publish?message=hello-world
    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){

        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to topic 1");
    }
}
