package com.example.shareongithub.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shareongithub.MainActivity;
import com.example.shareongithub.database.entities.User;
import com.example.shareongithub.database.entities.GradeLog;
import com.example.shareongithub.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {GradeLog.class , User.class} , version = 4 , exportSchema = false)
public abstract class GradeLogDatabase extends RoomDatabase {

    public static final String USER_TABLE = "userTable";
    private static final String DATABASE_NAME = "GradeLogDatabase";
    public static final String GRADE_LOG_TABLE = "gradeLogTable";

    private static volatile GradeLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GradeLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GradeLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext() ,
                            GradeLogDatabase.class , DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG , "DATABASE CREATED");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1" , "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1" , "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract GradeLogDAO gradeLogDAO();

    public abstract UserDAO userDAO();
}
