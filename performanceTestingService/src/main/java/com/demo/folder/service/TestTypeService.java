package com.demo.folder.service;

import com.demo.folder.action.TestTypeAction;
import com.demo.folder.broker.message.TestTypeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TestTypeService {

    @Autowired
    private TestTypeAction testTypeAction;


    public void publish() {
        var testTypeMessage = new TestTypeMessage();
        testTypeMessage.setTestType("k6 test");
        testTypeMessage.setTestTime(LocalDateTime.now());
        testTypeAction.publishToTopic(testTypeMessage);
    }

    public void publishLog(String summary) {
        testTypeAction.publishToLogTopic(summary);
    }
}
