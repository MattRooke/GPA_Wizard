/*Main activity that displays important at-a-glance GPA score information */

package com.utilityapp.gpa_wizard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class GpaViewActivity extends AppCompatActivity {
    public static final double DEFAULT_GPA = 0.0;
    public static final int DEFAULT_CLASSES = 0;
    public static final double DEFAULT_GRADE = 0;
    public static final int GOOD_GPA = 71;
    public static final int DELAY_MILLIS = 20;
    public static final int DIFF = 1;
    public static final double INCREMENT = 0.1;
    public static final int NUM_NEW_CLASSES = 4;

    //Incremental limits & colour codes for changes in progress bar:
    public static final int[] LIMS = {0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72, 78, 84, 90,
            96, 100};
    public static final int[] COLOURS = {0xFFFFFFFF, 0xFFFF0000, 0xFFFF2200, 0xFFFF4400, 0xFFFF6600,
            0xFFFF8800, 0xFFFFAA00, 0xFFFFCC00, 0xFFFFEE00, 0xFFEEFF00, 0xFFCCFF00, 0xFFAAFF00,
            0xFF88FF00, 0xFF66FF00, 0xFF44FF00, 0xFF22FF00, 0xFF11FF00, 0xFF00FF00};


    ProgressBar gpaProgressbar;
    TextView gpaView;
    Handler pbHandler;
    Handler tvHandler;
    ImageView goodJobStars;
    Calculator calculator = new Calculator();
    int oldScore, updatedScore;
    double oldGpaRounded, currentGpaRounded;
    double totalGrade = DEFAULT_GRADE;
    int totalClasses = DEFAULT_CLASSES;
    double oldGPA = DEFAULT_GPA, currentGPA = DEFAULT_GPA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        gpaProgressbar = findViewById(R.id.gpaProgressBar);
        gpaView = findViewById(R.id.gpaView);
        goodJobStars = findViewById(R.id.starVectorMiddle);
        goodJobStars.setVisibility(View.INVISIBLE);

        if (savedInstanceState != null) {
            currentGPA = savedInstanceState.getDouble("currentGPA");
            oldGPA = savedInstanceState.getDouble("oldGPA");
            totalGrade = savedInstanceState.getDouble("totalGrade");
            totalClasses = savedInstanceState.getInt("totalClasses");

            updateProgressBar();
            updateGpaView();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("currentGPA", currentGPA);
        outState.putDouble("oldGPA", oldGPA);
        outState.putDouble("totalGrade", totalGrade);
        outState.putInt("totalClasses", totalClasses);
    }

    /* A progress bar that fills and changes colour depending on gpa to make at-a-glance
    understanding of gpa faster.*/
    public void updateProgressBar() {
        currentGpaRounded = calculator.round(currentGPA);
        oldGpaRounded = calculator.round(oldGPA);
        updatedScore = calculator.convert(currentGpaRounded);
        oldScore = calculator.convert(oldGpaRounded);
        gpaProgressbar.setProgress(oldScore);
        pbHandler = new Handler();
        pbHandler.post(new Runnable() {
            public void run() {
                //Determines colour and makes a smooth colour transition.
                for (int limit = 0; limit < LIMS.length; limit++) {
                    if (gpaProgressbar.getProgress() >= LIMS[limit]) {
                        gpaProgressbar.getProgressDrawable().setColorFilter(
                                COLOURS[limit], android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                }

                //Adds star vector to emphasis good grades at-a-glance.
                if (oldScore >= GOOD_GPA) {
                    goodJobStars.setVisibility(View.VISIBLE);
                } else {
                    goodJobStars.setVisibility(View.INVISIBLE);
                }

                /* Increments progress to animate bar filling, emphasising up and down changes
                at-a-glance.*/
                if (oldScore < updatedScore) {
                    oldScore++;
                    gpaProgressbar.incrementProgressBy(DIFF);
                    pbHandler.postDelayed(this, DELAY_MILLIS);
                } else if (oldScore > updatedScore) {
                    oldScore--;
                    gpaProgressbar.incrementProgressBy(-DIFF);
                    pbHandler.postDelayed(this, DELAY_MILLIS);
                }
            }
        });
    }

    //Updater for the main text view that shows current GPA.
    public void updateGpaView() {
        currentGpaRounded = calculator.round(currentGPA);
        gpaView.setText(String.format(Locale.US, "%.1f", oldGpaRounded));
        tvHandler = new Handler();
        tvHandler.post(new Runnable() {
            //Increments numbers to animate gpa changes, emphasising up and down changes at-a-glance.
            public void run() {
                oldGpaRounded = calculator.round(oldGpaRounded); //Keeps result rounded to 1 decimal.
                if (oldGpaRounded < currentGpaRounded) {
                    oldGpaRounded += INCREMENT;
                    gpaView.setText(String.format(Locale.US, "%.1f", oldGpaRounded));
                    tvHandler.postDelayed(this, DELAY_MILLIS);
                } else if (oldGpaRounded > currentGpaRounded) {
                    oldGpaRounded -= INCREMENT;
                    gpaView.setText(String.format(Locale.US, "%.1f", oldGpaRounded));
                    tvHandler.postDelayed(this, DELAY_MILLIS);
                }
            }
        });

    }

    //Button click intent to move to the AddGrades activity.
    public void gradesButtonClicked(View view) {
        Intent addGradesIntent = new Intent(this, AddGradesActivity.class);
        startActivityForResult(addGradesIntent, AddGradesActivity.ADD_GRADES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddGradesActivity.ADD_GRADES_REQUEST) {
            if (resultCode == RESULT_OK) {
                //Gets the result of the grades entered by the user.
                if (data != null) {
                    totalGrade += data.getDoubleExtra("totalGrade", DEFAULT_GRADE);
                    totalClasses += NUM_NEW_CLASSES;
                }
            }
        }

        currentGPA = calculator.gpa(totalClasses, totalGrade);
        updateProgressBar();
        updateGpaView();
        oldGPA = currentGPA;
    }

}
