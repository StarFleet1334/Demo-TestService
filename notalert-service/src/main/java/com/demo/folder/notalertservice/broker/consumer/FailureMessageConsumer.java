package com.demo.folder.notalertservice.broker.consumer;


import com.demo.folder.notalertservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FailureMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureMessageConsumer.class);
    private final EmailService emailService;

    public FailureMessageConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "t-global-failure", errorHandler = "failureMessageErrorHandler")
    public void listen(String message) {
        LOGGER.info("Received message: {}", message);
        emailService.sendEmail("latariailia6@gmail.com", "Build Failure During Test", message);
    }
}
