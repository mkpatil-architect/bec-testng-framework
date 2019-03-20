package com.bec.api.automation.domain;


import org.joda.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;


public class FDataStore {


    private String zip_code;


    private String shipments_capacity;


    private String state;


    private String brand;


    private Long id;


    private String store_id;


    private String store_type;


    private String sub_brand;


    private boolean enable_ship_from_store;


    private String country;


    private String status;

    private LocalDateTime creationDatetime;

    public FDataStore() {

    }

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getShipments_capacity() {
        return shipments_capacity;
    }

    public void setShipments_capacity(String shipments_capacity) {
        this.shipments_capacity = shipments_capacity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_type() {
        return store_type;
    }

    public void setStore_type(String store_type) {
        this.store_type = store_type;
    }

    public String getSub_brand() {
        return sub_brand;
    }

    public void setSub_brand(String sub_brand) {
        this.sub_brand = sub_brand;
    }

    public boolean getEnable_ship_from_store() {
        return enable_ship_from_store;
    }

    public void setEnable_ship_from_store(boolean enable_ship_from_store) {
        this.enable_ship_from_store = enable_ship_from_store;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnable_ship_from_store() {
        return enable_ship_from_store;
    }

    @Override
    public String toString() {
        return "{" +
                "brand='" + brand + '\'' +
                ", zipCode='" + zip_code + '\'' +
                ", shipments_capacity='" + shipments_capacity + '\'' +
                ", state='" + state + '\'' +
                ", id=" + id +
                ", store_id='" + store_id + '\'' +
                ", store_type='" + store_type + '\'' +

                ", enable_ship_from_store=" + enable_ship_from_store +
                ", country='" + country + '\'' +

                '}';
    }


    public String toStoreFormatString() {
        return
                "{ brand='" + brand + '\'' +
                ", zipCode='" + zip_code + '\'' +
                ", shipmentsCapacity=" + Integer.parseInt(shipments_capacity)  +
                ", state='" + state + '\'' +
                ", storeId='" + store_id + '\'' +
                ", storeType='" + store_type + '\'' +
             ", enableShipFromStore=" + enable_ship_from_store +
                ", country='" + country + '\'' +

                '}';
    }

   /* public JSONObject toStoreFormatJSON() {
        try {
            JSONObject obj = new JSONObject("string");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("brand",brand);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }*/

}
