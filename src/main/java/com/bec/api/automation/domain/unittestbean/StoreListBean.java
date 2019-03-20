package com.bec.api.automation.domain.unittestbean;

public class StoreListBean
{
    private double quantitySent;
    private String storeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreListBean that = (StoreListBean) o;

        if (Double.compare(that.quantitySent, quantitySent) != 0) return false;
        return storeId != null ? storeId.equals(that.storeId) : that.storeId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(quantitySent);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (storeId != null ? storeId.hashCode() : 0);
        return result;
    }

    public double getQuantitySent() {
        return quantitySent;
    }

    public void setQuantitySent(double quantitySent) {
        this.quantitySent = quantitySent;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
