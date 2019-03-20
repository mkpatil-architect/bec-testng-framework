package com.bec.api.automation.utils;

import com.bec.api.automation.core.CELECTTestNGFramework;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.*;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ITestContext;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/**
 * Created by mkpatil on 20/10/17.
 */

@SuppressWarnings("Duplicates")
public class FileUtil
        extends CELECTTestNGFramework {
    private static final String TEST_PREFIX = "testProperty";
    private static final String SYS_PREFIX = "sys";
    private static final String VAR_PREFIX = "var";
    private static final String DPVAR_PREFIX = "dpvar";
    private static Log LOGGER = LogFactory.getLog(FileUtil.class);
    private static Map<String, String> elementIDMap = null;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static StringBuilder contents;

    public static File getFile(String filename) throws URISyntaxException {
        File file = new File(filename);
        if (!file.exists()) {
            file = new File(FileUtil.class.getClassLoader().getResource(filename).toURI());
        }
        return (file != null) && (file.exists()) ? file : null;
    }

    public static String loadIDsFromFile(String locatorID) {
        System.setProperty("webdriver.objectrepository", "src/main/resources/objectRepository/pagelocators.data");
        String fileData = loadFileData(new File(System.getProperty("webdriver.objectrepository")));
        elementIDMap = new LinkedHashMap();
        for (String pageSplitterKeyValue : fileData.split(" *; *")) {
            String[] locatorsSplitter = pageSplitterKeyValue.split("~");
            for (int i = 0; i < locatorsSplitter.length; i++) {
                String[] locatorKeyValue = locatorsSplitter[i].split(" *\\|\\| *", 2);

                elementIDMap.put(locatorKeyValue[0].trim(), locatorKeyValue.length == 1 ? "" : locatorKeyValue[1].trim());
            }
        }
        return (String) elementIDMap.get(locatorID);
    }

    public static String loadFileData(File file) {
        contents = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;


            while ((text = reader.readLine()) != null) {
                if ((!text.startsWith("//")) && (!text.isEmpty())) {
                    contents.append(text);
                }
            }
            return contents.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String[] getProperty(String propertyName) {
        String propertyValue = null;
        propertyValue = PropertiesCache.getInstance().getProperty(propertyName);
        try {
            if (propertyValue == null) {
                LOGGER.info("The property '" + propertyName + "' doesnt exist. \nPlease note that property name is case sensitive and should exist in the configuration.properties file.");

                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
        return propertyValue.split(",");
    }


    public static String getDBProperty(String propertyName) {
        String propertyValue = null;
        try {
            Properties properties = new Properties();
            String envDetail = envDetail();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(envDetail + ".dbQueries.properties"));
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (propertyValue == null) {
            LOGGER.info("The property '" + propertyName + "'doesnt exist. Please note that property name is case sensitive.");
            return null;
        }
        return propertyValue;
    }


    public static String generateCSVFile(String templateFilePath, String fileNamePrefix)
            throws IOException, URISyntaxException {
        Map<String, Object> configPropMap = new HashMap();
        Map<String, Object> valuesMap = new HashMap();
        Map<String, Object> vars = new HashMap();

        File file = new File(templateFilePath);
        if (!file.exists()) {
            URL url = FileUtil.class.getClassLoader().getResource(templateFilePath);
            if (url == null) {
                LOGGER.error("Cannot find the file " + templateFilePath);
                return null;
            }
            file = new File(url.toURI());
        }

        List<String> lines = FileUtils.readLines(file);

        configPropMap = configPropertyLookup(lines, vars);
        valuesMap.putAll(configPropMap);

        String value = FileUtils.readFileToString(file);
        LOGGER.info("Value of payload files " + value);
        StrSubstitutor varStr = new StrSubstitutor(valuesMap);
        String content = varStr.replace(value);
        LOGGER.info("Content of payload files " + content);
        File tempFile = File.createTempFile(fileNamePrefix + getCurrentTimestamp(), ".csv");
        FileUtils.writeStringToFile(tempFile, content);
        return tempFile.getAbsolutePath();
    }


    public static String generateFile(String templateFilePath, String fileNamePrefix)
            throws IOException, URISyntaxException {
        Map<String, Object> vars = new HashMap();
        String newJSONFile = generateFile(templateFilePath, fileNamePrefix, vars);
        return newJSONFile;
    }


    public static String generateFile(String templateFilePath, String fileNamePrefix, Map<String, Object> vars)
            throws IOException, URISyntaxException {
        Map<String, Object> configPropMap = new HashMap();
        Map<String, Object> valuesMap = new HashMap();

        File file = new File(templateFilePath);
        if (!file.exists()) {
            URL url = FileUtil.class.getClassLoader().getResource(templateFilePath);
            if (url == null) {
                LOGGER.error("Cannot find the file " + templateFilePath);
                return null;
            }
            file = new File(url.toURI());
        }

        List<String> lines = FileUtils.readLines(file);


        configPropMap = configPropertyLookup(lines, vars);
        valuesMap.putAll(configPropMap);
        valuesMap.putAll(vars);

        String value = FileUtils.readFileToString(file);
        StrSubstitutor varStr = new StrSubstitutor(valuesMap);
        String content = varStr.replace(value);
        LOGGER.info("Content of payload files " + content);
        String paylaodExtension = FilenameUtils.getExtension(templateFilePath);
        File newJSONFile;
        if (OS.contains("windows")) {
            File tempFileWin = File.createTempFile(fileNamePrefix + getCurrentTimestamp(), null);
            FileUtils.writeStringToFile(tempFileWin, content);
            String tempfilePathName = tempFileWin.getAbsolutePath();

            String newName = tempfilePathName.replace("tmp", paylaodExtension);
            newJSONFile = new File(newName);
        } else {
            File tempFile = File.createTempFile(fileNamePrefix + getCurrentTimestamp(), null);
            FileUtils.writeStringToFile(tempFile, content);
            String tempfilePathName = tempFile.getAbsolutePath();

            String newName = tempfilePathName.replace("tmp", paylaodExtension);
            newJSONFile = new File("/tmp", newName);
        }
        FileUtils.writeStringToFile(newJSONFile, content);
        return newJSONFile.getAbsolutePath();
    }

    public static boolean isEncoded(Map<String, Object> input) {
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            Object value = entry.getValue();
            char[] charArray = ((String) value).toCharArray();
            int i = 0;
            for (int charArrayLength = charArray.length; i < charArrayLength; i++) {
                Character c = Character.valueOf(charArray[i]);
                if (Character.getType(c.charValue()) == 24) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isEncoded(String input) {
        String value = "";
        try {
            value = URLDecoder.decode(input, StandardCharsets.UTF_8.name());
            if (value.equals(input)) {
                return true;
            }
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static Map<String, Object> configPropertyLookup(List<String> values, Map<String, Object> vars) {
        Map<String, Object> testPropertyValuesMap = new HashMap();
        Object obj = Thread.currentThread().getStackTrace()[4].getClassName();
        for (String line : values) {
            String regex = "(\\$\\{[\\w]+:)+[\\w]+\\}";
            Matcher matcher = Pattern.compile(regex).matcher(line);

            while (matcher.find()) {
                String result = matcher.group();
                String[] parts = result.split(":");
                String partsValue = parts[0].substring(2, parts[0].length());
                String name = parts[1].substring(0, parts[1].length() - 1);
                switch (partsValue) {
                    case "testProperty":

                        testPropertyValuesMap.put("testProperty:" + name, getProperty(name)[0]);
                        break;

                    case "sys":

                        testPropertyValuesMap.put("sys:" + name, RestAssuredUtil.getSysProperty(name));
                        break;

                    case "var":

                        testPropertyValuesMap.put("var:" + name, RestAssuredUtil.getSysProperty(name));


                        break;
                    case "dpvar":

                        testPropertyValuesMap.put("dpvar:" + name, vars.get(name));
                }

            }
        }
        return testPropertyValuesMap;
    }


    public static long getCurrentTimestamp() {
/* 350 */
        return new Date().getTime();
    }


    public static String getRandomUUID() {

        return UUID.randomUUID().toString();
    }

    public static void setDefaultSysProperties() {
        RestAssuredUtil.setSysProperty("currentTimeStamp", String.valueOf(getCurrentTimestamp()));
    }

    public static void getTestProperties(String propertyName) {
        RestAssuredUtil.setSysProperty("currentTimeStamp", String.valueOf(getCurrentTimestamp()));
    }


    public static String setIgnoredFields(Object obj, List<String> jsonPathExpression)
            throws IOException {
        DocumentContext docCtx = null;
        if ((obj instanceof File)) {
            docCtx = JsonPath.parse((File) obj, Configuration.defaultConfiguration());
        } else if ((obj instanceof String)) {
            docCtx = JsonPath.parse((String) obj, Configuration.defaultConfiguration());
        }
        if (jsonPathExpression != null) {
            for (int i = 0; i < jsonPathExpression.size(); i++) {
                docCtx.set((String) jsonPathExpression.get(i), "ignore", new Predicate[0]);
            }
        }
        return docCtx.jsonString();
    }

    static JsonNode readJsonData(JsonNode jsonNode) {
        JsonNode root = null;
        Iterator<Map.Entry<String, JsonNode>> ite = jsonNode.fields();
        while (ite.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry) ite.next();
            if (((JsonNode) entry.getValue()).isObject()) {
                root = readJsonData((JsonNode) entry.getValue());
            } else {
                System.out.println("key:" + (String) entry.getKey() + ", value:" + entry.getValue());
            }
        }
        return root;
    }

    public static String envDetail() {
        String envType = RestAssuredUtil.getSysProperty("envType");
        if (envType == null) {
            return "qa";
        }
        return envType.toLowerCase();
    }

    public static String[] envDetailList() {
        String[] defEnvType = {"qa"};

        String envType = RestAssuredUtil.getSysProperty("envType");

        if (envType == null)
            return defEnvType;
        String[] envList = envType.split(",");
        return envList;
    }

    public static String[] baseEnvDetail() {
        String[] baseList = null;
        String[] defBaseType = {"baseline"};
        String envBaseType = RestAssuredUtil.getSysProperty("baseEnvType");

        if (envBaseType == null) {
            return defBaseType;
        }
        baseList = envBaseType.split(",");


        return baseList;
    }


    public static boolean propertyFileDetail(ITestContext context) {
        String ignorePropertyFile = context.getCurrentXmlTest().getParameter("ignorePropertyFile");
        if (ignorePropertyFile == null) {
            return false;
        }
        return true;
    }

    public static File genrateFileFromString(String payloadString) throws IOException {
        JsonParser parser = new JsonParser();

        String firstChar = String.valueOf(payloadString.charAt(0));

        String prettyJson;
        if (firstChar.equalsIgnoreCase("[")) {
            JsonArray json = parser.parse(payloadString).getAsJsonArray();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            prettyJson = gson.toJson(json);
        } else {
            JsonObject json = parser.parse(payloadString).getAsJsonObject();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            prettyJson = gson.toJson(json);
        }
        File tempFile = File.createTempFile("MTGeneratedFile" + getCurrentTimestamp(), null);

        FileUtils.writeStringToFile(tempFile, prettyJson);
        String tempfilePathName = tempFile.getAbsolutePath();
        String newName = tempfilePathName.replace("tmp", "json");
        File newJSONFile;
        if (OS.contains("windows")) {
            newJSONFile = new File(newName);
        } else
            newJSONFile = new File("/tmp", newName);
        FileUtils.writeStringToFile(newJSONFile, prettyJson);
        return newJSONFile;
    }
}

