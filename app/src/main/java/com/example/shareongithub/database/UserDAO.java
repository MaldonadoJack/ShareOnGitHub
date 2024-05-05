package com.example.shareongithub.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.shareongithub.database.entities.GradeLog;
import com.example.shareongithub.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User ... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GradeLogDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

    @Query("DELETE FROM " + GradeLogDatabase.USER_TABLE)
    void deleteAll();
}
