package com.example.shareongithub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        usernameEditText = findViewById(R.id.UsernameEditText);
        passwordEditText = findViewById(R.id.PasswordEditText);
        loginButton = findViewById(R.id.LoginButton1);
        signupButton = findViewById(R.id.SignUpButton1);

        // Set listeners
        usernameEditText.setOnEditorActionListener(this);
        passwordEditText.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Dummy check for demonstration (replace with actual database check)
        if (username.equals("Tanmay1") && password.equals("Tanmay1")) {
            // Successful login, navigate to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish LoginActivity to prevent going back
        } else {
            // Failed login, show error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            // Username or password is empty, navigate to NewUserActivity
            Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
            startActivity(intent);
            finish(); // Finish the LoginActivity
        } else {
            // Continue with the login process
            loginUser();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            loginUser();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LoginButton1) {
            loginUser();
        } else if (v.getId() == R.id.SignUpButton1) {
            signUp();
        }
    }
}
