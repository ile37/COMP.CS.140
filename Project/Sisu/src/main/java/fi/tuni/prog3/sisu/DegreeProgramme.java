

package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * Class for the information of the student's studies
 */
public class DegreeProgramme extends DegreeModule {
    
    // ArrayList for the StudyGroupings under this degree
    private ArrayList<StudyGrouping> subModules;
    
    /**
     * Constructor for a DegreeProgramme instance
     * @param name String name of degree, or the "main" degree
     * @param minCredits Integer 
     */
    public DegreeProgramme(String name, Integer minCredits) {
        super(name, minCredits);
        subModules = new ArrayList<>();

    }
    
    /**
     * Used to get the sub-StudyGroupings of the DegreeProgramme
     * @return ArrayList a list of the sub-StudyGroupings
     */
    public ArrayList<StudyGrouping> getSubModules() {
        return this.subModules;
    }
    
    
}
