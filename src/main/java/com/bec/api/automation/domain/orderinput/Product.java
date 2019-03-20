package com.bec.api.automation.domain.orderinput;

public class Product {

    private int quantity;

    public int getQuantity() { return this.quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    private double weight;

    public double getWeight() { return this.weight; }

    public void setWeight(double weight) { this.weight = weight; }

    private String sku;

    public String getSku() { return this.sku; }

    public void setSku(String sku) { this.sku = sku; }

    private String weightUnitOfMeasure;

    public String getWeightUnitOfMeasure() { return this.weightUnitOfMeasure; }

    public void setWeightUnitOfMeasure(String weightUnitOfMeasure) { this.weightUnitOfMeasure = weightUnitOfMeasure; }

    @Override
    public String toString() {
        return "product{" +
                "quantity=" + quantity +
                ", weight=" + weight +
                ", sku='" + sku + '\'' +
                ", weightUnitOfMeasure='" + weightUnitOfMeasure + '\'' +
                '}';
    }
}
