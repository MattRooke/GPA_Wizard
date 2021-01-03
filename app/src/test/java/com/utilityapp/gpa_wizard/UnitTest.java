package com.utilityapp.gpa_wizard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 Local unit tests
 */
public class UnitTest {
    private Calculator calculator = new Calculator();

    @Test
    public void testGPA() {
        // Test gpa calculation.
        double totalGrades = 1 + 3 + 6 + 1;
        int totalClasses = 4;
        assertEquals(2.75, calculator.gpa(totalClasses, totalGrades), 0.001);
    }

    @Test
    public void testRounding() {
        // Tests rounding to one decimal place.
        double currentGPA = 2.753456;
        assertEquals(2.8, calculator.round(currentGPA), 0.001);
    }

    @Test
    public void testTotalGrade() {
        // Tests sum of grades.
        int[] grades = {1, 3, 6, 1};
        assertEquals(11, calculator.totalGrade(grades), 0.001);
    }

    @Test
    public void testConvert() {
        // Tests conversion of gpa to percentage.
        double gpa = calculator.round(5.0);
        assertEquals(71, calculator.convert(gpa));
    }
}