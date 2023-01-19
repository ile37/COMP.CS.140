/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3.junitattainment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ilari
 */
public class AttainmentTest {
    
    public AttainmentTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }


    @Test
    public void testGetCourseCode() {
        Attainment instance = new Attainment("AUT.120", "13244324324", 4);
        String expResult = "AUT.120";
        String result = instance.getCourseCode();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetStudentNumber() {
        Attainment instance = new Attainment("AUT.120", "13244324324", 4);
        String expResult = "13244324324";
        String result = instance.getStudentNumber();
        assertEquals(expResult, result);

    }

    @Test
    public void testGetGrade() {
        Attainment instance = new Attainment("AUT.120", "13244324324", 4);
        int expResult = 4;
        int result = instance.getGrade();
        assertEquals(expResult, result);
    }
    
    @Test
    public void IllegalArgumentException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Attainment(null, "4352543", 3);
        });
    }
    
    @Test
    public void testToString() {
        Attainment instance = new Attainment("AUT.120", "13244324324", 4);
        String expResult = "AUT.120 13244324324 4";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCompareTo() {
        Attainment instance1 = new Attainment("AUT.120", "13244324324", 4);
        Attainment instance2 = new Attainment("BUT.120", "13244324324", 4);
        int result = instance1.compareTo(instance2);
        assertTrue(result < 0);
        
    }
}
