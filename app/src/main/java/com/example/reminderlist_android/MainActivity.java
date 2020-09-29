package com.example.reminderlist_android;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    public static final String SHARED_PREFS = "sharedPrefs";
    private List<String> taskForPrefs;
    private HashMap<String, Boolean> activeTasks;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<LinearLayout> linearLayouts;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayouts = new ArrayList<>();

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.addNewElement);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);

        linearLayout = (LinearLayout) findViewById(R.id.    linearLayout);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.elementName);
                addToList(text.getText().toString(), false, false);
            }

        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPrefs();
            }
        });

    }

    private void addToList(final String text, Boolean initial, Boolean isChecked) {

        final TextView textView = new TextView(MainActivity.this.getApplicationContext());
        final Button deleteBtn = new Button(MainActivity.this.getApplicationContext());
        final CheckBox checkBox = new CheckBox(MainActivity.this.getApplicationContext());
        final LinearLayout row = new LinearLayout(MainActivity.this.getApplicationContext());

        if(isChecked) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        if ((!taskForPrefs.contains(text) || initial) && text != "") {
            Log.i("ReminderList-App", "Add element" + text);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            deleteBtn.setText("X");
            textView.setText(text);
            row.addView(textView);
            row.addView(deleteBtn);
            row.addView(checkBox);
            row.setId(taskForPrefs.size());

            linearLayout.addView(row);
            linearLayouts.add(linearLayout);
            if(!initial) {
                saveName(text.trim());
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFromList(row, text);
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveChecked(checkBox.isChecked(), text.trim());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Element exists or empty", Toast.LENGTH_SHORT).show();
        }


    }

    private void removeFromList(LinearLayout row, String text) {
        Toast.makeText(getApplicationContext(), "Element Removed: " + text, Toast.LENGTH_SHORT).show();
        linearLayout.removeView(row);
        taskForPrefs.remove(text);
        saveName("");
    }

    private void saveName(String taskName) {
        if(taskName != "") {
            taskForPrefs.add(taskName);
        }
        editor.putString("reminderList", taskForPrefs
                .toString()
                .substring(1, taskForPrefs.toString().length() - 1));
        editor.apply();
        Log.i("SharedPrefs: " , taskForPrefs.toString());
    }

    private void saveChecked(Boolean isChecked, String taskName) {
        editor.putBoolean(taskName, isChecked);
        editor.apply();
        Log.i("ShaderPrefs: ", taskName + ": " + isChecked);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        taskForPrefs = new ArrayList<String>(Arrays.asList(sharedPreferences.getString("reminderList", "").split(",")));
        activeTasks = new HashMap<String, Boolean>();
        Log.i("ReminderList-App", "HERE WE GO AGAIN " + taskForPrefs);
        Boolean isChecked = false;
        for (int i = 0; i < taskForPrefs.size(); i++) {
            isChecked = sharedPreferences.getBoolean(taskForPrefs.get(i).trim(), false);
            activeTasks.put(taskForPrefs.get(i), isChecked);
            addToList(taskForPrefs.get(i), true, isChecked);
        }
    }

    private void clearPrefs() {
        editor.putString("reminderList", "");
        for(String task: taskForPrefs) {
            editor.remove(task);
        }

        activeTasks.clear();
        taskForPrefs.clear();
        editor.apply();

        for(LinearLayout linearLayout: linearLayouts) {
            linearLayout.removeAllViews();
        }
    }

}