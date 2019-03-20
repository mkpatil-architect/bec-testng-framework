package com.bec.api.automation.core;

import com.bec.api.automation.jsonassert.JsonComparator;
import com.bec.api.automation.utils.FileUtil;
import com.bec.api.automation.utils.RestAssuredUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by mkpatil on 10/11/17.
 */

public class CELECTTestNGFramework {
    static RestAssuredUtil rst = new RestAssuredUtil();
    static String assertionName = null;
    private static Log logger = LogFactory.getLog(CELECTTestNGFramework.class);
    private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap();

    public static boolean isTrue(String ErrorMessage, String expectedResult, String actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, getErrorMsg(ErrorMessage));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
            return false;
        }
        return true;
    }

    public static void isEqual(String ErrorMessage, String expectedResult, String actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, getErrorMsg(ErrorMessage));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }

    public static void isEqual(String ErrorMessage, boolean expectedResult, boolean actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, getErrorMsg(ErrorMessage));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }

    public static void isEqual(String ErrorMessage, int expectedResult, int actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, getErrorMsg(ErrorMessage));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }


    public static void isEqual(String ErrorMessage, String expectedResult, int actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            if (expectedResult.contains("xx")) {
                String newExpectedResult = expectedResult.substring(0, 1);
                String newActualResult = String.valueOf(actualResult).substring(0, 1);
                Assert.assertEquals(newActualResult + "xx", newExpectedResult + "xx", getErrorMsg(ErrorMessage));
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            } else {
                Assert.assertEquals(Integer.valueOf(actualResult), expectedResult, getErrorMsg(ErrorMessage));
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            }
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }


    public static void isEqual(String ErrorMessage, Number expectedResult, Number actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            Assert.assertEquals(actualResult, expectedResult, getErrorMsg(ErrorMessage));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }


    public static void isTrue(String ErrorMessage, boolean actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            Assert.assertTrue(actualResult, getErrorMsg(ErrorMessage));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }


    public static boolean isExist(String ErrorMessage, String actualResult) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualResult);
            Assert.assertNotNull(actualResult);
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
            return false;
        }
        return true;
    }


    public static void isContains(String ErrorMessage, String subString, String actualString) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualString);
            if (!actualString.contains(subString)) {
                throw new AssertionError(getErrorMsg(ErrorMessage));
            }
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }


    public static void isContains(String ErrorMessage, String subString, List<Object> actualString) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            for (Object actual : actualString) {
                logger.info("ActualValue: " + actual);
                if (!((String) actual).contains(subString)) {
                    throw new AssertionError(getErrorMsg(ErrorMessage));
                }
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            }
        } catch (AssertionError e) {
            addVerificationFailure(e);
        }
    }


    public static void fail(String failureMessage) {
        Assert.fail(getErrorMsg(failureMessage));
    }


    protected static int getNoOfTestMethods(String className)
            throws ClassNotFoundException {
        Method[] methods = Class.forName(className).getMethods();
        int testCaseCount = 0;
        for (int i = 0; i < methods.length; i++) {
            if ((methods[i].getAnnotations().length == 1) &&
                    (methods[i].getAnnotations()[0].annotationType().toString().contains("org.testng.annotations.Test"))) {
                testCaseCount++;
            }
        }
        return testCaseCount;
    }


    public static List<Throwable> getVerificationFailures() {
        List<Throwable> verificationFailures = (List) verificationFailuresMap.get(Reporter.getCurrentTestResult());
        return verificationFailures == null ? new ArrayList() : verificationFailures;
    }


    protected static void addVerificationFailure(Throwable verificationError) {
        List<Throwable> verificationFailures = getVerificationFailures();

        verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
        verificationFailures.add(verificationError);
    }


    public static void isJsonEqual(String expectedResult, String jsonResponse, boolean strict, List<String> jsonPath) {
        try {
            assertionName = getAssertionname(jsonResponse);
            logger.info("Json response: " + jsonResponse);
            if (jsonPath != null) {
                String realExpectedResult = FileUtil.setIgnoredFields(expectedResult, jsonPath);
                String filteredJsonResponse = FileUtil.setIgnoredFields(jsonResponse, jsonPath);
                logger.info("filtered json response: " + filteredJsonResponse);
                JSONAssert.assertEquals(realExpectedResult, filteredJsonResponse, strict);
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            } else {
                JSONAssert.assertEquals(expectedResult, jsonResponse, strict);
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            }
        } catch (Exception e) {
            addVerificationFailure(e);
        }
    }


    public static void isJsonEqual(File expectedResultFile, String jsonResponse, boolean strict, List<String> jsonPath) {
        try {
            assertionName = getAssertionname(jsonResponse);
            logger.info("Json response: " + jsonResponse);
            String realExpectedResult = FileUtil.setIgnoredFields(expectedResultFile, jsonPath);
            if (jsonPath != null) {
                String filteredJsonResponse = FileUtil.setIgnoredFields(jsonResponse, jsonPath);
                logger.info("filtered json response: " + filteredJsonResponse);
                JSONAssert.assertEquals(realExpectedResult, filteredJsonResponse, strict);
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            } else {
                JSONAssert.assertEquals(realExpectedResult, jsonResponse, strict);
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            }
        } catch (Exception e) {
            addVerificationFailure(e);
        }
    }


    public static void isJsonEqual(File expectedResultFile, String jsonResponse, boolean strict) {
        try {
            assertionName = getAssertionname(jsonResponse);
            String fileInput = FileUtil.loadFileData(expectedResultFile);
            logger.info("Json response: " + jsonResponse);
            if (strict) {

                JSONAssert.assertEquals(fileInput, jsonResponse, new JsonComparator(JSONCompareMode.STRICT));
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);

            } else {
                JSONAssert.assertEquals(fileInput, jsonResponse, new JsonComparator(JSONCompareMode.LENIENT));
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            }
        } catch (Exception e) {
            addVerificationFailure(e);
        }
    }

    public static boolean matchEquals(String msg, int expected, int actual) {
        try {
            assertionName = getAssertionname(msg);
            logger.info("ActualValue: " + actual);
            Assert.assertEquals(actual, expected, getErrorMsg(msg));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
            return false;
        }
        return true;
    }

    public static boolean matchContains(String ErrorMessage, String subString, String actualString) {
        String assertionName = null;
        try {
            assertionName = getAssertionname(ErrorMessage);
            logger.info("ActualValue: " + actualString);
            Assert.assertTrue(actualString.contains(subString), getErrorMsg(ErrorMessage));
            RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
        } catch (AssertionError e) {
            addVerificationFailure(e);
            return false;
        }
        return true;
    }

    public static boolean matchContains(String ErrorMessage, String subString, List<Object> actualString) {
        try {
            assertionName = getAssertionname(ErrorMessage);
            for (Object actual : actualString) {
                logger.info("ActualValue: " + actual);
                Assert.assertTrue(actualString.contains(subString), getErrorMsg(ErrorMessage));
                RestAssuredUtil.myTestContext.setAttribute(assertionName + "-" + RestAssuredUtil.testCaseId, assertionName);
            }
        } catch (AssertionError e) {
            addVerificationFailure(e);
            return false;
        }
        return true;
    }

    protected static String getAssertionname(String assertion) {
        int index = assertion.indexOf(":");
        String assertName;
        if (index == -1) {
            assertName = assertion;
        } else {
            assertName = assertion.substring(0, index);
        }
        return assertName;
    }

    protected static String getErrorMsg(String msg) {
        int index = msg.indexOf(":");
        String assertName;
        if (index == -1) {
            assertName = msg;
        } else {
            assertName = msg.substring(index + 1, msg.length());
        }
        return assertName;
    }
}
