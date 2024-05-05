package com.example.shareongithub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shareongithub.database.GradeLogRepository;
import com.example.shareongithub.database.entities.GradeLog;
import com.example.shareongithub.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GradeLogRepository repository;

    public static final String TAG = "DAC_GRADEXLOG";
    String mCourse = "";
    double mGrade = 0.0;
    String mSemester = "";
    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GradeLogRepository.getRepository(getApplication());

        binding.CourseText.setMovementMethod(new ScrollingMovementMethod());
        updateDisplay();

        binding.EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
                showLetterGradeDialog(); // Show the letter grade dialog
            }
        });

        binding.CourseInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplay();
            }
        });
    }

    private void insertGymLogRecord() {
        if (mCourse.isEmpty()) {
            return;
        }
        GradeLog log = new GradeLog(mCourse, mGrade, mSemester, loggedInUserId);
        repository.insertGradeLog(log);
    }

    private void updateDisplay() {
        ArrayList<GradeLog> allLogs = repository.getAllLogs();
        if (allLogs.isEmpty()) {
            binding.CourseText.setText(R.string.nothing_to_show_enter_information);
        }
        StringBuilder sb = new StringBuilder();
        for (GradeLog log : allLogs) {
            sb.append(log);
        }
        binding.CourseText.setText(sb.toString());
    }

    private void getInformationFromDisplay() {
        mCourse = binding.CourseInput.getText().toString();
        String gradeInput = binding.GradeInput.getText().toString();

        if (!gradeInput.isEmpty()) {
            try {
                mGrade = Double.parseDouble(gradeInput);
            } catch (NumberFormatException e) {
                Log.d(TAG, "Error parsing grade input: " + gradeInput, e);
                mGrade = -1; // Set mGrade to a negative value to indicate an invalid grade
            }
        } else {
            mGrade = -1; // Set mGrade to a negative value to indicate an empty grade input
        }

        Log.d(TAG, "Parsed grade: " + mGrade); // Log the parsed grade
        mSemester = binding.SemesterText.getText().toString();
    }




    // Calculate letter grade based on number grade
    private String calculateLetterGrade(double grade) {
        Log.d(TAG, "Calculating letter grade for grade: " + grade);
        if (grade >= 90) {
            return "A";
        } else if (grade >= 80) {
            return "B";
        } else if (grade >= 70) {
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else if (grade >= 0 && grade < 60) {
            return "F";
        } else {
            return "Invalid"; // Handle invalid grade inputs
        }
    }

    public void onClick(View v) {
        getInformationFromDisplay();
        if (mGrade >= 0) {
            insertGymLogRecord();
            updateDisplay();
            showLetterGradeDialog(); // Show the letter grade dialog
        } else {
            // Show error message or handle invalid grade input
            Log.d(TAG, "Invalid grade input");
        }
    }


    // Show pop-up dialog with letter grade
    private void showLetterGradeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_grade, null);
        TextView letterGradeTextView = dialogView.findViewById(R.id.letterGradeTextView);

        // Calculate letter grade
        String letterGrade = calculateLetterGrade(mGrade);
        letterGradeTextView.setText(letterGrade);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
