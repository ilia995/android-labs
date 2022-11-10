package com.example.lab3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.databinding.ActivityStudentGroupBinding;

public class StudentGroupActivity extends AppCompatActivity {
    public static final String GROUP_NUMBER = "groupNumber";
    public ActivityStudentGroupBinding binding;

    private StudentsGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ok.setOnClickListener(view -> {
            SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
            String group = binding.groupNumber.getText().toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put("groupNumber", group);
            contentValues.put("groupName", binding.faculty.getText().toString());
            contentValues.put("educationLevel", (binding.bacRadioButton.isChecked() ? 0 : 1));
            contentValues.put("hasContracts", (binding.hasContracts.isChecked() ? 1 : 0));
            contentValues.put("hasPrivilegeStudents", (binding.hasPrivilegedStudents.isChecked() ? 1 : 0));

            Intent intent = getIntent();
            String groupId = intent.getStringExtra(GROUP_NUMBER);
            System.out.println("id   " + groupId);
            try{
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                db.update("Groups",
                        contentValues,
                        "id=?",
                        new String[]{groupId});
                Toast.makeText(this,
                        "Дані для групи " + group + " змінено",
                        Toast.LENGTH_SHORT).show();
            }catch (SQLiteException e){
                Toast.makeText(this,
                        "Database unavailable",
                        Toast.LENGTH_SHORT).show();
            }
        });

        binding.delete.setOnClickListener(view -> {
            SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
            Intent intent = getIntent();
            String groupId = intent.getStringExtra(GROUP_NUMBER);
            try{
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                db.delete("Groups",
                        "id=?",
                        new String[]{groupId});
                Toast.makeText(this,
                        "Група видалена",
                        Toast.LENGTH_SHORT).show();
                finish();
            }catch (SQLiteException e){
                Toast.makeText(this,
                        "Database unavailable",
                        Toast.LENGTH_SHORT).show();
            }
        });

        binding.groupStudentList.setOnClickListener(view -> {
            Intent newIntent = new Intent(this, StudentsListActivity.class);
            newIntent.putExtra(StudentsListActivity.GROUP_NUMBER, group.getId());
            startActivity(newIntent);
        });


        Intent intent = getIntent();
        String groupNumber = intent.getStringExtra(GROUP_NUMBER);
        group = null;
        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
        try{
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("Groups",
                    new String[]{"id", "groupNumber", "groupName", "educationLevel",
                            "hasContracts", "hasPrivilegeStudents"},
                    "id=?",
                    new String[]{groupNumber},
                    null, null, null);
            if (cursor.moveToFirst()){
                group = new StudentsGroup(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        (cursor.getInt(4) > 0),
                        (cursor.getInt(5) > 0)
                );
            }else{
                Toast.makeText(this,
                    "Can't find group with id " + groupNumber,
                    Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this,
                "Database unavailable",
                Toast.LENGTH_SHORT).show();
        }

        if (group != null){
            String grp = group.getNumber();
            String faculty = group.getGroupName();
            binding.groupNumber.setText(grp);
            binding.faculty.setText(faculty);
            binding.imgTextGroup.setText(grp);
            binding.imgTextFaculty.setText(faculty);
            if (group.getEducationLevel() == 0){
                binding.bacRadioButton.setChecked(true);
            }else{
                binding.magRadioButton.setChecked(true);
            }
            if (group.hasContracts()){
                binding.hasContracts.setChecked(true);
            }
            if (group.hasPrivilegeStudent()){
                binding.hasPrivilegedStudents.setChecked(true);
            }
        }
    }
}