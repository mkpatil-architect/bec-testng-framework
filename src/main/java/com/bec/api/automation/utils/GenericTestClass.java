package com.bec.api.automation.utils;

import com.bec.api.automation.domain.FulFillmentStatus;
import com.bec.api.automation.domain.FUnitTestInventoryUpdate;
import com.bec.api.automation.domain.FUnitTestOrder;
import com.bec.api.automation.domain.FUnitTestStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;


/**
 * @author srjit pal
 */

public class GenericTestClass extends RestAssuredUtil {


    private static Log LOGGER = LogFactory.getLog(GenericTestClass.class);


    private static JSONObject storeInputObject = null;
    private static Response storeSetUpResponse;
    private static JSONObject outputObject = null;
    private static FUnitTestStore fUnitTestStore;
    private static Response unitStoreSetUpResponse;
    private static JSONObject inventoryInputObject = null;
    private static Response inventorySetUpResponse;
    private static JSONObject inventoryOutputObject = null;
    private static FUnitTestInventoryUpdate fUnitTestInventoryUpdate;
    private static Response inventoruyUpdateSetUpResponse;
    private static JSONObject inputOrderObj;
    private static Response orderResponse;
    private static Response unitOrderResponse;
    private static JSONObject outputOrderResponse;
    private static FUnitTestOrder fUnitTestOrder;
    private static JSONParser parser = new JSONParser();
    private static ObjectMapper objectMapper;
    private static Response processOrderResponse;
    private static Response processOrderResponseTimeStamp;
    private static JSONObject originalInputOrder;

    org.json.JSONObject processResponseObj ;
    protected static String client1 = "DCMH__";
    protected static String client2 = "Regular_Store__";



    public static void inventorySetup(String path) {

        LOGGER.debug("inside inventorySetup()  ======>>>>  path is ====>>>  " + path);

        inventoryInputObject = new JSONObject();

        inventoryOutputObject = new JSONObject();

        fUnitTestInventoryUpdate = new FUnitTestInventoryUpdate();


        try {

            // fetching all inventory object based on path

            JSONObject inventoryUpdateJsonObj = (JSONObject)
                    parser.parse
                            (new FileReader
                                    (path));


            // setting up input objects for celect engine call .

            inventoryInputObject = FulFillmentUtils.getJsonObjectForPost(inventoryUpdateJsonObj.get("data").toString());


            // calling celect apis .

            inventorySetUpResponse = postServiceResponse
                    (inventoryInputObject.toJSONString(),
                            "CelectFulFillmentApi/v2/inventory_update", FulFillmentUtils.getStandardHeaders());

            if (inventorySetUpResponse.getStatusCode() == 200) {

                LOGGER.debug("inside inventorySetup() ::==> inventory set Response successful in storeSetUp");

                inventoryOutputObject.put("Output", inventorySetUpResponse.getBody().asString());

            } else {

                inventoryOutputObject.put("Output", storeSetUpResponse.getBody().asString());

                LOGGER.debug("Failure inside inventorySetup() ::==>inventory Response failed in inventorySetup() " + inventorySetUpResponse.toString());

                fail("Failure inside inventorySetup()  ::==>inventory Response failed in inventorySetup() " + inventorySetUpResponse.toString());
            }


            // setting up entity values with input,output,brand,uuid and status values .

            fUnitTestInventoryUpdate.setBrand((String) inventoryUpdateJsonObj.get("brand"));
            fUnitTestInventoryUpdate.setUuid((String) inventoryUpdateJsonObj.get("uuid"));
            fUnitTestInventoryUpdate.setTimeStamp((String) inventoryUpdateJsonObj.get("timeStamp"));
            fUnitTestInventoryUpdate.setInput(inventoryUpdateJsonObj.toString());
            fUnitTestInventoryUpdate.setOutput(inventoryOutputObject.toString());
            fUnitTestInventoryUpdate.setTestStatus(FulFillmentStatus.SUCCESS.toString());


            //Persisting data in to the db

            String uri = getTestProperty("FulFillmentServiceApi/saveUnitTestInventoryUpdate");

            inventoruyUpdateSetUpResponse = given()
                    .contentType("application/json").
                            body(fUnitTestInventoryUpdate).//changing to real obj
                    when().
                            post(uri);

            LOGGER.debug(inventoruyUpdateSetUpResponse.getBody());
            LOGGER.debug("==================================================");


        } catch (Exception e) {

            LOGGER.debug("inside inventorySetup()  ======>>>>   " + e);
            fail("Failure inside inventorySetup()  ::==> path not found " );
        }

    }

    public static void runStoreSetup(String path) {


        LOGGER.info("inside runStoreSetUp()  ======>>>>  path is ====>>>  " + path);

        storeInputObject = new JSONObject();

        outputObject = new JSONObject();

        fUnitTestStore = new FUnitTestStore();

        objectMapper = FulFillmentUtils.getObjectMapper();

        try {

            // fetching all store objects based on path

            JSONObject storeJsonObject = (JSONObject)
                    parser.parse
                            (new FileReader
                                    (path));


            // setting up input objects for celect engine call .

            storeInputObject = FulFillmentUtils.getJsonObjectForPost(storeJsonObject.get("data").toString());


            // calling celect apis .


            storeSetUpResponse = postServiceResponse
                    (storeInputObject.toString(), "CelectFulFillmentApi" + "/v2/stores",
                            FulFillmentUtils.getStandardHeaders());


            if (storeSetUpResponse.getStatusCode() == 200) {

                LOGGER.info("inside runStoreSetUp() ::==> getStore Response successful in storeSetUp");

                outputObject.put("Output", storeSetUpResponse.getBody().asString());

            } else {

                outputObject.put("Output", storeSetUpResponse.getBody().asString());

                LOGGER.info("Failure inside  runStoreSetUp() ::==>getStore Response failed in storeSetUp() " + storeSetUpResponse.toString());

                fail("Failure inside  runStoreSetUp()  ::==>getStore Response failed in storeSetUp() " + storeSetUpResponse.toString());
            }


            // setting up entity values with input,output and status values .


            fUnitTestStore = objectMapper.readValue(storeJsonObject.get("data").toString(), FUnitTestStore.class);


            fUnitTestStore.setInput(fUnitTestStore.toString());
            fUnitTestStore.setOutput(outputObject.toString());
            fUnitTestStore.setTestStatus(FulFillmentStatus.SUCCESS.toString());


            // persisting  data into unit test store table

            String uri = getTestProperty("FulFillmentServiceApi/saveUnitTestStore");



            unitStoreSetUpResponse = given()
                    .contentType("application/json").
                            body(fUnitTestStore).//changing to real obj
                    when().
                            post(uri);
            LOGGER.info(unitStoreSetUpResponse.getBody());
            LOGGER.info("==================================================");


        } catch (IOException e) {

            LOGGER.info("inside runStoreSetUp()  ======>>>>   " + e);

        } catch (org.json.simple.parser.ParseException e) {

            LOGGER.info("inside runStoreSetUp()  ======>>>>   " + e);

        } catch (Exception e) {

            LOGGER.info("inside runStoreSetUp()  ======>>>>   " + e);
        }


    }

    public static String runOrderSetUp(String path,String name) {


        LOGGER.debug("inside runOrderSetUp()  ======>>>>  path is ====>>>  " + path);


        inputOrderObj = new JSONObject();
        outputOrderResponse = new JSONObject();
        fUnitTestOrder = new FUnitTestOrder();
        JSONObject orderSetUpJsonObj;
        String uri;


        try {



            orderSetUpJsonObj = (JSONObject) parser.parse(new FileReader(path));

            // setting up input objects for celect engine call

            inputOrderObj = FulFillmentUtils.getJsonObjectForPost(orderSetUpJsonObj.get("data").toString());

            //fetching order id from orderSetUpJsonObj.get("data") object .

            originalInputOrder = (JSONObject) parser.parse(orderSetUpJsonObj.get("data").toString());

            //setting up entity values with input,brand,uuid and status values .

            fUnitTestOrder.setTestName(name);
            fUnitTestOrder.setInput(orderSetUpJsonObj.toString());
            fUnitTestOrder.setOrderId(originalInputOrder.get("orderId").toString());
            fUnitTestOrder.setTestStatus(FulFillmentStatus.PENDING.toString());


            //Persisting data in to the db


            uri = getTestProperty("FulFillmentServiceApi/saveUnitTestOrder");

            unitOrderResponse = given()
                    .contentType("application/json").
                            body(fUnitTestOrder).//changing to real obj
                    when().
                            post(uri);


            LOGGER.debug(unitOrderResponse.getBody());


            orderResponse = postServiceResponse
                    (inputOrderObj.toJSONString(),
                            "CelectFulFillmentApi/v2/orders", FulFillmentUtils.getStandardHeaders());



            if (orderResponse.getStatusCode() == 200) {

                LOGGER.debug("getOrder::==> Successful");

                outputOrderResponse.put("Output", orderResponse.getBody().asString());

            } else {

                outputOrderResponse.put("Output", orderResponse.getBody().asString());

                LOGGER.debug("Failure::==>getOrder::==>Order set up failed " + orderResponse.toString());

                LOGGER.debug("Failure::==>getOrder::==>Order set up failed " + orderResponse.toString());

                fail("Failure::==>Order set up failed " + orderResponse.toString());
            }


            //setting a input object  to the system property .

            setSysProperty("orderInputObject", unitOrderResponse.asString());


            org.json.JSONObject dataObject = new org.json.JSONObject(orderSetUpJsonObj.get("data").toString());

            return dataObject.toString();


        } catch (Exception e) {

            LOGGER.debug("Inside runOrderSetUp() ====>>>> exception is =====>>>>  " + e);
            fail("Failure::==>Order set up failed ======>>>>  Path not found"  );
        }

        return null;
    }


     public void orderResponseVerification(String orderId, String path) {


        fUnitTestOrder = new FUnitTestOrder();

         super.startTime= System.currentTimeMillis();
         super.endTime = startTime + totalInterval;

        try {

            JSONObject celectOrderResponseObject = (JSONObject)
                    parser.parse
                            (new FileReader
                                    (path));


            String reportOrderInputObject = getSysProperty("orderInputObject");

            fUnitTestOrder = objectMapper.readValue(reportOrderInputObject, FUnitTestOrder.class);

            while ( super.startTime < super.endTime) {

                String uri = getTestProperty("FulFillmentServiceApi/getOrderResponse");
                String uriTimeStamp = getTestProperty("FulFillmentServiceApi/getOrderResponseTimeStamp");
                //calling celect service to get process order response and time stamp for a given order id.

                processOrderResponse = given()
                        .contentType("application/json").
                                body(orderId).
                                when().
                                post(uri);
                setSysProperty("outputObject", processOrderResponse.asString());

                processResponseObj = new org.json.JSONObject(processOrderResponse.asString());

                processOrderResponseTimeStamp = given()
                        .contentType("application/json").
                                body(orderId).
                                when().
                                post(uriTimeStamp);

                if (!processOrderResponse.asString().equals("") && !processOrderResponseTimeStamp.asString().equals("")) {
                    break;
                }

                // slept for 1 min and adding continue looping

                Thread.sleep(super.interval);

                super.startTime += super.interval;
            }

            if (!processOrderResponse.asString().equals("") && !processOrderResponseTimeStamp.asString().equals("")) {


                if (celectOrderResponseObject.toString().equals(processResponseObj.toString())) {

                    //resetting corresponding values

                    fUnitTestOrder.setOrderResponse(processOrderResponse.asString());
                    fUnitTestOrder.setOrderReceivedTime(processOrderResponseTimeStamp.asString());
                    fUnitTestOrder.setTestStatus(FulFillmentStatus.SUCCESS.toString());
                    String uri = getTestProperty("FulFillmentServiceApi/saveUnitTestOrder");

                    // updating the object with status success.


                    unitOrderResponse = given()
                            .contentType("application/json").
                                    body(fUnitTestOrder).//changing to real obj
                            when().
                                    post(uri);

                }else{

                    fUnitTestOrder.setTestStatus(FulFillmentStatus.FAILED.toString());
                    fUnitTestOrder.setOrderResponse(processOrderResponse.asString());
                    fUnitTestOrder.setOrderReceivedTime(processOrderResponseTimeStamp.asString());

                    String uri = getTestProperty("FulFillmentServiceApi/saveUnitTestOrder");

                    // updating the object with status failed.

                    unitOrderResponse = given()
                            .contentType("application/json").
                                    body(fUnitTestOrder).//changing to real obj
                            when().
                                    post(uri);

                }

            }else{


                fUnitTestOrder.setTestStatus(FulFillmentStatus.FAILED.toString());

                String uri = getTestProperty("FulFillmentServiceApi/saveUnitTestOrder");

                // updating the object with status failed.

                unitOrderResponse = given()
                        .contentType("application/json").
                                body(fUnitTestOrder).//changing to real obj
                        when().
                                post(uri);

            }


        } catch (Exception e) {

            LOGGER.info("Inside orderResponseVerification() ====>>>> exception is =====>>>>  " + e);
            fail("Failure::==>Order response verification failed ======>>>>  Path not found");

        }

         super.startTime = 0;
         super.endTime =0;

    }
}
