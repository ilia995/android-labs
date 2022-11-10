package com.example.lab3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.databinding.ActivityAddNewStudentBinding;


public class AddNewStudentActivity extends AppCompatActivity {
    public static final String GROUP_NUMBER = "groupNumber";
    public ActivityAddNewStudentBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String groupId = intent.getStringExtra(GROUP_NUMBER);

        binding.addConfirm.setOnClickListener(view -> {
            String newStudName = binding.newStudentName.getText().toString();
            if (newStudName.isEmpty()) {
                Toast.makeText(this,
                        "Введіть ім'я",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
            try{
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", newStudName);
                contentValues.put("group_id", groupId);
                db.insert("Students",
                        null,
                        contentValues);
                db.close();
                Toast.makeText(this,
                        "Студента додано",
                        Toast.LENGTH_SHORT).show();
                finish();
            }catch (SQLiteException e){
                Toast.makeText(this,
                        "Database unavailable",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
