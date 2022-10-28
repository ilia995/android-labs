package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.databinding.ActivityStudentGroupBinding;

public class StudentGroupActivity extends AppCompatActivity {
    public static final String GROUP_NUMBER = "groupNumber";
    public ActivityStudentGroupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.showInfo.setOnClickListener(view -> {
            String info = "";
            info += "Номер групи: " + binding.groupNumber.getText().toString() + "\n";
            info += "Назва групи: " + binding.faculty.getText().toString() + "\n";
            info += "Освітній рівень: " + (binding.bacRadioButton.isChecked() ? "бакалавр\n" : "магістр\n");
            info += "Є контрактники: " + (binding.hasContracts.isChecked() ? "так\n" : "ні\n");
            info += "Є пільговики: " + (binding.hasPrivilegedStudents.isChecked() ? "так\n" : "ні\n");
            Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
        });

        binding.groupStudentList.setOnClickListener(view -> {
            Intent localIntent = getIntent();
            String group = localIntent.getStringExtra(GROUP_NUMBER);

            Intent newIntent = new Intent(this, StudentsListActivity.class);
            newIntent.putExtra(StudentsListActivity.GROUP_NUMBER, group);
            startActivity(newIntent);
        });

        Intent intent = getIntent();
        String groupNumber = intent.getStringExtra(GROUP_NUMBER);
        StudentsGroup group = StudentsGroup.getGroup(groupNumber);
        EditText txtGroupNumber = (EditText) binding.groupNumber;
        EditText txtFaculty = (EditText) binding.faculty;
        if (group != null){
            String grp = group.getNumber();
            String faculty = group.getGroupName();
            txtGroupNumber.setText(grp);
            txtFaculty.setText(faculty);
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