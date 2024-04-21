package com.example.shareongithub.database;

import android.app.Application;
import android.util.Log;

import com.example.shareongithub.MainActivity;
import com.example.shareongithub.database.entities.GradeLog;
import com.example.shareongithub.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GradeLogRepository {
    private final GradeLogDAO gradeLogDAO;
    private final UserDAO userDAO;
    private ArrayList<GradeLog> allLogs;

    private static GradeLogRepository repository;

    private GradeLogRepository(Application application) {
        GradeLogDatabase db = GradeLogDatabase.getDatabase(application);
        this.gradeLogDAO = db.gradeLogDAO();
        this.allLogs = (ArrayList<GradeLog>) this.gradeLogDAO.getAllRecords();
        this.userDAO = db.userDAO();
    }

    public static GradeLogRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<GradeLogRepository> future = GradeLogDatabase.databaseWriteExecutor.submit(
                new Callable<GradeLogRepository>() {
                    @Override
                    public GradeLogRepository call() throws Exception {
                        return new GradeLogRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch(InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG , "Problem getting GradeLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<GradeLog> getAllLogs() {
        Future<ArrayList<GradeLog>> future = GradeLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GradeLog>>() {
                    @Override
                    public ArrayList<GradeLog> call() throws Exception {
                        return (ArrayList<GradeLog>) gradeLogDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG , "Problem when getting all GradeLogs in repository");
        }
        return null;
    }

    public void insertGradeLog(GradeLog gradeLog) {
        GradeLogDatabase.databaseWriteExecutor.execute(() ->
        {
            gradeLogDAO.insert(gradeLog);
        });
    }

    public void insertUser(User... user) {
        GradeLogDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }
}
