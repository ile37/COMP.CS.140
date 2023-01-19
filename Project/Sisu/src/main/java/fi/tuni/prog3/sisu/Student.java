
package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for managing student information, extending the User class.
 */
public class Student extends User {
    
    
    private String name;
    private String id;
    
    // this has the DegreeProgramme object, which contains the hierarchy of
    // the currently selected degree
    private DegreeProgramme dp;
    
    // contains strings of courses chosen by the user
    private ArrayList<String> selectedCourses;
    
    /**
     * Constructor, takes the student's name and id as params
     * @param name The name of the student
     * @param id The identifier of the student
     */
    public Student(String name, String id, DegreeProgramme dp) {
        this.name = name;
        this.id = id;
        this.dp = dp;
    }
    
    /**
     * Returns the name of the student
     * @return String the student's name
     */
    @Override
    public String getName() {
        return name;
    }
    
     /**
      * Returns the id of the student
      * @return String the student's id
      */
    @Override
    public String getId() {
        return id;
    }
    
    /**
     * Returns the DegreeProgramme instance of the student
     * @return DegreeProgramme The dp of the student
     */
    public DegreeProgramme getDP() {
        return dp;
    }
    
    /**
     * Returns the list of selected courses of the student
     * @return ArrayList Arraylist of selected courses
     */
    public ArrayList<String> getSelectedCourses() {
        return this.selectedCourses;
    }
    
    /**
     * Sets an array of strings to be the selected courses of the student
     * @param array ArrayList of strings of selected course names
     */
    public void setSelectedCourses(ArrayList<String> array) {
        this.selectedCourses = array;
    }
}
