package com.demo.folder.notalertservice.broker.consumer;


import com.demo.folder.notalertservice.entity.FailureLogPayLoad;
import com.demo.folder.notalertservice.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FailureMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureMessageConsumer.class);
    private final EmailService emailService;


    @Autowired
    private ObjectMapper objectMapper;

    public FailureMessageConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "t-global-failure")
    public void listen(String message) throws JsonProcessingException {
        LOGGER.info("Received message: {}", message);

        LOGGER.info("Received Extra: {}",objectMapper.readValue(message, FailureLogPayLoad.class));

        String htmlMessage = formatMessageAsHtml(message);
        emailService.sendEmail("latariailia6@gmail.com", "Build Failure During Test", htmlMessage);

        LOGGER.info("Message is sent successfully");
    }

    private String formatMessageAsHtml(String message) throws JsonProcessingException {
        FailureLogPayLoad details = parseMessage(message);
        return "<html>" +
                "<body>" +
                "<h2>Build Failure During Test</h2>" +
                "<p><strong>Level:</strong> " + details.getLevel() + "</p>" +
                "<p><strong>Message:</strong> " + details.getMessage() + "</p>" +
                "<p><strong>Test Name:</strong> " + details.getTestName() + "</p>" +
                "<p><strong>Class Name:</strong> " + details.getClassName() + "</p>" +
                "<p><strong>Start Time:</strong> " + details.getStartTime() + "</p>" +
                "</body>" +
                "</html>";
    }

    private FailureLogPayLoad parseMessage(String message) throws JsonProcessingException {
        var failure = objectMapper.readValue(message, FailureLogPayLoad.class);
        var failureDetails = new FailureLogPayLoad();
        failureDetails.setLevel(failure.getLevel());
        failureDetails.setMessage(failure.getMessage());
        failureDetails.setTestName(failure.getTestName());
        failureDetails.setClassName(failure.getClassName());
        failureDetails.setStartTime(failure.getStartTime());
        return failureDetails;

    }


}
