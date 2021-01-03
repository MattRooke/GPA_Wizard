/*Activity that allows users input to add grades for the gpa calculation. */

package com.utilityapp.gpa_wizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddGradesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static int ADD_GRADES_REQUEST = 1;
    Spinner spin1;
    Spinner spin2;
    Spinner spin3;
    Spinner spin4;
    String[] possibleGrades = {"1", "2", "3", "4", "5", "6", "7"};
    Button save;
    Calculator calculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);

        save = findViewById(R.id.saveButton);

        //Uses spinners for grade selection to avoid input errors.
        spin1 = findViewById(R.id.textGradeOne);
        spin1.setOnItemSelectedListener(this);
        spin2 = findViewById(R.id.textGradeTwo);
        spin2.setOnItemSelectedListener(this);
        spin3 = findViewById(R.id.textGradeThree);
        spin3.setOnItemSelectedListener(this);
        spin4 = findViewById(R.id.textGradeFour);
        spin4.setOnItemSelectedListener(this);

        ArrayAdapter gradesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, possibleGrades);
        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(gradesAdapter);
        spin2.setAdapter(gradesAdapter);
        spin3.setAdapter(gradesAdapter);
        spin4.setAdapter(gradesAdapter);

    }

    // Action preformed on user press save button, sending total grade to the main activity.
    public void saveClicked(View view) {
        int grade1 = Integer.parseInt(spin1.getSelectedItem().toString());
        int grade2 = Integer.parseInt(spin2.getSelectedItem().toString());
        int grade3 = Integer.parseInt(spin3.getSelectedItem().toString());
        int grade4 = Integer.parseInt(spin4.getSelectedItem().toString());
        //stores results in array form for possible future scalability.
        int[] grades = new int[]{grade1, grade2, grade3, grade4};

        double totalGrade = calculator.totalGrade(grades);

        Intent intent = new Intent();
        intent.putExtra("totalGrade", totalGrade);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
