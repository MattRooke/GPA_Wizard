/*Model for performing the simple calculations needed to display correct GPA*/

package com.utilityapp.gpa_wizard;

class Calculator {

    //Adds grades together to return a total grade for gpa calculation.
    double totalGrade(int[] grades) {
        double totalGrade = 0;
        for (int value : grades
        ) {
            totalGrade += value;
        }
        return totalGrade;
    }

    //Calculates simple GPA (average)
    double gpa(int totalClasses, double totalGrade) {
        return totalGrade / totalClasses;
    }

    //rounds to 1 decimal place for GPA display
    double round(double num) {
        return Math.round(num * 10) / 10.0;
    }

    /*Converts double type gpa to an integer representing a percentage that can be displayed
    by the progress bar. */
    int convert(double roundedGpa){
        double convertedScore = (roundedGpa / 7) * 100;
        return (int) convertedScore;
    }
}
