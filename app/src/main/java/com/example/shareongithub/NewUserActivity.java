package com.example.shareongithub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {

        private EditText usernameEditText;
        private EditText passwordEditText;
        private EditText confirmPasswordEditText;
        private Button signupButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.newuser);

                usernameEditText = findViewById(R.id.editTextText2);
                passwordEditText = findViewById(R.id.editTextText4);
                confirmPasswordEditText = findViewById(R.id.editTextText5);
                signupButton = findViewById(R.id.button3);

                signupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                signUp();
                        }
                });
        }

        private void signUp() {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Check if username and password fields are not empty
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(NewUserActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                        Toast.makeText(NewUserActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                }

                // TODO: Add logic to save the new user's data to your database

                // Notify user that signup was successful
                Toast.makeText(NewUserActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();

                // Navigate back to the login page or wherever needed
                finish();
        }
}
