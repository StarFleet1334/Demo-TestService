package com.demo.folder.action;


import com.demo.folder.broker.message.TestTypeMessage;
import com.demo.folder.broker.producer.LogMessageProducer;
import com.demo.folder.broker.producer.TestTypeMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestTypeAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTypeAction.class);

    @Autowired
    private TestTypeMessageProducer testTypeMessageProducer;

    @Autowired
    private LogMessageProducer logMessageProducer;

    public void publishToTopic(TestTypeMessage testTypeMessage) {
        testTypeMessageProducer.publish(testTypeMessage);
    }

    public void publishToLogTopic(String summary) {
        logMessageProducer.publish(summary);
    }
}
