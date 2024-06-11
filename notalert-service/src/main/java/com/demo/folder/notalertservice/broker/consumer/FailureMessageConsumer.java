package com.demo.folder.notalertservice.broker.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FailureMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureMessageConsumer.class);

    @KafkaListener(topics = "t-global-failure",errorHandler = "failureMessageErrorHandler")
    public void listen(String message) {
     LOGGER.info(message);
    }
}
