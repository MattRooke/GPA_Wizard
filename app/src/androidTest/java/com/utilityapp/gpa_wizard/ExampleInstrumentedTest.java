package com.utilityapp.gpa_wizard;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Calculator calculator = new Calculator();

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.utilityapp.gpa_wizard", appContext.getPackageName());
    }

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
}
