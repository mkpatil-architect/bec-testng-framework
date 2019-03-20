package com.bec.api.automation.domain;


import java.io.Serializable;
import org.joda.time.LocalDateTime;

/**
 * Created by mkpatil on 04/12/17.
 */

public class FReportInventoryUpdate implements Serializable
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

    private LocalDateTime creationDatetime;

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

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public FReportInventoryUpdate() {
        this.creationDatetime = LocalDateTime.now();
    }
}
