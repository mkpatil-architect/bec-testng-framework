package com.bec.api.automation.domain;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Jyoti on 19/01/18.
 */

public class FUnitTestStore implements Serializable
{
    private Long id;

    private String storeId;

    private String brand;

    private String zipCode;

    private String country;

    private String state;

    private boolean enableShipFromStore;

    private int shipmentsCapacity;

    private String testName;

    private String testMethodName;

    private String testStatus;

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    private String startTime;

    private String endTime;

    private String input;

    private String output;

    private LocalDateTime createdTime;

    private String storeType;

    public FUnitTestStore() {
        this.createdTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isEnableShipFromStore() {
        return enableShipFromStore;
    }

    public void setEnableShipFromStore(boolean enableShipFromStore) {
        this.enableShipFromStore = enableShipFromStore;
    }

    public int getShipmentsCapacity() {
        return shipmentsCapacity;
    }

    public void setShipmentsCapacity(int shipmentsCapacity) {
        this.shipmentsCapacity = shipmentsCapacity;
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


    @Override
    public String toString() {
        return "{" +
                "brand='" + brand + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", shipmentsCapacity='" + shipmentsCapacity + '\'' +
                ", state='" + state + '\'' +
                ", id=" + id +
                ", storeId='" + storeId + '\'' +
                ", storeType='" + storeType + '\'' +
                ", enableShipFromStore=" + enableShipFromStore +
                ", country='" + country + '\'' +

                '}';
    }


    public String toStoreFormatString() {
        return
                "{ brand='" + brand + '\'' +
                        ", zipCode='" + zipCode + '\'' +
                        ", shipmentsCapacity=" + shipmentsCapacity  + '\'' +
                        ", state='" + state + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", storeType='" + storeType + '\'' +
                        ", enableShipFromStore=" + enableShipFromStore +
                        ", country='" + country + '\'' +

                        '}';
    }




}
