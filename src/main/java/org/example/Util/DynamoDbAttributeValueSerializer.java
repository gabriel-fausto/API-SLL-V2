package org.example.Util;

import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class DynamoDbAttributeValueSerializer {


    /**
     * Serializes a LastEvaluatedKey map to a Base64 encoded string.
     * @param lastEvaluatedKey The Map<String, AttributeValue> from the DynamoDB Page result.
     * @return A Base64 encoded string, or null if the key is null/empty.
     */
    public static String encodeLastEvaluatedKey(Map<String, AttributeValue> lastEvaluatedKey) {
        if (lastEvaluatedKey == null || lastEvaluatedKey.isEmpty()) {
            return null;
        }

        // Convert the Map<String, AttributeValue> to an EnhancedDocument for simple JSON serialization
        EnhancedDocument document = EnhancedDocument.fromAttributeValueMap(lastEvaluatedKey);
        String json = document.toJson();

        // Base64 encode the JSON string
        return Base64.getUrlEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes a Base64 string back into a Map<String, AttributeValue> for the next request.
     * @param encodedKey The Base64 encoded key string.
     * @return The Map<String, AttributeValue> for the ExclusiveStartKey parameter.
     */
    public static Map<String, AttributeValue> decodeToExclusiveStartKey(String encodedKey) {
        if (encodedKey == null || encodedKey.isEmpty()) {
            return null;
        }

        // Base64 decode the string to a JSON string
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedKey);
        String json = new String(decodedBytes, StandardCharsets.UTF_8);

        // Convert the JSON string back to a Map<String, AttributeValue>
        EnhancedDocument document = EnhancedDocument.fromJson(json);
        return document.toMap();
    }
}