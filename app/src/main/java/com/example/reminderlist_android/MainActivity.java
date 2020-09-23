package com.example.reminderlist_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.addNewElement);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        addToList(linearLayout, "start");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.elementName);
                Toast.makeText(getApplicationContext(), "Element Added: " + text.getText(), Toast.LENGTH_SHORT).show();
                addToList(linearLayout, text.getText().toString());
            }
        });
    }

    private void addToList(LinearLayout linearLayout, String text) {
        Log.i("ReminderList-App", "Add element");
        final TextView textView = new TextView(MainActivity.this.getApplicationContext());
        textView.setText(text);
        linearLayout.addView(textView);
    }
}