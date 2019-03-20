package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class FulfillStoreMultiShipmentMultiSkuSingleStoreFulfilled extends GenericTestClass {

    private static String name = client1+"FulfillStoreMultiShipmentMultiSkuSingleStoreFulfilled";
    //reset
    @Test(priority = 1)
    public void unitRest() throws Throwable {
        RestAssuredUtil.ResetAll();
    }

    //store
    @Test(priority = 2)
    public void storeSetUp() throws Throwable {
        GenericTestClass.runStoreSetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9920.json");
    }

    @Test(priority = 3)
    public void inventoryUpdateSetUp() throws Throwable {
        GenericTestClass.inventorySetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/fulfill_store_multi_shipment_multi_sku_single_store_fulfilled_inventory_update.json");
    }

    @Test(priority = 4)
    public void orderSetUp() throws Throwable {
        String orderSetUpObject = GenericTestClass.runOrderSetUp
                ("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/fulfill_store_multi_shipment_multi_sku_single_store_fulfilled_order.json",name);
        JSONObject convertedOrderObject = new JSONObject(orderSetUpObject);
        if (convertedOrderObject.get("orderId") != null)
            setSysProperty("orderId", convertedOrderObject.get("orderId").toString());
    }

    @Test(priority = 5)
    public void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/3_fulfill_store_multi_shipment_multi_sku_single_store_fulfilled.json";
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }
}
