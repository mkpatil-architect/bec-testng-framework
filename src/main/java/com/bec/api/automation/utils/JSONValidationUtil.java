package com.bec.api.automation.utils;

import com.bec.api.automation.core.CELECTTestNGFramework;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

public class JSONValidationUtil
        extends FileUtil {
    private static Log LOGGER = LogFactory.getLog(JSONValidationUtil.class);

    public static JsonNode getJsonNode(String jsonText) throws IOException {
        return JsonLoader.fromString(jsonText);
    }

    public static JsonNode getJsonNode(File jsonFile) throws IOException {
        return JsonLoader.fromFile(jsonFile);
    }

    public static JsonNode getJsonNode(URL url) throws IOException {
        return JsonLoader.fromURL(url);
    }

    public static JsonNode getJsonNodeFromResource(String resource) throws IOException {
        return JsonLoader.fromResource(resource);
    }

    public static JsonSchema getSchemaNode(String schemaText) throws IOException, ProcessingException {
        JsonNode schemaNode = getJsonNode(schemaText);
        return _getSchemaNode(schemaNode);
    }

    public static JsonSchema getSchemaNode(File schemaFile) throws IOException, ProcessingException {
        JsonNode schemaNode = getJsonNode(schemaFile);
        return _getSchemaNode(schemaNode);
    }

    public static JsonSchema getSchemaNode(URL schemaFile) throws IOException, ProcessingException {
        JsonNode schemaNode = getJsonNode(schemaFile);
        return _getSchemaNode(schemaNode);
    }

    public static JsonSchema getSchemaNodeFromResource(String resource) throws IOException, ProcessingException {
        JsonNode schemaNode = getJsonNodeFromResource(resource);
        return _getSchemaNode(schemaNode);
    }

    public static void validateJson(JsonSchema jsonSchemaNode, JsonNode jsonNode) throws ProcessingException {
        ProcessingReport report = jsonSchemaNode.validate(jsonNode);
        if (!report.isSuccess()) {
            Iterator localIterator = report.iterator();
            if (localIterator.hasNext()) {
                ProcessingMessage processingMessage = (ProcessingMessage) localIterator.next();
                throw new ProcessingException(processingMessage);
            }
        }
    }

    public static boolean isJsonValid(JsonSchema jsonSchemaNode, JsonNode jsonNode) throws ProcessingException {
        ProcessingReport report = jsonSchemaNode.validate(jsonNode);
        if (!report.isSuccess()) {
            LOGGER.info("\nJSON validation falied --> " + report.toString() + "\n");
            Exception e = new Exception(report.toString());
            CELECTTestNGFramework.addVerificationFailure(e);
        } else {
            LOGGER.info("\nJSON validation succeed --> " + report.toString() + "\n");
        }
        return report.isSuccess();
    }

    public static boolean isJsonValid(String schemaText, String jsonText) throws ProcessingException, IOException {
        JsonSchema schemaNode = getSchemaNode(schemaText);
        JsonNode jsonNode = getJsonNode(jsonText);
        return isJsonValid(schemaNode, jsonNode);
    }

    public static boolean isJsonValid(File schemaFile, File jsonFile) throws ProcessingException, IOException {
        JsonSchema schemaNode = getSchemaNode(schemaFile);
        JsonNode jsonNode = getJsonNode(jsonFile);
        return isJsonValid(schemaNode, jsonNode);
    }

    public static boolean isJsonValid(File schemaFile, String jsonText) throws ProcessingException, IOException {
        JsonSchema schemaNode = getSchemaNode(schemaFile);
        JsonNode jsonNode = getJsonNode(jsonText);
        return isJsonValid(schemaNode, jsonNode);
    }

    public static boolean isJsonValid(URL schemaURL, URL jsonURL) throws ProcessingException, IOException {
        JsonSchema schemaNode = getSchemaNode(schemaURL);
        JsonNode jsonNode = getJsonNode(jsonURL);
        return isJsonValid(schemaNode, jsonNode);
    }

    public static void validateJson(String schemaText, String jsonText) throws IOException, ProcessingException {
        JsonSchema schemaNode = getSchemaNode(schemaText);
        JsonNode jsonNode = getJsonNode(jsonText);
        validateJson(schemaNode, jsonNode);
    }

    public static void validateJson(File schemaFile, File jsonFile) throws IOException, ProcessingException {
        JsonSchema schemaNode = getSchemaNode(schemaFile);
        JsonNode jsonNode = getJsonNode(jsonFile);
        validateJson(schemaNode, jsonNode);
    }

    public static void validateJson(URL schemaDocument, URL jsonDocument) throws IOException, ProcessingException {
        JsonSchema schemaNode = getSchemaNode(schemaDocument);
        JsonNode jsonNode = getJsonNode(jsonDocument);
        validateJson(schemaNode, jsonNode);
    }

    public static void validateJsonResource(String schemaResource, String jsonResource) throws IOException, ProcessingException {
        JsonSchema schemaNode = getSchemaNode(schemaResource);
        JsonNode jsonNode = getJsonNodeFromResource(jsonResource);
        validateJson(schemaNode, jsonNode);
    }

    private static JsonSchema _getSchemaNode(JsonNode jsonNode)
            throws ProcessingException {
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        return factory.getJsonSchema(jsonNode);
    }
}
