package com.bec.api.automation.domain;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Jyoti on 18/01/18.
 */

public class FUnitTestStoreResponse implements Serializable
{
    private Long id;

    private String shipmentId;

    private String zipCode;

    private String state;

    private String country;

    private String products;

    private String timeStamp;

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

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
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

    public FUnitTestStoreResponse() {
        this.creationDatetime = LocalDateTime.now();
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", shipmentId='" + shipmentId + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", products='" + products + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
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
