package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.testng.annotations.Test;

public class FulfillStoreZeroQuantity extends GenericTestClass {


    private static String name = client1+"FulfillStoreZeroQuantity";


    @Test(priority = 1)
    public void unitRest() throws Throwable {
        RestAssuredUtil.ResetAll();
    }

    @Test(priority = 2)
    public void storeSetUp() throws Throwable {
        GenericTestClass.runStoreSetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9920.json");
    }

    //inventoryUpdate
    @Test(priority = 3)
    public void unitTestZeroQuantityInventorySetUp() throws Throwable {
        GenericTestClass.inventorySetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/fulfill_store_zero_quantity_inventory_update.json");
    }

    //order
    @Test(priority = 4)
    public void orderSetUp() throws Throwable {
        String orderSetUpObject = GenericTestClass.runOrderSetUp("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/fulfill_store_zero_quantity_order.json", name);
        org.json.JSONObject convert = new org.json.JSONObject(orderSetUpObject);
        if (convert.get("orderId") != null)
            setSysProperty("orderId", convert.get("orderId").toString());
    }

    //verification
    @Test(priority = 5)
    public void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/19_fulfill_store_zero_quantity.json";
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }
}























