package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class reminder_activity extends AppCompatActivity {
    Button addTime,addDate,add;
    EditText titleTxt;
    String time_noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        addTime=findViewById(R.id.btnTime);
        addDate=findViewById(R.id.btnDate);
        add=findViewById(R.id.btnAdd);

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_select();
            }
        });

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_select();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTxt.getText().toString().trim();
                String time = addTime.getText().toString().trim();
                String date = addDate.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(reminder_activity.this, "Please add the Title", Toast.LENGTH_SHORT).show();
                } else if (time.equals("time") || date.equals("date")) {
                    Toast.makeText(reminder_activity.this, "Please select a date and a time", Toast.LENGTH_SHORT).show();
                } else {
                    add_data(title, date, time);
                }
            }

        });
    }

    private void add_data(String title, String date, String time) {
        String result = new dbManager(this).addreminder(title, date, time);
        setAlarm(title,date,time);
        titleTxt.setText("");
        Toast.makeText(this, "Reminder added successfully", Toast.LENGTH_SHORT).show();
    }

    private void time_select() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                time_noti = i + ":" + i1;
                addTime.setText(time_formatter(i, i1));
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
    private void date_select() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                addDate.setText(day + "-" + (month + 1) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String time_formatter(int hour, int minute) {
        String time;
        time = "";
        String formattedMinute;
        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }
        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;
    }

private void setAlarm(String head,String date,String time)
{
    AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent intent=new Intent(getApplicationContext(),broadcast.class);
    intent.putExtra("Event",head);
    intent.putExtra("Time",time);
    intent.putExtra("Date",date);

    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
    String datetime = date + " " + time_noti;
    DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
    try {
        Date date1 = formatter.parse(datetime);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm", Toast.LENGTH_SHORT).show();

    } catch (ParseException e) {
        e.printStackTrace();
    }

    Intent intentBack = new Intent(getApplicationContext(), MainActivity.class);
    intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intentBack);

}
}







