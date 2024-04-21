package com.example.shareongithub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shareongithub.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private static final String TAG = "DAC_GRADEXLOG";
    String mCourse = "";
    double mGrade = 0.0;
    String mSemester = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.CourseView.setMovementMethod( new ScrollingMovementMethod());
        
        binding.EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                updateDisplay();

            }
        });

    }

    private void updateDisplay() {
        String currentInfo = binding.CourseView.getText().toString();
        Log.d(TAG , "current info: " + currentInfo);
        String newDisplay = String.format(Locale.US , "Course:%s%nGrade:%.2f%nSemester:%s%n=-=-=-=%n%s" , mCourse , mGrade , mSemester , currentInfo);
        binding.CourseView.setText(newDisplay);
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
