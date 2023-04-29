package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.mediation.Adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add;
    RecyclerView reminder_view;
    ArrayList<Model> dataholder = new ArrayList<Model>();
    myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.addReminder);
        reminder_view = findViewById(R.id.recyclerReminder);
        reminder_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), reminder_activity.class);
                startActivity(intent);


            }

        });
        Cursor cursor = new dbManager(getApplicationContext()).readReminders();
        while (cursor.moveToNext()) {
            Model model = new Model(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder.add(model);
        }
        adapter = new Adapter(dataholder);
        reminder_view.setAdapter(adapter);
    }
        @Override
        public void onBackPressed () {
            finish();
            super.onBackPressed();

        }
    }



