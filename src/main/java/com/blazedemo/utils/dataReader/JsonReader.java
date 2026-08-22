package com.blazedemo.utils.dataReader;

import com.blazedemo.utils.logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    String jsonReader;
    String jsonFileName;
    private final String TEST_DATA_PATH = "src/test/resources/test-data/";

    public JsonReader(String jsonFileName){
        this.jsonFileName = jsonFileName;
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(TEST_DATA_PATH + jsonFileName+".json"));
            jsonReader = data.toJSONString();
        }
        catch (Exception e){
          LogsManager.error("Error reading JSON file:", e.getMessage());
          jsonReader = "{}"; // Initialize with an empty JSON object to avoid null issues
        }
    }

    public String getJsonData(String jsonPath){
        try {
            return JsonPath.read(jsonReader, jsonPath);
        }
        catch (Exception e){
            LogsManager.error("Error getting JSON data:", e.getMessage());
            return "";
        }

    }
}
