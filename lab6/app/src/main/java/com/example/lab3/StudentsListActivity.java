package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.lab3.databinding.ActivityGroupsListBinding;
import com.example.lab3.databinding.ActivityStudentsListBinding;

public class StudentsListActivity extends AppCompatActivity {
    public ActivityStudentsListBinding binding;

    public static final String GROUP_NUMBER = "groupNumber";
    private Cursor cursor;
    private SQLiteDatabase db;
    private int groupId = 0;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent localIntent = getIntent();
        groupId = localIntent.getIntExtra(GROUP_NUMBER, 0);

        binding.addStudent.setOnClickListener(view -> {
            Intent intent = new Intent(StudentsListActivity.this, AddNewStudentActivity.class);
            intent.putExtra(StudentGroupActivity.GROUP_NUMBER, Integer.toString(groupId));
            startActivity(intent);
        });

        binding.studentsList.setOnItemClickListener((adapterView, view, i, l) -> {
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
            String student = null;
            if (cursor.moveToPosition(i)) {
                student = cursor.getString(1);
            }
            Intent intent = new Intent(StudentsListActivity.this, EditOrRemoveStudentActivity.class);
            intent.putExtra(StudentGroupActivity.GROUP_NUMBER, student);
            startActivity(intent);
        });
    }
    protected void onStart(){
        super.onStart();
        ListView listView = binding.studentsList;
        SimpleCursorAdapter adapter = getDataFromDB(groupId);
        if (adapter != null){
            listView.setAdapter(adapter);
        }
    }
    private SimpleCursorAdapter getDataFromDB(int groupId){
        SimpleCursorAdapter listAdapter = null;
        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
        try{
            db = sqLiteOpenHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT s.id _id, name, groupNumber " +
                    "FROM Students s INNER JOIN Groups g ON s.group_id = g.id " +
                    "WHERE g.id = ?", new String[]{Integer.toString(groupId)} );
            listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"name"},
                    new int[]{android.R.id.text1},
                    0);
        }catch (SQLiteException e){
            Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT).show();
        }
        return listAdapter;
    }
}