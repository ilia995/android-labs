package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lab3.databinding.ActivityAddGroupBinding;

public class AddGroup extends AppCompatActivity {
    public ActivityAddGroupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.confirm.setOnClickListener(view -> {
            String groupNumber = binding.groupNumberInput.getText().toString();
            String groupName = binding.facultyInput.getText().toString();

            SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
            try{
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("groupNumber", groupNumber);
                contentValues.put("groupName", groupName);
                contentValues.put("educationLevel", 0);
                contentValues.put("hasContracts", 0);
                contentValues.put("hasPrivilegeStudents", 0);
                db.insert("Groups",
                        null,
                        contentValues);
                db.close();
                Toast.makeText(this,
                        "Група " + groupNumber + " успішно додана",
                        Toast.LENGTH_SHORT).show();
            }catch (SQLiteException e){
                Toast.makeText(this,
                        "Database unavailable",
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}