package com.demo.folder.broker.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogMessageProducer {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String summary) {
        kafkaTemplate.send("t-log",summary);
    }
}
