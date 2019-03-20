package com.bec.api.automation.jsonassert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;
import org.skyscreamer.jsonassert.comparator.JSONCompareUtil;

import java.util.Map;
import java.util.Set;

public class JsonComparator extends DefaultComparator {

    private static final String IGNORE = "ignore";
    private static Log logger = LogFactory.getLog(JsonComparator.class);

    public JsonComparator(JSONCompareMode mode) {

        super(mode);

    }

    protected void checkJsonObjectKeysExpectedInActual
            (String prefix, JSONObject expected, JSONObject actual, JSONCompareResult result)
            throws JSONException {

        Set<String> expectedKeys = JSONCompareUtil.getKeys(expected);

        for (String key : expectedKeys) {

            logger.debug("key=" + key);

            Object expectedValue = expected.get(key);

            if (actual.has(key)) {

                Object actualValue = actual.get(key);

                logger.debug("actual value=" + actualValue);

                logger.debug("expected value=" + expectedValue);

                if ((expectedValue instanceof String)) {

                    if (!expectedValue.equals("ignore")) {
                    }

                } else {

                    compareValues(JSONCompareUtil.qualify(prefix, key), expectedValue, actualValue, result);

                }

            } else {

                result.missing(prefix, key);

            }

        }

    }


    protected void compareJSONArrayWithStrictOrder(String key, JSONArray expected, JSONArray actual, JSONCompareResult result)
            throws JSONException {

        for (int i = 0; i < expected.length(); i++) {

            Object expectedValue = expected.get(i);

            Object actualValue = actual.get(i);

            if ((expectedValue instanceof String)) {

                if (!expectedValue.equals("ignore")) {
                }


            } else {

                compareValues(key + "[" + i + "]", expectedValue, actualValue, result);

            }

        }

    }


    protected void compareJSONArrayOfSimpleValues(String key, JSONArray expected, JSONArray actual, JSONCompareResult result)
            throws JSONException {

        Map<Object, Integer> expectedCount = JSONCompareUtil.getCardinalityMap(JSONCompareUtil.jsonArrayToList(expected));

        Map<Object, Integer> actualCount = JSONCompareUtil.getCardinalityMap(JSONCompareUtil.jsonArrayToList(actual));

        for (Object o : expectedCount.keySet()) {

            if (!o.equals("ignore")) {


                if (!actualCount.containsKey(o)) {

                    result.missing(key + "[]", o);

                } else if (!((Integer) actualCount.get(o)).equals(expectedCount.get(o))) {

                    result.fail(key + "[]: Expected " + expectedCount.get(o) + " occurrence(s) of " + o + " but got "
                            + actualCount.get(o) + " occurrence(s)");

                }
            }

        }

        for (Object o : actualCount.keySet()) {

            if (!expectedCount.containsKey("ignore")) {


                if (!expectedCount.containsKey(o)) {

                    result.unexpected(key + "[]", o);

                }

            }

        }

    }

}


