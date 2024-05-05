package com.example.shareongithub.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.shareongithub.database.entities.GradeLog;

import java.util.List;

@Dao
public interface GradeLogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GradeLog gradeLog);

    @Query("SELECT * FROM " + GradeLogDatabase.GRADE_LOG_TABLE + " ORDER BY date DESC")
    List<GradeLog> getAllRecords();
}
