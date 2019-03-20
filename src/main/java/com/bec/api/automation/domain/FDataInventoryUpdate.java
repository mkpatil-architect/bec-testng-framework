package com.bec.api.automation.domain;
/*
*
 * Created by mkpatil on 04/12/17.*/


import org.joda.time.LocalDateTime;




public class FDataInventoryUpdate {


    private Long id;

    private String uuid;


    private String timeStamp;


    private String brand;

    private String type;


    private String inventoryVals;


    private String status;



    private LocalDateTime creationDatetime;


    private String responseDataObj;

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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getInventoryVals() {
        return inventoryVals;
    }

    public void setInventoryVals(String inventoryVals) {
        this.inventoryVals = inventoryVals;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public String getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(String responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}