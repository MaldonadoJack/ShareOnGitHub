package com.example.shareongithub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import com.example.shareongithub.database.GradeLogRepository;
import com.example.shareongithub.database.entities.GradeLog;
import com.example.shareongithub.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

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

        binding.CourseText.setMovementMethod( new ScrollingMovementMethod());
        updateDisplay();

        binding.EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();

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
        GradeLog log = new GradeLog(mCourse , mGrade , mSemester , loggedInUserId);
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

        try {
            mGrade = Double.parseDouble(binding.GradeInput.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("TAG" , "Error reading value from Grade edit text");
        }

        mSemester = binding.SemesterText.getText().toString();
    }
}
