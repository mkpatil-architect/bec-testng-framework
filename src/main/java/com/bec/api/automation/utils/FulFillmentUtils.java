package com.bec.api.automation.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.testng.TestNG;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FulFillmentUtils {
    public final static String MESSAGE_MISMATCH = "Actual and expected message did not match";
    public final static String INCORRECT_RESPONSE = "Response from API is incorrect";
    public final static String INTERNAL_ERROR = "An unexpected internal error has occurred";
    private static Log logger = LogFactory.getLog(FulFillmentUtils.class);

    public static void runSuite(String suitePath) {
       // logger.info("runSuite=" + suitePath);
        List<String> suites = new ArrayList<String>();
        suites.add(suitePath);

        TestNG tng = new TestNG();

        tng.setTestSuites(suites);

        tng.run(); //run test suite
    }

    public static Map<String, Object> getStandardHeaders() {
        Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
        headersMap.put("Content-Type", "application/json");
        return headersMap;
    }

    public static JSONObject getJsonObjectForPost(String fulFillmentObjct) throws Exception{
       // logger.info("getJsonObjectForPost=" + fulFillmentObjct);
        org.json.JSONObject dataObject = new org.json.JSONObject(fulFillmentObjct);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("client_id", RestAssuredUtil.getProperty("ClientId")[0]);
        jsonObject.put("api_key", RestAssuredUtil.getProperty("APIKey")[0]);
        jsonObject.put("data", dataObject);

        return jsonObject;
    }



    public static JSONObject getJsonObjectForPost() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("client_id", RestAssuredUtil.getProperty("ClientId")[0]);
        jsonObject.put("api_key", RestAssuredUtil.getProperty("APIKey")[0]);


        return jsonObject;
    }


    public static ObjectMapper getObjectMapper() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        JavaTimeModule javaTimeModule=new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
        module.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        return new ObjectMapper()
                .registerModule(module)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
