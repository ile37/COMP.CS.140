
package fi.tuni.prog3.round7.jsoncountries;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;




public class CountryData {
    
    public static List<Country> readFromJsons(String areaFile, String populationFile, String gdpFile) throws IOException {
        ArrayList<Country> countries = new ArrayList<>();
        
        FileReader areaJsonReader = new FileReader(new File(areaFile));
        FileReader popJsonReader = new FileReader(new File(populationFile));
        FileReader gdpJsonReader = new FileReader(new File(gdpFile));
        
                Gson AreaGson = new Gson();              
                JsonObject aObject = AreaGson.fromJson(areaJsonReader, JsonObject.class);
                JsonObject aRootObject = (JsonObject) aObject.get("Root");
                JsonObject aDataObject = (JsonObject) aRootObject.get("data");
                JsonArray aRecordArray =  aDataObject.getAsJsonArray("record");
                
                Gson pGson = new Gson();              
                JsonObject jsonObject = pGson.fromJson(popJsonReader, JsonObject.class);
                JsonObject pRootObject = (JsonObject) jsonObject.get("Root");
                JsonObject pDataObject = (JsonObject) pRootObject.get("data");
                JsonArray pRecordArray =  pDataObject.getAsJsonArray("record");
                
                Gson gdpGson = new Gson();              
                JsonObject gdpJsonObject = gdpGson.fromJson(gdpJsonReader, JsonObject.class);
                JsonObject gdpRootObject = (JsonObject) gdpJsonObject.get("Root");
                JsonObject gdpDataObject = (JsonObject) gdpRootObject.get("data");
                JsonArray gdpRecordArray =  gdpDataObject.getAsJsonArray("record");

                for (int i = 0; i < aRecordArray.size(); i++ ) {
                    
                    String name = aRecordArray.get(i).getAsJsonObject().getAsJsonArray("field").get(0).getAsJsonObject().get("value").toString();
                    name = name.substring(1, name.length()-1);

                    String areaStr = aRecordArray.get(i).getAsJsonObject().getAsJsonArray("field").get(2).getAsJsonObject().get("value").toString();
                    double area = Double.parseDouble(areaStr.substring(1, areaStr.length()-1));
                    
                    String populationStr = pRecordArray.get(i).getAsJsonObject().getAsJsonArray("field").get(2).getAsJsonObject().get("value").toString();
                    long population = Long.parseLong(populationStr.substring(1, populationStr.length()-1));
                    
                    String gdpStr = gdpRecordArray.get(i).getAsJsonObject().getAsJsonArray("field").get(2).getAsJsonObject().get("value").toString();
                    double gdp = Double.parseDouble(gdpStr.substring(1, gdpStr.length()-1));
                    
                    countries.add(new Country(name, area, population, gdp));
                    
                }

        return countries;
    }
    
    public static void writeToJson(List<Country> countries, String countryFile) throws IOException {
        
        try (Writer writer = new FileWriter(countryFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            JsonArray objectArray = new JsonArray();

            for (int i = 0; i < countries.size(); i++) {
                
                JsonObject obj = new JsonObject();
                obj.addProperty("name", countries.get(i).getName().toString());
                obj.addProperty("area", countries.get(i).getArea());
                obj.addProperty("population", countries.get(i).getPopulation());
                obj.addProperty("gdp", countries.get(i).getGdp());
                
                objectArray.add(obj);
            }

            gson.toJson(objectArray, writer);
        }
        
    }

    
}
