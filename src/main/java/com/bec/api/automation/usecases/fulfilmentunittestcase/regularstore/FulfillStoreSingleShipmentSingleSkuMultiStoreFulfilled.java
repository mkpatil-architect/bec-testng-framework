package com.bec.api.automation.usecases.fulfilmentunittestcase.regularstore;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class FulfillStoreSingleShipmentSingleSkuMultiStoreFulfilled extends GenericTestClass {
    private static String name = client2+"FulfillStoreSingleShipmentSingleSkuMultiStoreFulfilled";

    //reset
    @Test(priority = 1)
    private void unitRest() throws Throwable {
        RestAssuredUtil.ResetAll();
    }

    //storeSetUp
    @Test(priority = 2)
    private void storeSetUp() throws Throwable {
        List<String> storePathList = new ArrayList<>();
        storePathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/regularstore/storepayload/store_2001.json");
        storePathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/regularstore/storepayload/store_2002.json");
        for (String path : storePathList)
            GenericTestClass.runStoreSetup(path);
    }

    //inventoryUpdate setUp
    @Test(priority = 3)
    private void inventoryUpdateSetUp() throws Throwable {
        GenericTestClass.inventorySetup("src/main/resources/payloads/fulfilment_unit_test_payload/regularstore/inventoryupdatepayload/16_fulfill_store_single_shipment_single_sku_multi_store_fulfilled_inventory_update.json");
    }

    //orderSetUp
    @Test(priority = 4)
    private void orderSetUp() throws Throwable {
        String orderSetUpObject = GenericTestClass.runOrderSetUp("src/main/resources/payloads/fulfilment_unit_test_payload/regularstore/orderpayload/16_fulfill_store_single_shipment_single_sku_multi_store_fulfilled_order.json", name);
        //convert string to object and extract orderId
        JSONObject convertedOrderSetUpObject = new JSONObject(orderSetUpObject);
        if (convertedOrderSetUpObject.get("orderId") != null)
            setSysProperty("orderId", convertedOrderSetUpObject.get("orderId").toString());
    }

    //verification
    @Test(priority = 5)
    private void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/regularstore/orderresponsepayload/16_fulfill_store_single_shipment_single_sku_multi_store_fulfilled.json";
        //getOrderId and call orderResponseVerification() method
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }
}
