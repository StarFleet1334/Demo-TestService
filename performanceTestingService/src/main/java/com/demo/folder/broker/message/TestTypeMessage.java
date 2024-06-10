package com.demo.folder.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TestTypeMessage {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime testTime;
    private String testType;

    public LocalDateTime getTestTime() {
        return testTime;
    }

    public void setTestTime(LocalDateTime testTime) {
        this.testTime = testTime;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    @Override
    public String toString() {
        return "TestTypeMessage{" +
                "testTime=" + testTime +
                ", testType='" + testType + '\'' +
                '}';
    }
}
