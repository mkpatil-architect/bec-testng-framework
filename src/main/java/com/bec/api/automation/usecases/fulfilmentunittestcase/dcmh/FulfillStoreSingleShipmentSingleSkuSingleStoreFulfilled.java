package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;


public class FulfillStoreSingleShipmentSingleSkuSingleStoreFulfilled extends GenericTestClass {

    private static String name = client1+"FulfillStoreSingleShipmentSingleSkuSingleStoreFulfilled";

    //reset
    @Test(priority = 1)
    private void unitReset() throws Throwable {
        RestAssuredUtil.ResetAll();
    }

    //storeSetUp
    @Test(priority = 2)
    private void storeSetUp() throws Throwable {
        GenericTestClass.runStoreSetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9920.json");
    }

    //inventoryUpdateSetUp
    @Test(priority = 3)
    private void inventoryUpdateSetUp() throws Throwable {
        GenericTestClass.inventorySetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/fulfill_store_single_shipment_single_sku_single_store_fulfilled_inventory_update.json");
    }

    //orderSetUp
    @Test(priority = 4)
    private void orderSetUp() throws Throwable {
        String orderSetUpObject = GenericTestClass.runOrderSetUp("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/fulfill_store_single_shipment_single_sku_single_store_fulfilled_order.json", name);
        //convert string to object
        JSONObject convertedOrderSetUpObject = new JSONObject(orderSetUpObject);
        //set orderId to sysProperty
        if (convertedOrderSetUpObject.get("orderId") != null)
            setSysProperty("orderId", convertedOrderSetUpObject.get("orderId").toString());
    }

    //verification
    @Test(priority = 5)
    public void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/17_fulfill_store_single_shipment_single_sku_single_store_fulfilled.json";
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }
}
