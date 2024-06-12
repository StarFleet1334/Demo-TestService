package com.demo.folder.broker.producer;


import com.demo.folder.broker.message.FailureLogPayLoad;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FailureMessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureMessageProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publish(FailureLogPayLoad payload) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(payload);
            LOGGER.info("Message: {}", jsonMessage);
            kafkaTemplate.send("t-global-failure",jsonMessage);
            LOGGER.info("Message is successfully sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
