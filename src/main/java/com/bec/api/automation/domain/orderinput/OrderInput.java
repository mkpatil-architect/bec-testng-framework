package com.bec.api.automation.domain.orderinput;

public class OrderInput {

    private Data data;

    public Data getData() { return this.data; }

    public void setData(Data data) { this.data = data; }

    /**
     * @return order input object
     */
    @Override
    public String toString() {
        return "OrderInput{" +
                "data=" + data +
                '}';
    }
}
