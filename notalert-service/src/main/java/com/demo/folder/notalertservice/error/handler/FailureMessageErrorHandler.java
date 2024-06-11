package com.demo.folder.notalertservice.error.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service(value = "failureMessageErrorHandler")
public class FailureMessageErrorHandler implements ConsumerAwareListenerErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FailureMessageErrorHandler.class);

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        LOGGER.warn("Error happened: {}",message.getPayload(),exception.getMessage());

        if (exception.getCause() instanceof RuntimeException) {
            throw exception;
        }
        return null;
    }
}
