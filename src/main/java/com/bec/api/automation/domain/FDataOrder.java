package com.bec.api.automation.domain;

/**
 * Created by mkpatil on 04/12/17.*/


import org.joda.time.LocalDateTime;



public class FDataOrder {


    private Long id;


    private LocalDateTime creationDatetime;


    private String shipmentId;


    private String promiseDate;


    private boolean shipToStoreFlag;


    private String zipCode;


    private String state;


    private String country;


    private String productsObj;

    private String orderId;




    private String responseObj;


    private String status;

    private String postTimeStamp;

    private String type;


    public FDataOrder() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
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

    public String getProductsObj() {
        return productsObj;
    }

    public void setProductsObj(String productsObj) {
        this.productsObj = productsObj;
    }

    public String getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(String responseObj) {
        this.responseObj = responseObj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPostTimeStamp() {
        return postTimeStamp;
    }

    public void setPostTimeStamp(String postTimeStamp) {
        this.postTimeStamp = postTimeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}