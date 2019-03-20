package com.bec.api.automation.domain;



import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Jyoti on 19/01/18.
 */

public class FUnitTestInventoryUpdate implements Serializable
{

    private Long id;

    private String uuid;

    private String brand;

    private String timeStamp;

    private String inventoryVals;

    private String testName;

    private String testMethodName;

    private String testStatus;

    private String startTime;

    private String endTime;

    private String input;

    private String output;

    private org.joda.time.LocalDateTime creationDatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getInventoryVals() {
        return inventoryVals;
    }

    public void setInventoryVals(String inventoryVals) {
        this.inventoryVals = inventoryVals;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public org.joda.time.LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(org.joda.time.LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public FUnitTestInventoryUpdate() {
        this.creationDatetime = org.joda.time.LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", brand='" + brand + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", inventoryVals='" + inventoryVals + '\'' +
                ", testName='" + testName + '\'' +
                ", testMethodName='" + testMethodName + '\'' +
                ", testStatus='" + testStatus + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", creationDatetime=" + creationDatetime +
                '}';
    }
}
