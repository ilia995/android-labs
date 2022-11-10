package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StudentsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "students_database";
    private static final int DB_VERSION = 1;

    public StudentsDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlString = "CREATE TABLE Groups (" +
                "id                   INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "groupNumber          TEXT (10) NOT NULL, " +
                "groupName            TEXT (100), " +
                "educationLevel       INTEGER, " +
                "hasContracts         BOOLEAN, " +
                "hasPrivilegeStudents BOOLEAN);";
        sqLiteDatabase.execSQL(sqlString);
        updateSchema(sqLiteDatabase, 0);
        populateDB(sqLiteDatabase);
    }

    private void populateDB(SQLiteDatabase db){
        populateGroups(db);
        populateStudents(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        updateSchema(sqLiteDatabase, oldV);
    }

    private void populateGroups(SQLiteDatabase db){
        for (StudentsGroup group : StudentsGroup.getGroupObjects()){
            insertRowToGroup(db, group);
        }
    }
    private void populateStudents(SQLiteDatabase db){
        for (Student student : Student.Companion.getStudents()){
            insertRowToStudent(db, student);
        }
    }

    private void insertRowToGroup(SQLiteDatabase db, StudentsGroup group){
        ContentValues contentValues = new ContentValues();
        contentValues.put("groupNumber", group.getNumber());
        contentValues.put("groupName", group.getGroupName());
        contentValues.put("educationLevel", group.getEducationLevel());
        contentValues.put("hasContracts", group.hasContracts());
        contentValues.put("hasPrivilegeStudents", group.hasPrivilegeStudent());
        db.insert("Groups", null, contentValues);
    }

    private void insertRowToStudent(SQLiteDatabase db, Student student){
        db.execSQL("INSERT INTO Students(name, group_id) " +
                "SELECT '" + student.getName() + "', id " +
                "FROM Groups " +
                "WHERE groupNumber = '" + student.getGroupNumber() + "';");
    }

    private void updateSchema(SQLiteDatabase db, int oldV){
        if (oldV < 2){
            db.execSQL("CREATE TABLE Students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT(100) NOT NULL, " +
                "group_id INTEGER REFERENCES Groups (id) " +
                    "ON DELETE RESTRICT " +
                    "ON UPDATE RESTRICT);");
        }
    }
}
