package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class FulfillStoreMultiShipmentMultiSkuSingleStorePartiallyFulfilled extends GenericTestClass {

    private static String name = client1+"FulfillStoreMultiShipmentMultiSkuSingleStorePartiallyFulfilled";

    @Test(priority = 1)
    private void unitReset() throws Throwable{
        RestAssuredUtil.ResetAll();
    }
    //store setup
    @Test(priority = 2)
    private void storeSetUp() throws Throwable{
        GenericTestClass.runStoreSetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9920.json");

    }
    //inventoryUpdate
    @Test(priority = 3)
    private void inventoryUpdateSetUp() throws Throwable{
        GenericTestClass.inventorySetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/fulfill_store_multi_shipment_multi_sku_single_store_partially_fulfilled_inventory_update.json");

    }
    //orderSetUp
    @Test(priority = 4)
    private void orderSetUp() throws Throwable{
        String orderSetUpObject=GenericTestClass.runOrderSetUp
                ("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/fulfill_store_multi_shipment_multi_sku_single_store_partially_fulfilled_order.json",name);
        JSONObject convertedOrderSetUpObject=new JSONObject(orderSetUpObject);
        if(convertedOrderSetUpObject.get("orderId")!=null)
            setSysProperty("orderId",convertedOrderSetUpObject.get("orderId").toString());
    }

    //verification
    @Test(priority = 5)
    public void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/4_fulfill_store_multi_shipment_multi_sku_single_store_partially_fulfilled.json";
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }

}
