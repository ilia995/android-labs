package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
            StudentsGroup.addGroup(groupNumber, groupName);
            finish();
        });
    }
}