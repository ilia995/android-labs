package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.lab3.databinding.ActivityGroupsListBinding;

import java.util.ArrayList;
import java.util.Objects;

public class GroupsListActivity extends AppCompatActivity {
    public ActivityGroupsListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.groupsList.setOnItemClickListener((adapterView, view, i, l) -> {
            String group = (String) adapterView.getItemAtPosition(i);
            StudentsGroup groupObj = getThisGroup(group);
            String id = Integer.toString(groupObj != null ? groupObj.getId() : 0);
            Intent intent = new Intent(GroupsListActivity.this, StudentGroupActivity.class);
            intent.putExtra(StudentGroupActivity.GROUP_NUMBER, id);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                // StudentsGroup.getGroups());
                getGroupsFromDB());
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
    private ArrayList<StudentsGroup> getDataFromDB(){
        ArrayList<StudentsGroup> groups = new ArrayList<>();

        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
        try{
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("Groups",
                    new String[]{"id", "groupNumber", "groupName", "educationLevel",
                        "hasContracts", "hasPrivilegeStudents"},
                    null, null, null, null, null);
            while (cursor.moveToNext()){
                groups.add(new StudentsGroup(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        (cursor.getInt(4) > 0),
                        (cursor.getInt(5) > 0)
                ));
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_LONG).show();
        }
        return groups;
    }
    private ArrayList<String> getGroupsFromDB(){
        ArrayList<String> groups = new ArrayList<>();
        for (StudentsGroup g : getDataFromDB()){
            groups.add(g.getNumber());
        }
        return groups;
    }
    private StudentsGroup getThisGroup(String groupNumber){
        for (StudentsGroup g : getDataFromDB()){
            if (Objects.equals(g.getNumber(), groupNumber)){
                return g;
            }
        }
        return null;
    }
}