package com.bec.api.automation.usecases.fulfilmentunittestcase.dcmh;

import com.bec.api.automation.utils.GenericTestClass;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;




public class FulfillStoreMultiShipmentMultiSkuMultiStoreFulfilled extends GenericTestClass {


    private static Log log = LogFactory.
            getLog(FulfillStoreMultiShipmentMultiSkuMultiStoreFulfilled.class);


    private static String name = client1+"FulfillStoreMultiShipmentMultiSkuMultiStoreFulfilled";

    @Test(priority = 1)

    public void unitTestReset() throws Throwable {
        RestAssuredUtil.ResetAll();
    }

    @Test(priority = 2)

    public void storeSetUp()  {

        List<String> pathList = new ArrayList<>();

        pathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9920.json");
        pathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_9923.json");
        pathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_2001.json");
        pathList.add("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/storepayloads/store_2002.json");

        for (String path: pathList) {

            GenericTestClass.runStoreSetup(path);
        }

    }

    @Test(priority = 3)

    public void unitTestMultiSkuInventorySetUp()  {

        GenericTestClass.inventorySetup(
                "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/inventoryupdatepayload/inventoryupdate_abc123.json");
    }

    @Test(priority = 4)

    public void orderSetUp() throws Throwable {

        String orderSetUpObject = GenericTestClass.runOrderSetUp
                ("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/inputorder_1.json",name);
        org.json.JSONObject convert = new org.json.JSONObject(orderSetUpObject);
        if (convert.get("orderId") != null)
            setSysProperty("orderId", convert.get("orderId").toString());
    }

    @Test(priority = 5)

    public void orderVerification() throws Throwable {

      String path = "src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderresponsepayload/1_fulfill_store_multi_shipment_multi_sku_multi_store_fulfilled.json";

      String orderId = getSysProperty("orderId");

      super.orderResponseVerification(orderId,path);


    }


}


