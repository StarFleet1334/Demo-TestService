package com.demo.folder.error.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

public class GlobalErrorHandler implements CommonErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final String deadTopic;


    public GlobalErrorHandler(KafkaTemplate<String, String> kafkaTemplate, String deadTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.deadTopic = deadTopic;
    }

    /**
     * @param thrownException
     * @param record
     * @param consumer
     * @param container
     * @return
     */
    @Override
    public boolean handleOne(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        LOGGER.warn("Global error handler for message: {}", record.value().toString());
        try {
            kafkaTemplate.send(deadTopic, record.value().toString());
            LOGGER.info("Sent to dead-letter topic: {}", deadTopic);
        } catch (Exception e) {
            LOGGER.error("Failed to send to dead-letter topic: {} with exception: {}", deadTopic, e.getMessage());
        }
        return false;
    }
}

