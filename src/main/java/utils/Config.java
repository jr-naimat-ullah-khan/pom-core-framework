package utils;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Config {

    private static JSONObject configData;

    static {
        try {
            // Load the JSON file from resources
            InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.json");

            // If the inputStream is null, that means the file was not found
            if (inputStream == null) {
                throw new RuntimeException("Unable to find config.json in resources folder.");
            }

            // Read the JSON file into a string
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String jsonContent = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();

            // Parse the string into a JSONObject
            configData = new JSONObject(jsonContent);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load config.json file");
        }
    }

    // Generic method to fetch any configuration value dynamically
    public static <T> T getConfigValue(String key, Class<T> type) {
        String[] keys = key.split("\\.");
        JSONObject currentObject = configData;

        for (String k : keys) {
            if (currentObject.has(k)) {
                Object value = currentObject.get(k);
                if (value == null) {
                    System.out.println("Null value for key: " + k);
                    return null;
                }

                if (type == String.class && value instanceof String) {
                    return type.cast(value);
                } else if (type == Boolean.class && value instanceof Boolean) {
                    return type.cast(value);
                } else if (type == Integer.class && value instanceof Integer) {
                    return type.cast(value);
                } else if (value instanceof JSONObject) {
                    currentObject = (JSONObject) value;  // Handle nested objects
                } else {
                    System.out.println("Unexpected value type for key " + k + ": " + value.getClass().getName());
                }
            } else {
                System.out.println("Key not found: " + key);
            }
        }
        return null;
    }


}