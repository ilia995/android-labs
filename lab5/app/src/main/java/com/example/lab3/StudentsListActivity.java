package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.lab3.databinding.ActivityGroupsListBinding;
import com.example.lab3.databinding.ActivityStudentsListBinding;

public class StudentsListActivity extends AppCompatActivity {
    public static final String GROUP_NUMBER = "groupNumber";
    public ActivityStudentsListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent localIntent = getIntent();
        String group = localIntent.getStringExtra(GROUP_NUMBER);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                Student.Companion.getStudentsList(group));
        binding.studentsList.setAdapter(adapter);
    }
}