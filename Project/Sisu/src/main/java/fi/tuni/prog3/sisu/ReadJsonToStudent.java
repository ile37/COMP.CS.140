
package fi.tuni.prog3.sisu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static fi.tuni.prog3.sisu.SisuAPI.getData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class to read the JsonObject that's formed from SisuAPI.
 */
public class ReadJsonToStudent {
    private static DegreeProgramme degree;
     
    /**
     * Reads the jsonObject given as a parameter and forms a DegreeProgramme 
     * from it.
     * @param json jsonObject A jsonObject which contains the degree from Sisu API
     * @return DegreeProgramme the DegreeProgramme which images the degree
     */
    public static DegreeProgramme readDegreeStructure(JsonObject json) {
        degree = new DegreeProgramme(null, null);
        json.entrySet().forEach( a -> {
            String tab = "    ";
            
            // the DegreeProgramme. This is the instance of the class to 
            // be returned by this function
            String degreeName = a.getKey();
            //System.out.println(degreeName);
            degree.setName(degreeName);
             
            a.getValue().getAsJsonObject().entrySet().forEach( b -> {
                // These are 1 lower than the DegreeProgramme,
                // for all intents and purposes they're StudyGroupings.            
                
                // A StudyGrouping for each branch of a, each b
                StudyGrouping newGrouping = new StudyGrouping(b.getKey(), null);
                
                /* This is the second level in the hierarchy, 
                and it's guaranteed
                to be either a StudyModule or a GroupingModule by Sisu's terms,
                so a StudyGrouping in the internal system.
                From here the "branches" of the hierarchy are read recursively*/
                newGrouping = recurseStudyGrouping(b.getValue(),
                        newGrouping, b.getKey());

                degree.getSubModules().add(newGrouping);  
            });   
        });
        return degree;
    }
    
    private static StudyGrouping recurseStudyGrouping(JsonElement jsonBranch,
            StudyGrouping curHead, String branchName) {
        // this function uses recursion to go through the current "branch"
        // of the DegreeProgramme json gotten from SisuAPI. 
        // branchName is included so that in the case of jsonBranch
        // being primitive (course)
        // we still have access to the key, which is the course's name.
        // in the case of studygrouping recursion, branchname contains the name
        // of the 'previous' level
        if (jsonBranch.isJsonPrimitive() == false) {
            // if the element jsonBranch is a primitive, it's a Course and so
            // there is no need to do anything - 
            
            Set<Entry<String, JsonElement>> entrySet = jsonBranch.
                    getAsJsonObject().entrySet();
            for(var entry : entrySet) {
                
                if(entry.getValue().isJsonPrimitive() == false) {
                    // Entry value is not an int, so its a StudyGrouping
                    // add as sub StudyGrouping
                    StudyGrouping newSg = new StudyGrouping(entry.getKey(),
                            null);
                    newSg = recurseStudyGrouping(entry.getValue(),
                            newSg, entry.getKey());
                    curHead.getSubModules().add(newSg);
                    
                } else {
                    // Entry value is int, so it's a Course

                    Integer credits = entry.getValue().getAsInt();
                    String name = entry.getKey();
                    Course newC = new Course(name, credits);
                    curHead.getSubCourses().add(newC);
                }
            }         
        }
        return curHead;
    }
}
