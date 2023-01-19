
package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for the types of GroupingModule and StudyModule in the SISU 
 * hierarchy.
 */
public class StudyGrouping extends DegreeModule {
    
    // With StudyGrouping, minCredits can be null, since while a StudyModule has
    // info about minCredits, GroupingModule does not.
    
    // ArrayLists for subCourses, subModules.
    // Courses to subCourses, StudyGroupings to subModules
    private ArrayList<StudyGrouping> subModules;
    private ArrayList<Course> subCourses;
    
    /**
     * Constructor for StudyGrouping instances
     * @param name String name of the grouping module
     * @param minCredits Integer the minimum credits value from Sisu API
     */
    public StudyGrouping (String name, Integer minCredits) {
        super(name, minCredits);
        subModules = new ArrayList<>();
        subCourses = new ArrayList<>();
    }
    
    /**
     * Used to get the sub-StudyGroupings of the instance
     * @return ArrayList a list of StudyGroupings
     */
    public ArrayList<StudyGrouping> getSubModules() {
        return this.subModules;
    }
    
    /**
     * Used to get the sub-Courses of the instance
     * @return ArrayList a list of Courses
     */
    public ArrayList<Course> getSubCourses() {
        return this.subCourses;
    }
}
