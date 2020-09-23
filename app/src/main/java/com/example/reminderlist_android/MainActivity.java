package com.example.reminderlist_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         MainActivity that = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.addNewElement);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ReminderList-App", "Add element");
                EditText text = findViewById(R.id.elementName);
                Toast.makeText(getApplicationContext(), "Element Added: " + text.getText(), Toast.LENGTH_SHORT).show();
                final TextView textView = new TextView(MainActivity.this.getApplicationContext());
                textView.setText(text.getText());
                linearLayout.addView(textView);
            }
        });
    }
}