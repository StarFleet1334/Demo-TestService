package com.demo.folder.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class FailureLogPayLoad {

    private String level;
    private String message;
    private String testName;
    private String className;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String startTime;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "FailureLogPayLoad{" +
                "level='" + level + '\'' +
                ", message='" + message + '\'' +
                ", testName='" + testName + '\'' +
                ", className='" + className + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
