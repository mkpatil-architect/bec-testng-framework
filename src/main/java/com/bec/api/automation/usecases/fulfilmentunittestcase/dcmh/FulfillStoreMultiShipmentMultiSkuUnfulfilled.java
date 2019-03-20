package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class FulfillStoreMultiShipmentMultiSkuUnfulfilled extends GenericTestClass {

    private static String name = client1+"FulfillStoreMultiShipmentMultiSkuUnfulfilled";

    //reset
    @Test(priority = 1)
    private void unitRest() throws Throwable{
        RestAssuredUtil.ResetAll();
    }
    //storeSetUp
    @Test(priority = 2)
    private void storeSetUp() throws Throwable{
        GenericTestClass.runStoreSetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9923.json");

    }
    //inventorySetUp
    @Test(priority = 3)
    private void inventoryUpdateSetUp() throws Throwable{
        GenericTestClass.inventorySetup("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/fulfill_store_multi_shipment_multi_sku_unfulfilled_inventory_update.json");

    }
    //orderSetUp
    @Test(priority = 4)
    private void orderSetUp() throws Throwable{
        String orderSetUpObject=GenericTestClass.runOrderSetUp
                ("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/fulfill_store_multi_shipment_multi_sku_unfulfilled_order.json",name);
        JSONObject convertedOrderSetUpObject=new JSONObject(orderSetUpObject);
        if(convertedOrderSetUpObject.get("orderId")!=null)
            setSysProperty("orderId",convertedOrderSetUpObject.get("orderId").toString());
    }

    // verification
    @Test(priority = 5)
    public void orderVerification() throws Throwable {
        String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/5_fulfill_store_multi_shipment_multi_sku_unfulfilled.json";
        String orderId = getSysProperty("orderId");
        super.orderResponseVerification(orderId, path);
    }
}
