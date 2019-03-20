package com.bec.api.automation.domain;

/* *
        * Created by Jyoti on 04/12/17.
*/


import org.joda.time.LocalDateTime;


public class FDataStoreResponse {


    private Long id;


    private String shipmentId;


    private String zipCode;


    private String state;


    private String country;


    private String productObj;


    private String responsejsonObj;


    private String status;

    private String processDateTime;

    private String type;

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

    public String getProductObj() {
        return productObj;
    }

    public void setProductObj(String productObj) {
        this.productObj = productObj;
    }

    public String getResponsejsonObj() {
        return responsejsonObj;
    }

    public void setResponsejsonObj(String responsejsonObj) {
        this.responsejsonObj = responsejsonObj;
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


    private LocalDateTime creationDatetime;

    public String getProcessDateTime() {
        return processDateTime;
    }

    public void setProcessDateTime(String processDateTime) {
        this.processDateTime = processDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FDataStoreResponse() {

    }


}



