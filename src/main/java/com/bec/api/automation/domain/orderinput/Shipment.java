package com.bec.api.automation.domain.orderinput;

import java.util.ArrayList;

public class Shipment {

    private String shipToStoreId;

    public String getShipToStoreId() { return this.shipToStoreId; }

    public void setShipToStoreId(String shipToStoreId) { this.shipToStoreId = shipToStoreId; }

    private String country;

    public String getCountry() { return this.country; }

    public void setCountry(String country) { this.country = country; }

    private String zipCode;

    public String getZipCode() { return this.zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    private String promiseDate;

    public String getPromiseDate() { return this.promiseDate; }

    public void setPromiseDate(String promiseDate) { this.promiseDate = promiseDate; }

    private String shipmentId;

    public String getShipmentId() { return this.shipmentId; }

    public void setShipmentId(String shipmentId) { this.shipmentId = shipmentId; }

    private String state;

    public String getState() { return this.state; }

    public void setState(String state) { this.state = state; }

    private boolean shipToStoreFlag;

    public boolean getShipToStoreFlag() { return this.shipToStoreFlag; }

    public void setShipToStoreFlag(boolean shipToStoreFlag) { this.shipToStoreFlag = shipToStoreFlag; }

    private ArrayList<Product> products;

    public ArrayList<Product> getProducts() { return this.products; }

    public void setProducts(ArrayList<Product> products) { this.products = products; }


    @Override
    public String toString() {
        return "shipment{" +
                "shipToStoreId='" + shipToStoreId + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", promiseDate='" + promiseDate + '\'' +
                ", shipmentId='" + shipmentId + '\'' +
                ", state='" + state + '\'' +
                ", shipToStoreFlag=" + shipToStoreFlag +
                ", products=" + products +
                '}';
    }
}
