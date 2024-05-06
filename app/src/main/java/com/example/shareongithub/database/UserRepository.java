package com.example.shareongithub.database;

import android.app.Application;
import android.util.Log;

import com.example.shareongithub.MainActivity;
import com.example.shareongithub.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserRepository {
    private final UserDAO userDAO;

    private static UserRepository repository;

    private UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
    }

    public static UserRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        synchronized (UserRepository.class) {
            if (repository == null) {
                repository = new UserRepository(application);
            }
        }
        return repository;
    }

    public List<User> getAllUsers() {
        Future<List<User>> future = UserDatabase.databaseWriteExecutor.submit(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                return userDAO.getAllUsers();
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(MainActivity.TAG, "Error getting all users", e);
            return null;
        }
    }

    public void insertUser(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDAO.insert(user));
    }

    public void deleteUser(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDAO.delete(user));
    }
}
