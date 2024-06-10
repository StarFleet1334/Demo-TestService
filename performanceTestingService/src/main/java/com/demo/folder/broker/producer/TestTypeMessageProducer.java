package com.demo.folder.broker.producer;


import com.demo.folder.broker.message.TestTypeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestTypeMessageProducer {

    @Autowired
    private KafkaTemplate<String, TestTypeMessage> kafkaTemplate;

    public void publish(TestTypeMessage testTypeMessage) {
        kafkaTemplate.send("t-test-type",testTypeMessage);
    }

}
