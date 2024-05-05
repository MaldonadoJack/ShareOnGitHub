package com.example.shareongithub.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import androidx.room.PrimaryKey;

import com.example.shareongithub.database.GradeLogDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = GradeLogDatabase.GRADE_LOG_TABLE)
public class GradeLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String course;
    private double grade;
    private String semester;
    private LocalDateTime date;
    private int userId;

    public GradeLog(String course, double grade, String semester , int userId) {
        this.course = course;
        this.grade = grade;
        this.semester = semester;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return course + '\n' + "grade: " + grade + '\n' + "semester: " + semester + '\n' + "date: " + date.toString() + '\n' + "=-=-=-=-=-=-=-=\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeLog gradeLog = (GradeLog) o;
        return id == gradeLog.id && Double.compare(gradeLog.grade, grade) == 0 && userId == gradeLog.userId && Objects.equals(course, gradeLog.course) && Objects.equals(semester, gradeLog.semester) && Objects.equals(date, gradeLog.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, grade, semester, date, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}