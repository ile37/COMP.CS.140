
package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static fi.tuni.prog3.sisu.ReadJsonToStudent.readDegreeStructure;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Saves and reads Student objects.
 *
 */
public class ReadWriteToJson {
    
    private static JsonArray studentArray = new JsonArray();
    
    /**
     * Saves given ArrayList of Student objects to a given file.
     * if study program is null function expects student to already be saved in 
     * json file.
     * 
     * @param fileName File name where Students are saved
     * @param student Student object to be saved
     * @param program JsonObject of the study branch
     * @return true if saved succesfull.
     */
    public static boolean writeToJson(String fileName, Student student, JsonObject program) 
            throws IOException {       
        
        JsonArray studentObj = new JsonArray();
        JsonObject studentCaller = new JsonObject();
        //checked if allready in the array
            boolean checker = true;
        
        if (program != null ) {
            for (var i : studentArray) {
                if (i.getAsJsonObject().get("tag").getAsJsonArray().get(1)
                        .getAsJsonObject().get("id").toString().replace("\"", "")
                        .equals(student.getId())) {
                    studentCaller = i.getAsJsonObject();
                    checker = false;
                }
            }
            
                                           
            JsonObject nameObj = new JsonObject();
            nameObj.addProperty("name", student.getName());

            JsonObject idObj = new JsonObject();
            idObj.addProperty("id", student.getId());

            studentObj.add(nameObj);
            studentObj.add(idObj);

            JsonObject programObj = new JsonObject();
            idObj.add("program", program);
            studentObj.add(programObj);

            
            
            
            
            if (studentArray != null) {
                for (var i : studentArray) {
                    if (i.getAsJsonObject().get("tag").getAsJsonArray().get(1)
                            .getAsJsonObject().get("id").toString().replace("\"", "")
                            .equals(student.getId())) {
                        i = studentCaller;
                        checker = false;
                    }
                }
            }
            
            
            studentCaller.add("tag", studentObj);
            
            if (checker) {
                
                studentArray.add(studentCaller);
            }
            
                     
        } else {
            if (student.getSelectedCourses() != null) {
                for (var i : studentArray) {
                    if (i.getAsJsonObject().get("tag").getAsJsonArray().get(1)
                            .getAsJsonObject().get("id").toString().replace("\"", "")
                            .equals(student.getId())) {
                        studentObj = i.getAsJsonObject().get("tag").getAsJsonArray();
                        
                        JsonArray coursesArray = new JsonArray();
                        
                        if (i.getAsJsonObject().get("tag").getAsJsonArray().size() > 3) {
                            
                            if (i.getAsJsonObject().get("tag").getAsJsonArray().get(3)
                                    .getAsJsonObject().has("courses")) {
                                coursesArray = i.getAsJsonObject().get("tag")
                                        .getAsJsonArray().get(3).getAsJsonObject()
                                        .get("courses").getAsJsonArray();
                                checker = false;
                            }
                        }
                        
                        JsonObject coursesObj = new JsonObject();   
                        coursesObj.add("courses", coursesArray);                     
                        
                        if (student.getSelectedCourses() != null) {
                            
                            for (var j : student.getSelectedCourses()) {               
                                coursesObj.getAsJsonArray("courses").add(j);
                            }
                        }
                       
                        if (checker) {
                            studentObj.add(coursesObj); 
                        }
                    }
                }
                                            
                for (var i : studentArray) { 
                    if (i.getAsJsonObject().get("tag").getAsJsonArray().get(1)
                            .getAsJsonObject().get("id").toString().replace("\"", "")
                            .equals(student.getId())) {
                        i = studentObj;
                    }
                }
                
            }
        }

        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            gson.toJson(studentArray, writer);
            return true;
            
        } finally {
            return false;
        }
        
        
    }
    
    /**
     * Reads the save file and return an ArrayList of Student objects
     * 
     * @param fileName Name of save file
     * @return ArrayList of Student Objects
     * @throws FileNotFoundException 
     */
    public static ArrayList<Student> readFromJson(String fileName) 
            throws FileNotFoundException {
        
        ArrayList<Student> students = new ArrayList<>();
                
        FileReader jsonReader = new FileReader(new File(fileName));
        Gson gson = new Gson();  
        JsonArray jsonArray = gson.fromJson(jsonReader, JsonArray.class);
        
        if (jsonArray != null) {
            for (var i : jsonArray) {
                studentArray.add(i.getAsJsonObject());
                String name = i.getAsJsonObject().get("tag").getAsJsonArray().get(0)
                        .getAsJsonObject().get("name").toString().replace("\"", "");                   
                String id = i.getAsJsonObject().get("tag").getAsJsonArray().get(1)
                        .getAsJsonObject().get("id").toString().replace("\"", "");
                JsonObject program = i.getAsJsonObject().get("tag").getAsJsonArray()
                        .get(1).getAsJsonObject().get("program").getAsJsonObject();          
                DegreeProgramme dp = readDegreeStructure(program);
                Student student = new Student(name, id , dp);
                
                
                if (i.getAsJsonObject().get("tag").getAsJsonArray().size() > 3) {
                    ArrayList<String> courses = new ArrayList<>();
                    for (var j : i.getAsJsonObject().get("tag").getAsJsonArray()
                            .get(3).getAsJsonObject().get("courses")
                            .getAsJsonArray()) {
                        courses.add(j.toString().replace("\"", ""));
                    }

                    student.setSelectedCourses(courses);
                }
                
                students.add(student);
            }
        }
            
        return students;
    }
}
