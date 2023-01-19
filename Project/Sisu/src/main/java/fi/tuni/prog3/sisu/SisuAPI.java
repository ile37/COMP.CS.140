
package fi.tuni.prog3.sisu;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class used to search degree information from the SISU api.
 */
public class SisuAPI {
     
    private static ArrayList<ArrayList<String>> getIdsFromPage(String id) 
            throws MalformedURLException, IOException {
        ArrayList<ArrayList<String>> ids = new ArrayList<>();
        ArrayList<String> groupIds = new ArrayList<>();
        ArrayList<String> courseIds = new ArrayList<>();
        
        JsonObject data = getPageFromId(id);
                
        if(data != null) {           
            JsonObject ruleModule = (JsonObject) data.get("rule");
            JsonArray rulesModules = new JsonArray();
            
            while (ruleModule.has("rule")) {               
                ruleModule = ruleModule.get("rule").getAsJsonObject();                
            }
            
            rulesModules = ruleModule.getAsJsonArray("rules");
            
            for (var i : rulesModules) {
                if (i.getAsJsonObject().has("rules")) {
                    for (var j : i.getAsJsonObject().get("rules")
                            .getAsJsonArray()) {
                        if (j.getAsJsonObject().get("type").toString()
                                .equals("\"ModuleRule\"")) {
                            groupIds.add(j.getAsJsonObject()
                                    .get("moduleGroupId").toString());
                        } else if (j.getAsJsonObject().get("type").toString()
                                .equals("\"CourseUnitRule\"")) {
                            courseIds.add(j.getAsJsonObject()
                                    .get("courseUnitGroupId").toString());
                        }
                    }
                }
                if (i.getAsJsonObject().get("type").toString()
                        .equals("\"ModuleRule\"")) {
                    groupIds.add(i.getAsJsonObject().get("moduleGroupId")
                            .toString());
                } else if (i.getAsJsonObject().get("type").toString()
                                .equals("\"CourseUnitRule\"")) {
                            courseIds.add(i.getAsJsonObject()
                                    .get("courseUnitGroupId").toString());
                        }
            }         
        }
        
        ids.add(groupIds);
        ids.add(courseIds);
        return ids;       
    }
    
       
    private static HashMap<String, Integer> getCourseInfoFromcourseId 
        (ArrayList<String> courseIds) throws MalformedURLException, IOException {
        
        HashMap<String, Integer> info = new HashMap<>();
        for (var i : courseIds) {
            var url = new URL("https://sis-tuni.funidata.fi/kori/api/"
                    + "course-units/by-group-id?groupId=" + i.replace("\"", "") 
                    + "&universityId=tuni-university-root-id");
            String jsonText = new String(url.openStream().readAllBytes());            
            Gson dataGson = new Gson();
         
            JsonArray data = dataGson.fromJson(jsonText, JsonArray.class);                     
            JsonObject courseName = (JsonObject) data.get(0).getAsJsonObject()
                    .get("name");
            JsonObject courseCredits = (JsonObject) data.get(0).getAsJsonObject()
                    .get("credits");
            if (courseName.get("fi") == null) {
                info.put(courseName.get("en").toString().replace("\"", ""), 
                        Integer.valueOf(courseCredits.get("min").toString()));
            } else {
                info.put(courseName.get("fi").toString().replace("\"", ""), 
                        Integer.valueOf(courseCredits.get("min").toString()));
            }
        }
        return info;
    }
    
    private static JsonObject getPageFromId(String id) 
            throws MalformedURLException, IOException {
        
        if (id.contains("otm-")) {
            var url = new URL("https://sis-tuni.funidata.fi/kori/api/modules/" 
                    + id.replace("\"", ""));
            String JsonText = new String(url.openStream().readAllBytes());
            Gson Gson = new Gson();
            return Gson.fromJson(JsonText, JsonObject.class);  
        } else if (id.contains("tut-") || id.contains("uta")) {
            var url = new URL("https://sis-tuni.funidata.fi/kori/api/modules/"
                    + "by-group-id?groupId=" + id.replace("\"", "") 
                    + "&universityId=tuni-university-root-id");
            String JsonText = new String(url.openStream().readAllBytes());
            Gson Gson = new Gson();
            return Gson.fromJson(JsonText, JsonArray.class).get(0)
                    .getAsJsonObject();            
        } else {
            return null;
        }   
    }
        
    private static String getNameFromPage(String id) throws IOException {
        
        String returnName = "";
        
        JsonObject data = getPageFromId(id);
        
        if(data != null) {        
            JsonObject name = data.get("name").getAsJsonObject();       
            if (name.get("fi") == null) {
                returnName = name.get("en").toString().replace("\"", "");
            }else {
                returnName = name.get("fi").toString().replace("\"", "");
            }
        }                  
        return returnName;
    }
    
    
    private static JsonObject recursiveDataGather(JsonObject json, 
            String prevName, String id) throws IOException {
        
        JsonObject courses = new JsonObject();
        
        ArrayList<ArrayList<String>> ids = getIdsFromPage(id);
        
        for (var info : getCourseInfoFromcourseId(ids.get(1))
                .entrySet()) {
            courses.addProperty(info.getKey(), info.getValue());
        }
                
        json.add(prevName, courses);

        for (var groupId : ids.get(0)) {
            json.add(prevName, recursiveDataGather(json.get(prevName)
                    .getAsJsonObject(), getNameFromPage(groupId), groupId));
        }       
        return json;
        
    }
    
    /**
     * Returns the course hierarchy with the given SisuAPI id as a JsonObject
     * @param id id of the webpage where we want so search info from
     * @return JsonObject which has degree information
     * @throws IOException 
     */
    public static JsonObject getData(String id) throws IOException {
        return recursiveDataGather(new JsonObject(), getNameFromPage(id), id);
    }
}
