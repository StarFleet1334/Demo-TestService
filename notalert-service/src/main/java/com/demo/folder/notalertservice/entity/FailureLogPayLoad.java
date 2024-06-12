package com.demo.folder.notalertservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import com.fasterxml.jackson.annotation.JsonProperty;

public class FailureLogPayLoad {

    @JsonProperty("level")
    private String level;

    @JsonProperty("message")
    private String message;

    @JsonProperty("testName")
    private String testName;

    @JsonProperty("className")
    private String className;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String startTime;


    public FailureLogPayLoad() {
        // no-arg constructor
    }

    // Getter and setter for each property
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

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
