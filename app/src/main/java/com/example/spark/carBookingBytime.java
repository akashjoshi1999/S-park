package com.example.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class carBookingBytime extends AppCompatActivity {

    private TextView textViewStartTime,textViewEndTime,TextViewStartDate,TextViewEndDate,textViewFinalBook;
    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    public String date,date2;
    String AmPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking_bytime);

        textViewStartTime = (TextView) findViewById(R.id.textViewSetStartTime);
        textViewEndTime = (TextView) findViewById(R.id.textViewSetEndTime);
        TextViewStartDate = (TextView) findViewById(R.id.textViewSetStartDate);
        TextViewEndDate = (TextView) findViewById(R.id.textViewSetEndDate);
        textViewFinalBook = (TextView) findViewById(R.id.textViewFinalBook);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        carBookingBytime.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                        if(hourOfDay >= 12){
                            AmPm = "PM";
                        } else {
                            AmPm = "AM";
                        }
                        textViewStartTime.setText(String.format("%2d:%2d",hourOfDay,minutes)+AmPm);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        textViewEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        carBookingBytime.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                        if(hourOfDay >= 12){
                            AmPm = "PM";
                        } else {
                            AmPm = "AM";
                        }
                        textViewEndTime.setText(String.format("%2d:%2d",hourOfDay,minutes)+AmPm);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        TextViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        carBookingBytime.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        date= day+"/"+ month +"/" +year;
                        TextViewStartDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        TextViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        carBookingBytime.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        date2= day+"/"+ month +"/" +year;
                        TextViewEndDate.setText(date2);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        textViewFinalBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
