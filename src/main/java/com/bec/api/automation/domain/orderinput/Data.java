package com.bec.api.automation.domain.orderinput;

import java.util.ArrayList;

public class Data {

    private String brand;

    public String getBrand() { return this.brand; }

    public void setBrand(String brand) { this.brand = brand; }

    private String correlationId;

    public String getCorrelationId() { return this.correlationId; }

    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    private String createDateTime;

    public String getCreateDateTime() { return this.createDateTime; }

    public void setCreateDateTime(String createDateTime) { this.createDateTime = createDateTime; }

    private String orderId;

    public String getOrderId() { return this.orderId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }

    private String postTimeStamp;

    public String getPostTimeStamp() { return this.postTimeStamp; }

    public void setPostTimeStamp(String postTimeStamp) { this.postTimeStamp = postTimeStamp; }

    private boolean processRealTimeFlag;

    public boolean getProcessRealTimeFlag() { return this.processRealTimeFlag; }

    public void setProcessRealTimeFlag(boolean processRealTimeFlag) { this.processRealTimeFlag = processRealTimeFlag; }

    private ArrayList<Shipment> shipments;

    public ArrayList<Shipment> getShipments() { return this.shipments; }

    public void setShipments(ArrayList<Shipment> shipments) { this.shipments = shipments; }

    @Override
    public String toString() {
        return "Data{" +
                "brand='" + brand + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", createDateTime='" + createDateTime + '\'' +
                ", orderId='" + orderId + '\'' +
                ", postTimeStamp='" + postTimeStamp + '\'' +
                ", processRealTimeFlag=" + processRealTimeFlag +
                ", shipments=" + shipments +
                '}';
    }
}
