package com.bec.api.automation.domain.unittestbean;

import java.util.List;

public class OrderInputBean {

    private boolean processRealTimeFlag;
    private String createDateTime;
    private String correlationId;
    private List<ShipmentsBean> Shipments;
    private String brand;
    private String postTimeStamp;
    private String orderId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderInputBean that = (OrderInputBean) o;

        if (processRealTimeFlag != that.processRealTimeFlag) return false;
        if (createDateTime != null ? !createDateTime.equals(that.createDateTime) : that.createDateTime != null)
            return false;
        if (correlationId != null ? !correlationId.equals(that.correlationId) : that.correlationId != null)
            return false;
        if (Shipments != null ? !Shipments.equals(that.Shipments) : that.Shipments != null) return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (postTimeStamp != null ? !postTimeStamp.equals(that.postTimeStamp) : that.postTimeStamp != null)
            return false;
        return orderId != null ? orderId.equals(that.orderId) : that.orderId == null;
    }

    @Override
    public int hashCode() {
        int result = (processRealTimeFlag ? 1 : 0);
        result = 31 * result + (createDateTime != null ? createDateTime.hashCode() : 0);
        result = 31 * result + (correlationId != null ? correlationId.hashCode() : 0);
        result = 31 * result + (Shipments != null ? Shipments.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (postTimeStamp != null ? postTimeStamp.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        return result;
    }

    public boolean isProcessRealTimeFlag() {
        return processRealTimeFlag;
    }

    public void setProcessRealTimeFlag(boolean processRealTimeFlag) {
        this.processRealTimeFlag = processRealTimeFlag;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public List<ShipmentsBean> getShipments() {
        return Shipments;
    }

    public void setShipments(List<ShipmentsBean> shipments) {
        Shipments = shipments;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPostTimeStamp() {
        return postTimeStamp;
    }

    public void setPostTimeStamp(String postTimeStamp) {
        this.postTimeStamp = postTimeStamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
