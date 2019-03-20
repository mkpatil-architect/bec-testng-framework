package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class FulfillStoreMultiShipmentMultiSkuMultiStorePartiallyFulfilled extends GenericTestClass {

    private static String name = client1+"FulfillStoreMultiShipmentMultiSkuMultiStorePartiallyFulfilled";

    @Test(priority = 1)
    public void unitRest() throws Throwable {
        RestAssuredUtil.ResetAll();
    }

    //store
    @Test(priority = 2)
    public void storeSetUp() throws Throwable {
        List<String> storePathList = new ArrayList<>();
        storePathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9923.json");
        storePathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9920.json");
        for (String path:storePathList) {
            GenericTestClass.runStoreSetup(path);
        }
    }

    //inventoryUpdate
    @Test(priority = 3)
    public void inventoryUpdate() throws Throwable {
        GenericTestClass.inventorySetup
                ("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/fulfill_store_multi_shipment_multi_sku_multi_store_partially_fulfilled_inventory_update.json");
    }

    //order
    @Test(priority = 4)
    public void orderSetUp() throws Throwable {
        String orderSetUpObject = GenericTestClass.runOrderSetUp
                ("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/fulfill_store_multi_shipment_multi_sku_multi_store_partially_fulfilled_order.json",name);
        JSONObject convert = new JSONObject(orderSetUpObject);
        if (convert.get("orderId") != null)
            setSysProperty("orderId", convert.get("orderId").toString());
    }

    @Test(priority = 5)
    public void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/2_fulfill_store_multi_shipment_multi_sku_multi_store_partially_fulfilled.json";
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }
}
