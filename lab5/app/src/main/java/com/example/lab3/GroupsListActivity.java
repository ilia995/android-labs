package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.lab3.databinding.ActivityGroupsListBinding;

public class GroupsListActivity extends AppCompatActivity {
    public ActivityGroupsListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.groupsList.setOnItemClickListener((adapterView, view, i, l) -> {
            String group = (String) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(GroupsListActivity.this, StudentGroupActivity.class);
            intent.putExtra(StudentGroupActivity.GROUP_NUMBER, group);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                StudentsGroup.getGroups());
        binding.groupsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.groups_menu, menu);
        StringBuilder text = new StringBuilder();
        for (String group: StudentsGroup.getGroups()){
            text.append(group).append("\n");
        }
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, (CharSequence) text);
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(intent);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_group) {
            startActivity(
                    new Intent(this, AddGroup.class)
            );
        }
        return super.onOptionsItemSelected(item);
    }
}