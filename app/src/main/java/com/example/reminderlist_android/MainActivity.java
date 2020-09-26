package com.example.reminderlist_android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    public static final String SHARED_PREFS = "sharedPrefs";
    private List<String> taskForPrefs;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.addNewElement);
        linearLayout = (LinearLayout) findViewById(R.id.    linearLayout);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.elementName);
                Toast.makeText(getApplicationContext(), "Element Added: " + text.getText(), Toast.LENGTH_SHORT).show();
                addToList(text.getText().toString(), true);
            }
        });
    }

    private void addToList(String text, Boolean saveToPrefs) {
        Log.i("ReminderList-App", "Add element");

        final TextView textView = new TextView(MainActivity.this.getApplicationContext());
        final Button deleteBtn = new Button(MainActivity.this.getApplicationContext());
        final LinearLayout row = new LinearLayout(MainActivity.this.getApplicationContext());

        row.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        deleteBtn.setText("X");
        textView.setText(text);
        row.addView(textView);
        row.addView(deleteBtn);
        row.setId(taskForPrefs.size());

        linearLayout.addView(row);

        if(saveToPrefs) {
            saveData(text);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromList(row);
            }
        });    }

    private void removeFromList(LinearLayout row) {
        Toast.makeText(getApplicationContext(), "Element Removed:" + row.getId(), Toast.LENGTH_SHORT).show();
        linearLayout.removeView(row);
        taskForPrefs.remove(
                taskForPrefs.get(row.getId() - 1));
        saveData("");
    }

    private void saveData(String taskName) {

        if(taskName != "") {
            taskForPrefs.add(taskName);
        }

        editor.putString("reminderList", taskForPrefs
                .toString()
                .replaceAll("[|]", "")
        );
        editor.apply();
        Log.i("SharedPrefs: " , taskForPrefs.toString());

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        taskForPrefs = new ArrayList<String>(Arrays.asList(sharedPreferences.getString("reminderList", "").split(",")));
        Log.i("ReminderList-App", "HERE WE GO AGAIN " + taskForPrefs);

        for (int i = 0; i < taskForPrefs.size() - 1; i++) {
            addToList(taskForPrefs.get(i), false);
        }
    }

    private void clearPrefs() {
        editor.putString("reminderList", "");
        editor.apply();
    }

}