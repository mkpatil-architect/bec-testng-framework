package com.bec.api.automation.domain.unittestbean;

import java.util.List;

public class ProductsBean {

    private String quantityFulfilled;
    private String quantitySent;
    private String processDateTime;
    private String sku;
    private List<StoreListBean> storeList;
    private double quantity;
    private double weight;
    private double unfulfilledQuantityNoInventory;
    private double unfulfilledQuantityNoCapacity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsBean that = (ProductsBean) o;

        if (Double.compare(that.quantity, quantity) != 0) return false;
        if (Double.compare(that.weight, weight) != 0) return false;
        if (Double.compare(that.unfulfilledQuantityNoInventory, unfulfilledQuantityNoInventory) != 0) return false;
        if (Double.compare(that.unfulfilledQuantityNoCapacity, unfulfilledQuantityNoCapacity) != 0) return false;
        if (quantityFulfilled != null ? !quantityFulfilled.equals(that.quantityFulfilled) : that.quantityFulfilled != null)
            return false;
        if (quantitySent != null ? !quantitySent.equals(that.quantitySent) : that.quantitySent != null) return false;
        if (processDateTime != null ? !processDateTime.equals(that.processDateTime) : that.processDateTime != null)
            return false;
        if (sku != null ? !sku.equals(that.sku) : that.sku != null) return false;
        return storeList != null ? storeList.equals(that.storeList) : that.storeList == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = quantityFulfilled != null ? quantityFulfilled.hashCode() : 0;
        result = 31 * result + (quantitySent != null ? quantitySent.hashCode() : 0);
        result = 31 * result + (processDateTime != null ? processDateTime.hashCode() : 0);
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (storeList != null ? storeList.hashCode() : 0);
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(unfulfilledQuantityNoInventory);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(unfulfilledQuantityNoCapacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getQuantityFulfilled() {
        return quantityFulfilled;
    }

    public void setQuantityFulfilled(String quantityFulfilled) {
        this.quantityFulfilled = quantityFulfilled;
    }

    public String getQuantitySent() {
        return quantitySent;
    }

    public void setQuantitySent(String quantitySent) {
        this.quantitySent = quantitySent;
    }

    public String getProcessDateTime() {
        return processDateTime;
    }

    public void setProcessDateTime(String processDateTime) {
        this.processDateTime = processDateTime;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<StoreListBean> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreListBean> storeList) {
        this.storeList = storeList;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getUnfulfilledQuantityNoInventory() {
        return unfulfilledQuantityNoInventory;
    }

    public void setUnfulfilledQuantityNoInventory(double unfulfilledQuantityNoInventory) {
        this.unfulfilledQuantityNoInventory = unfulfilledQuantityNoInventory;
    }

    public double getUnfulfilledQuantityNoCapacity() {
        return unfulfilledQuantityNoCapacity;
    }

    public void setUnfulfilledQuantityNoCapacity(double unfulfilledQuantityNoCapacity) {
        this.unfulfilledQuantityNoCapacity = unfulfilledQuantityNoCapacity;
    }
}
