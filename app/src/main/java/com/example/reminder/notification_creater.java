package com.example.reminder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class notification_creater extends AppCompatActivity {
    TextView txtMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        txtMessage = findViewById(R.id.title);
        Bundle bundle = getIntent().getExtras();                                                    //call the data which is passed by another intent
        txtMessage.setText(bundle.getString("message"));
    }
}

