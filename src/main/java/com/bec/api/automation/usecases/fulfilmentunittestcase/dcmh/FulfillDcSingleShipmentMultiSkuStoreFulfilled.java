package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class FulfillDcSingleShipmentMultiSkuStoreFulfilled extends GenericTestClass {

    private static String name = client1+"FulfillDcSingleShipmentMultiSkuStoreFulfilled";

    //reset
    @Test(priority = 1)
    private void unitReset() throws Throwable {
        RestAssuredUtil.ResetAll();
    }

    //storeSetUp
    @Test(priority = 2)
    private void storeSetUp() throws Throwable {
        List<String> pathList = new ArrayList<>();
        pathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9923.json");
        pathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9920.json");
        pathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_2003.json");
        for (String path : pathList) {
            GenericTestClass.runStoreSetup(path);
        }
    }

    //inventoryUpdateSetUp
    @Test(priority = 3)
    private void inventoryUpdateSetUp() throws Throwable {
        GenericTestClass.inventorySetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/fulfill_dc_single_shipment_multi_sku_store_fulfilled_inventory_update.json");
    }

    //orderSetUp
    @Test(priority = 4)
    private void orderSetUp() throws Throwable {
        String orderSetUpObject = GenericTestClass.runOrderSetUp("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/fulfill_dc_single_shipment_multi_sku_store_fulfilled_order.json", name);
        //convert string to object
        JSONObject convertedOrderSetUpObject = new JSONObject(orderSetUpObject);
        //set orderId to sysProperty
        if (convertedOrderSetUpObject.get("orderId") != null)
            setSysProperty("orderId", convertedOrderSetUpObject.get("orderId").toString());
    }

    //verification
    @Test(priority = 5)
    public void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/20_fulfill_dc_single_shipment_multi_sku_store_fulfilled.json";
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }
}
