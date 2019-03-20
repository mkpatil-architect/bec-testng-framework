package com.bec.api.automation.domain.unittestbean;

import java.util.List;

public class ShipmentsBean {

    private String shipmentId;
    private String promiseDate;
    private String zipCode;
    private String state;
    private boolean shipToStoreFlag;
    private List<ProductsBean> products;
    private String country;
    private String shipToStoreId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentsBean that = (ShipmentsBean) o;

        if (shipToStoreFlag != that.shipToStoreFlag) return false;
        if (shipmentId != null ? !shipmentId.equals(that.shipmentId) : that.shipmentId != null) return false;
        if (promiseDate != null ? !promiseDate.equals(that.promiseDate) : that.promiseDate != null) return false;
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (products != null ? !products.equals(that.products) : that.products != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        return shipToStoreId != null ? shipToStoreId.equals(that.shipToStoreId) : that.shipToStoreId == null;
    }

    @Override
    public int hashCode() {
        int result = shipmentId != null ? shipmentId.hashCode() : 0;
        result = 31 * result + (promiseDate != null ? promiseDate.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (shipToStoreFlag ? 1 : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (shipToStoreId != null ? shipToStoreId.hashCode() : 0);
        return result;
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

    public boolean isShipToStoreFlag() {
        return shipToStoreFlag;
    }

    public void setShipToStoreFlag(boolean shipToStoreFlag) {
        this.shipToStoreFlag = shipToStoreFlag;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShipToStoreId() {
        return shipToStoreId;
    }

    public void setShipToStoreId(String shipToStoreId) {
        this.shipToStoreId = shipToStoreId;
    }


}
