package com.bec.api.automation.domain;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by mkpatil on 04/12/17.
 */

public class FReportOrder implements Serializable
{

    private Long id;

    private String shipmentId;

    private String promiseDate;

    private boolean shipToStoreFlag;

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

    private LocalDateTime createdTime;

    private String orderId;

    private String orderResponse;

    private String orderReceivedTime;

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

    public String getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(String promiseDate) {
        this.promiseDate = promiseDate;
    }

    public boolean isShipToStoreFlag() {
        return shipToStoreFlag;
    }

    public void setShipToStoreFlag(boolean shipToStoreFlag) {
        this.shipToStoreFlag = shipToStoreFlag;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public FReportOrder() {
        this.createdTime = LocalDateTime.now();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderReceivedTime() {
        return orderReceivedTime;
    }

    public void setOrderReceivedTime(String orderReceivedTime) {
        this.orderReceivedTime = orderReceivedTime;
    }

    public String getOrderResponse() {
        return orderResponse;
    }

    public void setOrderResponse(String orderResponse) {
        this.orderResponse = orderResponse;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
