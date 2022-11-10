package com.example.lab3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.databinding.ActivityEditOrRemoveStudentBinding;

public class EditOrRemoveStudentActivity extends AppCompatActivity {
    public ActivityEditOrRemoveStudentBinding binding;
    public static final String GROUP_NUMBER = "groupNumber";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditOrRemoveStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent localIntent = getIntent();
        String studentName = localIntent.getStringExtra(GROUP_NUMBER);
        binding.stName.setText(studentName);
        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
        binding.changeNameConfirm.setOnClickListener(view -> {
            String newName = binding.stName.getText().toString();
            try{
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", newName);
                db.update("Students",
                        contentValues,
                        "name = ?",
                        new String[]{studentName});
                Toast.makeText(this,
                        "Ім'я студента змінено",
                        Toast.LENGTH_SHORT).show();
                finish();
            }catch (SQLiteException e){
                dbUnavailable();
            }
        });
        binding.removeConfirm.setOnClickListener(view -> {
            try{
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                db.delete("Students",
                        "name = ?",
                        new String[]{studentName});
                Toast.makeText(this,
                        "Студента видалено",
                        Toast.LENGTH_SHORT).show();
                finish();
            }catch (SQLiteException e){
                dbUnavailable();
            }
        });
    }
    private void dbUnavailable(){
        Toast.makeText(this,
                "Database unavailable",
                Toast.LENGTH_SHORT).show();
    }
}
