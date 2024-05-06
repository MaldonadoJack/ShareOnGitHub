package com.example.shareongithub.database.entities;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.shareongithub.database.UserDAO;
import com.example.shareongithub.database.UserDatabase;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Entity(tableName = UserDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    // Constructor with default values
    @Ignore
    public User() {
        this("", "");
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    // Singleton instance to interact with the database
    public static User getInstance(Context context) {
        return new User();
    }

    // Asynchronously delete a user from the database
    private void deleteUserAsync(Context context, String username, ExecutorService executorService, OnUserDeleteListener listener) {
        executorService.execute(() -> {
            UserDAO userDAO = UserDatabase.getDatabase(context).userDAO();
            User userToDelete = userDAO.getUserByUsername(username);
            if (userToDelete != null) {
                userDAO.delete(userToDelete);
                listener.onUserDelete(true);
            } else {
                listener.onUserDelete(false);
            }
        });
    }

    // Public method to delete a user
    public void deleteUser(Context context, String username, ExecutorService executorService, OnUserDeleteListener listener) {
        deleteUserAsync(context, username, executorService, listener);
    }

    // Update user password
    private void updateUserAsync(Context context, String username, String password, ExecutorService executorService) {
        executorService.execute(() -> {
            UserDAO userDAO = UserDatabase.getDatabase(context).userDAO();
            User userToUpdate = userDAO.getUserByUsername(username);
            if (userToUpdate != null) {
                userToUpdate.setPassword(password);
                userDAO.update(userToUpdate);
            }
        });
    }

    // Public method to update user password
    public void updateUser(String username, String password, Context context) {
        ExecutorService executorService = UserDatabase.getExecutorService();
        updateUserAsync(context, username, password, executorService);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // Equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
    }

    // Listener interface for asynchronous deletion
    public interface OnUserDeleteListener {
        void onUserDelete(boolean success);
    }
}
