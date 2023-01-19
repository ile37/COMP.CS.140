/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 *An abstract class for storing information on Modules and Courses.
 */
public abstract class DegreeModule {
    private String name;
    private Integer minCredits;

    
    
    /**
     * A constructor for initializing the member variables.
     * @param name name of the Module or Course.
     * @param minCredits minimum credits of the Module or Course.
     */
    public DegreeModule(String name,Integer minCredits) {
        
        this.name = name;
        this.minCredits = minCredits;

    }
    
    /**
     * Returns the name of the Module or Course.
     * @return name of the Module or Course.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the minimum credits of the Module or Course.
     * @return minimum credits of the Module or Course.
     */
    public Integer getMinCredits() {
        return this.minCredits;
    }

    /**
     * Sets the name for the module.
     * @param name String the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

}
