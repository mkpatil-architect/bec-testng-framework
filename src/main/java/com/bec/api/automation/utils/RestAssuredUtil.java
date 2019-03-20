package com.bec.api.automation.utils;

import com.bec.api.automation.core.UseDifferentTestName;
import com.bec.api.automation.core.UseEncodedURL;
import com.bec.api.automation.domain.orderinput.OrderInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Predicate;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.DecoderConfig;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.TestException;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * Created by mkpatil on 20/10/17.
 */
@SuppressWarnings("Duplicates")
public class RestAssuredUtil
        extends FileUtil
        implements ITest {
    public static ITestContext myTestContext;
    public static String testCaseId;
    static String extractTokenKey = null;
    static Boolean urlEncoded = Boolean.valueOf(true);
    static String actualTestName;
    static Response response = null;
    static PropertiesCache cache = PropertiesCache.getInstance();
    private static Log LOGGER = LogFactory.getLog(RestAssuredUtil.class);
    private static RestOperations restTemplate;

    private static int urlListCount;
    private static int objectListCount;
    private static List<String> objectList = new ArrayList<>();
    private static List<String> urlList = new ArrayList<>();
    private static Map<String, Object> orderInputValues = new HashMap<String, Object>();
    private static JSONParser parser = new JSONParser();
    private static ObjectMapper objectMapper;
    private static OrderInput orderInput;

    protected static int totalInterval = 300000;
    protected static int interval = 60000;
    protected static  long startTime ;
    protected static long endTime ;

    @JsonProperty("access_token")
    public String ingestorToken;
    private int idx = 1;
    private String testInstanceName = "";

    public static final String getToken(String URL, Map<String, Object> queryParams, Map<String, Object> headers) {
        RestAssured.useRelaxedHTTPSValidation();

        RequestSpecification request = RestAssured.given();
        RestAssured.baseURI = URL;

        if ((queryParams != null) && (!queryParams.isEmpty()))
            request.queryParameters(queryParams);
        if ((headers != null) && (!headers.isEmpty())) {
            request.headers(headers);
        }


        response = (Response) request.when().post(RestAssured.baseURI + "/oauth2/token", new Object[0]);
        if (response != null) {
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            com.jayway.restassured.path.json.JsonPath jsonPath = new com.jayway.restassured.path.json.JsonPath(response.asString());
            isExist("Token error", jsonPath.getString("tokenKey"));
            extractTokenKey = jsonPath.getString("tokenKey");
            LOGGER.info("Token Key : " + extractTokenKey);
        }
        return extractTokenKey;
    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static final String getToken(String URL, Map<String, Object> queryParams, Map<String, Object> headers, String tokenURL) {
        RestAssured.useRelaxedHTTPSValidation();

        RequestSpecification request = RestAssured.given();
        RestAssured.baseURI = URL;

        if ((queryParams != null) && (!queryParams.isEmpty()))
            request.queryParameters(queryParams);
        if ((headers != null) && (!headers.isEmpty())) {
            request.headers(headers);
        }


        response = (Response) request.when().post(RestAssured.baseURI + tokenURL, new Object[0]);

        if (response != null) {
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            com.jayway.restassured.path.json.JsonPath jsonPath = new com.jayway.restassured.path.json.JsonPath(response.asString());
            isExist("Token error", jsonPath.getString("tokenKey"));
            extractTokenKey = jsonPath.getString("tokenKey");
            LOGGER.info("Token Key : " + extractTokenKey);
        }
        return extractTokenKey;
    }

    public static String getToken(String psdBaseUrl, String psdGrantType, String psdUsername, String pswPassword, String pwdClientId, String pwdClientSecret) {
        String baseUrl = null;
        String grantType = null;
        String username = null;
        String password = null;
        String clientId = null;
        String clientSecret = null;
        String testPropertyFlag = getIgnoreTestProperty();
        if (testPropertyFlag.equalsIgnoreCase("true")) {
            baseUrl = psdBaseUrl;
            grantType = psdGrantType;
            username = psdUsername;
            password = pswPassword;
            clientId = pwdClientId;
            clientSecret = pwdClientSecret;
        } else {
            baseUrl = getProperty(psdBaseUrl)[0];
            grantType = getProperty(psdGrantType)[0];
            username = getProperty(psdUsername)[0];
            password = getProperty(pswPassword)[0];
            clientId = getProperty(pwdClientId)[0];
            clientSecret = getProperty(pwdClientSecret)[0];
        }
        String dfClientSecret = "";
        String dfGrantType = "password";
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap();

        String basicDigestHeaderValue;
        if ((grantType != null) || (grantType.isEmpty())) {
            formParams.add("grant_type", grantType);
            formParams.add("username", username);
            formParams.add("password", password);
            basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(new StringBuilder().append(clientId).append(":").append(clientSecret).toString().getBytes()));
        } else {
            formParams.add("grant_type", dfGrantType);
            formParams.add("username", username);
            formParams.add("password", password);
            basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(new StringBuilder().append(clientId).append(":").append(dfClientSecret).toString().getBytes()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", basicDigestHeaderValue);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        LOGGER.info("To obtain OAuth token of " + username + " from UAA server");

        HttpEntity<MultiValueMap<String, String>> formEncodedRequest = new HttpEntity(formParams, headers);
        setProxy();
        try {
            RestAssuredUtil oauthToken = (RestAssuredUtil) restTemplate.postForObject(baseUrl, formEncodedRequest, RestAssuredUtil.class, new Object[0]);
            LOGGER.info("Successfully obtained OAuth token of " + username + " from UAA server: " + oauthToken.ingestorToken);
            return extractTokenKey = oauthToken.ingestorToken;

        } catch (RuntimeException e) {
            LOGGER.error(e);
            throw new TestException(e);
        }
    }

    public static String getAPMToken(String baseUrl, String username, String password, String clientId, String clientSecret) {
        String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(new StringBuilder().append(clientId).append(":").append(clientSecret).toString().getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", basicDigestHeaderValue);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap();
        formParams.add("grant_type", "password");
        formParams.add("username", username);
        formParams.add("password", password);

        LOGGER.info("To obtain OAuth token of " + username + " from UAA server");

        HttpEntity<MultiValueMap<String, String>> formEncodedRequest = new HttpEntity(formParams, headers);
        setProxy();
        try {
            RestAssuredUtil oauthToken = (RestAssuredUtil) restTemplate.postForObject(baseUrl, formEncodedRequest, RestAssuredUtil.class, new Object[0]);
            LOGGER.info("Successfully obtained OAuth token of " + username + " from UAA server: " + oauthToken.ingestorToken);
            return extractTokenKey = oauthToken.ingestorToken;

        } catch (RuntimeException e) {
            LOGGER.error(e);
            throw new TestException(e);
        }
    }

    public static String getAPMIngestorToken() {
        String baseUrl = getProperty("uaaUrl")[0];
        String uaaPsw = getProperty("uaaPsw")[0];
        String username = getProperty("tenantUUID")[0] + "_ingestor";
        String clientId = getProperty("ingestorClientId")[0];
        return getAPMToken(baseUrl, username, uaaPsw, clientId, "");
    }

    public static String getAPMMdmToken() {
        String username = getProperty("managerUser")[0];
        String password = getProperty("managerPsw")[0];
        String clientId = getProperty("ingestorClientId")[0];
        String baseUrl = getProperty("uaaUrl")[0];

        return getAPMToken(baseUrl, username, password, clientId, "");
    }

    public static String getAPMSystemAdminToken() {
        String username = getProperty("tenantAdminName")[0];
        String password = getProperty("tenantAdminPasswd")[0];
        String clientId = getProperty("ingestorClientId")[0];
        String baseUrl = getProperty("uaaUrl")[0];
        return getAPMToken(baseUrl, username, password, clientId, "");
    }

    public static Response getServiceResponse(String serviceURL) {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri = null;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else {
            uri = normalizeUri(serviceURL);
        }


        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        long starttime = System.currentTimeMillis();


        response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).when().get(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();

        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static String[] getProxy() {
        String[] defProxyDetails = {"proxy-src.research.com", "8080"};
        String httpProxy = getTestProperty("httpProxy");
        String portNumber = getTestProperty("portNumber");
        String[] proxyDetails = {httpProxy, portNumber};
        if (proxyDetails[0] == null) {
            proxyDetails[0] = "proxy-src.research.com";
        }
        if (proxyDetails[1] == null) {
            proxyDetails[1] = "8080";
        }

        return proxyDetails;
    }

    public static Response getServiceResponse(String serviceURL, Map<String, Object> params) {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);

        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();
        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(true)));
        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if (((params != null) && (!params.isEmpty()) && (params.containsKey("Authorization"))) || (params.containsKey("Content-Type")) || (params.containsKey("content-type"))) {
            request.headers(params);
        } else
            request.queryParameters(params);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).when().get(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response getServiceResponse(String serviceURL, Map<String, Object> queryParams, Map<String, Object> headers) {
        /*  528 */
        String testPropertyFlag = getIgnoreTestProperty();
        /*  529 */
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            /*  530 */
            uri = getTestProperty(serviceURL);
            /*      */
        } else
            /*  532 */       uri = serviceURL;
        /*  533 */
        uri = normalizeUri(uri);
        /*  534 */
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        /*  535 */
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        /*  536 */
        String proxyDetail = proxyDetail();
        /*  537 */
        RequestSpecification request = RestAssured.given();
        /*      */
        /*  539 */
        if (proxyDetail.equalsIgnoreCase("true")) {
            /*  540 */
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
            /*      */
        }
        /*  542 */
        if ((queryParams != null) && (!queryParams.isEmpty())) {
            /*  543 */
            String value = "";
            /*  544 */
            request.queryParameters(queryParams);
            /*      */
        }
        /*  546 */
        if ((headers != null) && (!headers.isEmpty()))
            /*  547 */ request.headers(headers);
        /*  548 */
        LOGGER.info(queryParams);
        /*  549 */
        long starttime = System.currentTimeMillis();
        /*      */
        /*      */
        /*      */
        /*  553 */
        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).when().get(uri, new Object[0]);
        /*  554 */
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        /*  555 */
        long endtime = System.currentTimeMillis();
        /*  556 */
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        /*  557 */
        return response;
        /*      */
    }

    public static Response postServiceResponse(String serviceURL, Map<String, Object> headers)
            throws IOException {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();
        RequestSpecification request = RestAssured.given().config(RestAssured.config().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();

        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).when().post(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response postServiceResponse(String absfilename, String serviceURL, Map<String, Object> headers)
            throws IOException {
        File providedFile = null;
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String paylaod = FilenameUtils.getExtension(absfilename);
        if ((paylaod.equalsIgnoreCase("txt")) || (paylaod.equalsIgnoreCase("json")) || (paylaod.equalsIgnoreCase("jpg")) || (paylaod.equalsIgnoreCase("csv")) || (paylaod.equalsIgnoreCase("dat")) || (paylaod.equalsIgnoreCase("zip"))) {
            providedFile = new File(absfilename);
            if (!providedFile.exists())
                try {
                    throw new Exception("File: " + providedFile.getAbsolutePath() + " doesn't exist.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        } else {
            providedFile = genrateFileFromString(absfilename);
        }
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((headers != null) && (!headers.isEmpty())) {
            request.headers(headers);
        }
        if ((!headers.containsValue("multipart/form-data")) || (headers.containsValue("multipart/formdata")) || (headers.containsValue("multipart/mixed"))) {
            long starttime = System.currentTimeMillis();

            Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue())
                    .content(FileUtils.readFileToByteArray(providedFile)).when().post(uri, new Object[0]);
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            long endtime = System.currentTimeMillis();
            myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
            return response;
        }
        long starttime = System.currentTimeMillis();

        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).multiPart(new File(absfilename)).when().post(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response postServiceResponse(String absfilename, String serviceURL, Map<String, Object> queryParams, Map<String, Object> headers)
            throws IOException {
        File providedFile = null;
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String paylaod = FilenameUtils.getExtension(absfilename);
        if ((paylaod.equalsIgnoreCase("txt")) || (paylaod.equalsIgnoreCase("json")) || (paylaod.equalsIgnoreCase("jpg")) || (paylaod.equalsIgnoreCase("csv")) || (paylaod.equalsIgnoreCase("dat")) || (paylaod.equalsIgnoreCase("zip"))) {
            providedFile = new File(absfilename);
            if (!providedFile.exists())
                try {
                    throw new Exception("File: " + providedFile.getAbsolutePath() + " doesn't exist.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        } else {
            providedFile = genrateFileFromString(absfilename);
        }
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }

        if ((!headers.containsValue("multipart/form-data")) || (headers.containsValue("multipart/formdata")) || (headers.containsValue("multipart/mixed"))) {
            if ((queryParams != null) && (!queryParams.isEmpty()))
                request.queryParameters(queryParams);
            if ((headers != null) && (!headers.isEmpty()))
                request.headers(headers);
            long starttime = System.currentTimeMillis();

            Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).content(FileUtils.readFileToByteArray(providedFile)).when().post(uri, new Object[0]);
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            long endtime = System.currentTimeMillis();
            myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
            return response;
        }

        if ((queryParams != null) && (!queryParams.isEmpty()))
            request.queryParameters(queryParams);
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).multiPart(new File(absfilename)).when().post(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response putServiceResponse(String serviceURL)
            throws IOException {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        long starttime = System.currentTimeMillis();

        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue())
                .when().put(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response putServiceResponse(String serviceURL, Map<String, Object> headers)
            throws IOException {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((headers != null) && (!headers.isEmpty())) {
            request.headers(headers);
        }
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).when().put(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response putServiceResponse(String absfilename, String serviceURL, Map<String, Object> headers)
            throws IOException {
        File providedFile = null;
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);

        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String paylaod = FilenameUtils.getExtension(absfilename);
        if ((paylaod.equalsIgnoreCase("txt")) || (paylaod.equalsIgnoreCase("json")) || (paylaod.equalsIgnoreCase("jpg")) || (paylaod.equalsIgnoreCase("csv")) || (paylaod.equalsIgnoreCase("dat")) || (paylaod.equalsIgnoreCase("zip"))) {
            providedFile = new File(absfilename);
            if (!providedFile.exists())
                try {
                    throw new Exception("File: " + providedFile.getAbsolutePath() + " doesn't exist.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        } else {
            providedFile = genrateFileFromString(absfilename);
        }
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        if ((!headers.containsValue("multipart/form-data")) || (headers.containsValue("multipart/formdata")) || (headers.containsValue("multipart/mixed"))) {
            long starttime = System.currentTimeMillis();

            Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).content(FileUtils.readFileToByteArray(providedFile)).when().put(uri, new Object[0]);
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            long endtime = System.currentTimeMillis();
            myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
            return response;
        }

        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).multiPart(new File(absfilename)).when().put(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response putServiceResponse(String absfilename, String serviceURL, Map<String, Object> queryParams, Map<String, Object> headers)
            throws IOException {
        File providedFile = null;
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String paylaod = FilenameUtils.getExtension(absfilename);
        if ((paylaod.equalsIgnoreCase("txt")) || (paylaod.equalsIgnoreCase("json")) || (paylaod.equalsIgnoreCase("jpg")) || (paylaod.equalsIgnoreCase("csv")) || (paylaod.equalsIgnoreCase("dat")) || (paylaod.equalsIgnoreCase("zip"))) {
            providedFile = new File(absfilename);
            if (!providedFile.exists())
                try {
                    throw new Exception("File: " + providedFile.getAbsolutePath() + " doesn't exist.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        } else {
            providedFile = genrateFileFromString(absfilename);
        }
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }

        if ((!headers.containsValue("multipart/form-data")) || (headers.containsValue("multipart/formdata")) || (headers.containsValue("multipart/mixed"))) {
            if ((queryParams != null) && (!queryParams.isEmpty()))
                request.queryParameters(queryParams);
            if ((headers != null) && (!headers.isEmpty()))
                request.headers(headers);
            long starttime = System.currentTimeMillis();

            Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).content(FileUtils.readFileToByteArray(providedFile)).when().put(uri, new Object[0]);
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            long endtime = System.currentTimeMillis();
            myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
            return response;
        }
        if ((queryParams != null) && (!queryParams.isEmpty()))
            request.queryParameters(queryParams);
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();
        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).multiPart(new File(absfilename)).when().put(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response patchServiceResponse(String serviceURL, Map<String, Object> headers)
            throws IOException {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);

        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).when().patch(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response patchServiceResponse(String absfilename, String serviceURL, Map<String, Object> headers)
            throws IOException {
        File providedFile = null;
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);

        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String paylaod = FilenameUtils.getExtension(absfilename);
        if ((paylaod.equalsIgnoreCase("txt")) || (paylaod.equalsIgnoreCase("json")) || (paylaod.equalsIgnoreCase("jpg")) || (paylaod.equalsIgnoreCase("csv")) || (paylaod.equalsIgnoreCase("dat")) || (paylaod.equalsIgnoreCase("zip"))) {
            providedFile = new File(absfilename);
            if (!providedFile.exists())
                try {
                    throw new Exception("File: " + providedFile.getAbsolutePath() + " doesn't exist.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        } else {
            providedFile = genrateFileFromString(absfilename);
        }
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((!headers.containsValue("multipart/form-data")) || (headers.containsValue("multipart/formdata")) || (headers.containsValue("multipart/mixed"))) {
            if ((headers != null) && (!headers.isEmpty()))
                request.headers(headers);
            long starttime = System.currentTimeMillis();


            Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).content(FileUtils.readFileToByteArray(providedFile)).when().patch(uri, new Object[0]);
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            long endtime = System.currentTimeMillis();
            myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
            return response;
        }
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();

        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).multiPart(new File(absfilename)).when().patch(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response patchServiceResponse(String absfilename, String serviceURL, Map<String, Object> queryParams, Map<String, Object> headers)
            throws IOException {
        File providedFile = null;
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String paylaod = FilenameUtils.getExtension(absfilename);
        if ((paylaod.equalsIgnoreCase("txt")) || (paylaod.equalsIgnoreCase("json")) || (paylaod.equalsIgnoreCase("jpg")) || (paylaod.equalsIgnoreCase("csv")) || (paylaod.equalsIgnoreCase("dat")) || (paylaod.equalsIgnoreCase("zip"))) {
            providedFile = new File(absfilename);
            if (!providedFile.exists())
                try {
                    throw new Exception("File: " + providedFile.getAbsolutePath() + " doesn't exist.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        } else {
            providedFile = genrateFileFromString(absfilename);
        }
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((!headers.containsValue("multipart/form-data")) && (!headers.containsValue("multipart/formdata")) && (!headers.containsValue("multipart/mixed"))) {
            if ((queryParams != null) && (!queryParams.isEmpty()))
                request.queryParameters(queryParams);
            if ((headers != null) && (!headers.isEmpty()))
                request.headers(headers);
            long starttime = System.currentTimeMillis();
            Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).content(FileUtils.readFileToByteArray(providedFile)).when().patch(uri, new Object[0]);
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            long endtime = System.currentTimeMillis();
            myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
            return response;
        }
        if ((queryParams != null) && (!queryParams.isEmpty()))
            request.queryParameters(queryParams);
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).multiPart(new File(absfilename)).when().patch(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response deleteServiceResponse(String serviceURL, Map<String, Object> queryParams, Map<String, Object> headers)
            throws IOException {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        if ((queryParams != null) && (!queryParams.isEmpty()))
            request.queryParameters(queryParams);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue())
                .when().delete(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response deleteServiceResponse(String serviceURL, Map<String, Object> headers)
            throws IOException {
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);
        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }

        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).when().delete(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static Response deleteServiceResponse(String absfilename, String serviceURL, Map<String, Object> headers)
            throws IOException {
        File providedFile = null;
        String testPropertyFlag = getIgnoreTestProperty();
        String uri;
        if (!testPropertyFlag.equalsIgnoreCase("true")) {
            uri = getTestProperty(serviceURL);
        } else
            uri = serviceURL;
        uri = normalizeUri(uri);

        LOGGER.info("\nHostname URL --> " + uri + "\n");
        myTestContext.setAttribute("HostURI-" + testCaseId, uri);
        String paylaod = FilenameUtils.getExtension(absfilename);
        if ((paylaod.equalsIgnoreCase("txt")) || (paylaod.equalsIgnoreCase("json")) || (paylaod.equalsIgnoreCase("jpg")) || (paylaod.equalsIgnoreCase("csv")) || (paylaod.equalsIgnoreCase("dat")) || (paylaod.equalsIgnoreCase("zip"))) {
            providedFile = new File(absfilename);
            if (!providedFile.exists())
                try {
                    throw new Exception("File: " + providedFile.getAbsolutePath() + " doesn't exist.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        } else {
            providedFile = genrateFileFromString(absfilename);
        }
        String proxyDetail = proxyDetail();

        RequestSpecification request = RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (proxyDetail.equalsIgnoreCase("true")) {
            request.proxy(getProxy()[0], Integer.parseInt(getProxy()[1]));
        }
        if ((headers != null) && (!headers.isEmpty()))
            request.headers(headers);
        if ((!headers.containsValue("multipart/form-data")) || (headers.containsValue("multipart/formdata")) || (headers.containsValue("multipart/mixed"))) {
            long starttime = System.currentTimeMillis();


            Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).content(FileUtils.readFileToByteArray(providedFile)).when().delete(uri, new Object[0]);
            LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
            long endtime = System.currentTimeMillis();
            myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
            return response;
        }
        long starttime = System.currentTimeMillis();


        Response response = (Response) request.urlEncodingEnabled(urlEncoded.booleanValue()).multiPart(new File(absfilename)).when().delete(uri, new Object[0]);
        LOGGER.info("\nRESPONSE --> " + response.asString() + "\n");
        long endtime = System.currentTimeMillis();
        myTestContext.setAttribute("ResponseTime-" + testCaseId, Long.valueOf(endtime - starttime));
        return response;
    }

    public static void setProxy() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();


        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(getProxy()[0], Integer.parseInt(getProxy()[1])));
        requestFactory.setProxy(proxy);

        restTemplate = new RestTemplate(requestFactory);
    }

    public static String generateQueryParam(Map<String, Object> params) {
        StringBuilder str = new StringBuilder();
        String result = null;
        try {
            if (params != null) {
                for (Entry<String, Object> entry : params.entrySet()) {
                    str.append((String) entry.getKey() + "=" + entry.getValue());
                    str.append("&");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        result = str.substring(0, str.length() - 1).toString();
        return result;
    }

    public static void setSysProperty(String propName, String propValue) {
        if (propValue != null) {
            System.setProperty(propName, propValue);
        } else {
            System.setProperty(propName, null);
        }
    }

    public static String getSysProperty(String propName) {
        String propValue = System.getProperty(propName);
        if (propValue != null) {
            return propValue;
        }
        return null;
    }

    public static String getResponseByCondition(String json, String jsonQueryName) {
        String jsonQuery = jsonQueryName == null ? null : getProperty(jsonQueryName)[0];
        if (jsonQuery == null)
            return null;
        jsonQuery = normalizeUri(jsonQuery);
        Object obj = com.jayway.jsonpath.JsonPath.read(json, jsonQuery, new Predicate[0]);
        if (obj != null) {
            if ((obj instanceof List)) {
            }
            return obj.toString();
        }
        return null;
    }

    public static List<?> getListResponseByCondition(String json, String jsonQueryName) {
        String jsonQuery = jsonQueryName == null ? null : getProperty(jsonQueryName)[0];
        if (jsonQuery == null)
            return null;
        jsonQuery = normalizeUri(jsonQuery);
        Object obj = com.jayway.jsonpath.JsonPath.read(json, jsonQuery, new Predicate[0]);
        if (obj != null) {
            if ((obj instanceof List)) {
                return (List) obj;
            }
            return Arrays.asList(new String[]{obj.toString()});
        }

        return null;
    }

    public static String getTestProperty(String propertyName) {
        String uri = null;
        String firstUrl = null;
        String lastUrl = null;
        try {
            if (getSysProperty(propertyName) != null) {
                return getSysProperty(propertyName);
            }
            if (propertyName.contains("/")) {
                int indexURL = propertyName.indexOf("/");

                String[] parsedServiceURL = propertyName.split("/");
                firstUrl = propertyName == null ? null : getProperty(parsedServiceURL[0])[0];
                lastUrl = propertyName.substring(indexURL, propertyName.length());
                uri = firstUrl + lastUrl;
                return normalizeUri(uri);
            }
            if (propertyName.contains("?")) {
                int indexURL = propertyName.indexOf("?");

                String[] parsedServiceURL = propertyName.split("\\?");
                //  System.out.println(parsedServiceURL);
                firstUrl = propertyName == null ? null : getProperty(parsedServiceURL[0])[0];

                lastUrl = propertyName.substring(indexURL, propertyName.length());
                uri = firstUrl + lastUrl;
                return normalizeUri(uri);
            }

            firstUrl = getProperty(propertyName)[0];
            uri = firstUrl;
            return normalizeUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String proxyDetail() {
        String ignoreProxyValueFlag = cache.getProperty("proxy");

        if (ignoreProxyValueFlag == null) {
            return "true";
        }
        return ignoreProxyValueFlag;
    }

    public static String getIgnoreTestProperty() {
        String getIgnoreTestPropertyFlag = cache.getProperty("ignoreTestProperty");
        if (getIgnoreTestPropertyFlag == null) {
            return "false";
        }
        return getIgnoreTestPropertyFlag;
    }

    public static String normalizeUri(String uri) {
        Map<String, Object> vars = new HashMap();
        Map<String, Object> map = configPropertyLookup(Arrays.asList(new String[]{uri}), vars);
        StrSubstitutor varStr = new StrSubstitutor(map);
        return varStr.replace(uri);
    }

    public static Map<String, Object> getFieldNamesAndValues(Object valueObj) {
        LOGGER.info("Begin - getFieldNamesAndValues");
        Class c1 = null;
        try {
            c1 = getCallerClass(3);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        LOGGER.info("Class name got is:: " + c1.getName());
        Map<String, Object> fieldMap = new HashMap();
        Field[] valueObjFields = c1.getDeclaredFields();

        Method[] mthdObj = c1.getDeclaredMethods();

        Object dynamicObj = null;
        try {
            dynamicObj = c1.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < valueObjFields.length; i++) {
            String fieldName = valueObjFields[i].getName();
            String varType = valueObjFields[i].getType().getTypeName();
            java.lang.reflect.Type genericFieldType = valueObjFields[i].getGenericType();
            if ((genericFieldType instanceof ParameterizedType)) {
                ParameterizedType aType = (ParameterizedType) genericFieldType;
                java.lang.reflect.Type[] fieldArgTypes = aType.getActualTypeArguments();
                for (java.lang.reflect.Type fieldArgType : fieldArgTypes) {
                    Class fieldArgClass = (Class) fieldArgType;
                    LOGGER.info("fieldArgClass = " + fieldArgClass);
                }
            }

            LOGGER.info("Getting Field Type for Field:: " + varType);

            LOGGER.info("Getting Field Values for Field:: " + valueObjFields[i].getName());
            valueObjFields[i].setAccessible(true);
            Object newObj = null;
            try {
                newObj = valueObjFields[i].get(dynamicObj);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            LOGGER.info("Value of field" + fieldName + "newObj:: " + newObj);
            fieldMap.put(fieldName, newObj);
        }

        //System.out.println("End - getFieldNamesAndValues");
        return fieldMap;
    }

    public static Class getCallerClass(int level) throws ClassNotFoundException {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String rawFQN = stElements[(level + 3)].toString().split("\\(")[0];
        return Class.forName(rawFQN.substring(0, rawFQN.lastIndexOf('.')));
    }

    public static String getCallerClassMethod(int level) throws ClassNotFoundException {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String rawFQN = stElements[(level + 3)].toString().split("\\(")[0];
        return rawFQN.substring(rawFQN.lastIndexOf('.') + 1, rawFQN.length());
    }

    public static String extractDate(String givenTime) throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        String actualDate = formatter.format(calendar.getTime());
        Date dt = formatter.parse(actualDate);
        DateFormat simpleDt = new SimpleDateFormat("MM/dd/yyyy");
        String dateOnly = simpleDt.format(dt);
        return dateOnly;
    }

    public static JSONObject parserFromObj(String path) {

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> findAllFilesInaGivenDirectory(File dir) throws Exception {

        LOGGER.debug("inside findAllFilesInaGivenDirectory" + dir);
        List<String> hold_list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                findAllFilesInaGivenDirectory(file);
            } else {
                if (!file.getName().contains("reports_realtime.json"))
                    hold_list.add(file.toString());
            }
        }

        return hold_list;

    }

    public static List<String> allUrlsForUnitTestResetting() throws Exception {

        List<String> urlList = new ArrayList<>();
        urlList.add("/v2/optimization_configuration/reset_state");
        // urlList.add("/v2/store_response/reset_state");
        urlList.add("/v2/stores/reset_state");
        urlList.add("/v2/inventory/reset_state");

        return urlList;

    }

    public static boolean compareJson(org.json.simple.JSONObject Object) {

        try {
            JSONParser parser = new JSONParser();
            Object obj1 = parser.parse(new FileReader("src/main/resources/payloads/responseJsonStructure/actualResponseStrucure.json"));
            org.json.simple.JSONObject realObj = (org.json.simple.JSONObject) obj1;
            Set<String> keys_real = realObj.keySet();
            Set<String> keys_obj = Object.keySet();
            if (keys_real.containsAll(keys_obj))
                return true;
        } catch (IOException e) {
            LOGGER.info("compare json error occured" + e);
        } catch (org.json.simple.parser.ParseException e) {
            LOGGER.info("parse json error occured in =======>> compare json method " + e);
        }
        return false;
    }


    public static void ResetAll() throws Exception {

        urlListCount = 0;
        objectListCount = 0;

        try {
            RestAssuredUtil.findAllFilesInaGivenDirectory(new File("src/main/resources/payloads/unittestpayloads"))
                    .stream()
                    .forEach((key) -> {
                        JSONObject Object = RestAssuredUtil.parserFromObj(key);
                        objectList.add(Object.toString());
                    });

            urlList = RestAssuredUtil.allUrlsForUnitTestResetting();

            while (urlListCount < urlList.size()) {

                while (objectListCount < objectList.size()) {

                    //Create request builder

                    RequestSpecBuilder builder = new RequestSpecBuilder();

                    //Set the body content

                    builder.setBody(objectList.get(objectListCount));

                    //Set the request content type

                    builder.setContentType("application/json; charset=UTF-8");

                    //Build the spec

                    RequestSpecification requestSpec = builder.build();

                    //printing input before calling method

                   /* System.out.println("url is =====>>>  "
                            +"https://fulfillment-api-test.celectengine.com/api"+urlList.get(urlListCount).toString());
*/
                    Response response = RestAssured.
                            given()
                            .spec(requestSpec)
                            .when()
                            .post("https://fulfillment-api-test.celectengine.com/api" + urlList.get(urlListCount).toString());


                    if (response.getStatusCode() == 200) {
                        LOGGER.info("unitTestSetUp::==> unitTestSetUp Response successful");
                    } else {
                        fail("unitTestSetUp::==> set up failed " + response.toString());
                        LOGGER.info("Failure::==>unitTestSetUp Response failed " + response.toString());
                    }
                    break;
                }
                urlListCount++;
                objectListCount++;
            }
        } catch (Exception e) {
            LOGGER.info("Failure::==>ResetAll () ====== >>>> failed " + e);
        }


    }

    public static void fetchFromInutObject() {

        orderInput = new OrderInput();

        try {
            JSONObject orderInputJsonObj = (JSONObject)
                    parser.parse
                            (new FileReader
                                    ("src/main/resources/payloads/fulfilment_unit_test_payload/dcmh/orderpayload/inputorder_1.json"));


            objectMapper = FulFillmentUtils.getObjectMapper();
            orderInput = objectMapper.readValue(orderInputJsonObj.toString(), OrderInput.class);

            System.out.println(orderInput.toString());

            orderInputValues.put("Brand", (Object) orderInput.getData().getBrand());

            orderInputValues.put("OrderId", (Object) orderInput.getData().getOrderId());


            //catch everything
            //needs to be completed


        } catch (IOException e) {

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }


    public String getTestName() {
        return this.testInstanceName;
    }

    private void setTestName(String anInstanceName) {
        this.testInstanceName = anInstanceName;
    }

    @BeforeMethod(alwaysRun = true)
    public void extractTestNameFromParameters(Method method, Object[] parameters, ITestContext ctx) {
        myTestContext = ctx;
        Class<?> obj = getClass();
        actualTestName = method.getName();
        if ((obj.isAnnotationPresent(UseDifferentTestName.class)) && (parameters.length != 0)) {
            Annotation annotation = obj.getAnnotation(UseDifferentTestName.class);
            UseDifferentTestName testnameInfo = (UseDifferentTestName) annotation;
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < parameters.length; i++) {
                str.append(parameters[0]);
                str.append("_");
            }
            if (parameters.length > 2) {
                testCaseId = actualTestName + "_" + parameters[0].toString() + this.idx++;
            } else
                testCaseId = actualTestName + "_" + str + this.idx++;
            setTestName(testCaseId);
        } else {
            testCaseId = actualTestName;
            setTestName(testCaseId);
            try {
                if (obj.isAnnotationPresent(UseEncodedURL.class)) {
                    urlEncoded = Boolean.valueOf(false);
                } else {
                    urlEncoded = Boolean.valueOf(true);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkToken(String token, String clientId, String clientSecret) {
        String checkTokenUrl = getProperty("check_token_url")[0];
        String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(new StringBuilder().append(clientId).append(":").append(clientSecret).toString().getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", basicDigestHeaderValue);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap();
        formParams.add("token", token);
        HttpEntity<MultiValueMap<String, String>> formEncodedRequest = new HttpEntity(formParams, headers);
        setProxy();
        ResponseEntity<RestAssuredUtil> out = restTemplate.exchange(checkTokenUrl, HttpMethod.POST, formEncodedRequest, RestAssuredUtil.class, new Object[0]);
        HttpStatus status = out.getStatusCode();
        if (status.toString() == "200") {
            return true;
        }

        return false;
    }

    public static String generateAccountApiEndpoint(String gateWayPath, String api) {

        return gateWayPath + "/account/v1/" +  api;
    }
}



