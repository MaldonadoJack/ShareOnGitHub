package com.example.shareongithub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shareongithub.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminActivity extends AppCompatActivity {

    private EditText usernameInput;
    private Button deleteUserButton;
    private User userRepository;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        usernameInput = findViewById(R.id.UsernameInput);
        deleteUserButton = findViewById(R.id.DeleteUserButton);
        userRepository = User.getInstance(getApplicationContext());

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        // Initialize ExecutorService
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor when the activity is destroyed
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void deleteUser() {
        String username = usernameInput.getText().toString().trim();

        // Check if username is empty
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        // Delete the user from the database asynchronously
        userRepository.deleteUser(getApplicationContext(), username, executorService, new User.OnUserDeleteListener() {
            @Override
            public void onUserDelete(boolean success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            Toast.makeText(AdminActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                            // Clear the username input field after successful deletion
                            usernameInput.setText("");
                        } else {
                            Toast.makeText(AdminActivity.this, "Failed to delete user. User not found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                executorService.shutdown(); // Shutdown the executor when done
            }
        });
    }


}
