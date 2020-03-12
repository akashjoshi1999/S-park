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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class carBookingBytime extends AppCompatActivity {

    private TextView textViewStartTime,textViewEndTime,TextViewStartDate,TextViewEndDate,textViewFinalBook;
    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    public String date1,date2,time1,time2;
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
                        time1 = "%2d:%2d"+hourOfDay+minutes + AmPm;
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
                        time2 = "%2d:%2d"+hourOfDay+minutes+AmPm;
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
                        date1= day+"/"+ month +"/" +year;
                        TextViewStartDate.setText(date1);
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
                try {

                    String format = "MM/dd/yyyy hh:mm a";

                    SimpleDateFormat sdf = new SimpleDateFormat(format);

                    Date dateObj1 = sdf.parse(date1 + " " + time1);
                    Date dateObj2 = sdf.parse(date2 + " " + time2);
                    System.out.println(dateObj1);
                    System.out.println(dateObj2 + "\n");

                    DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

                    // getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
                    long diff = dateObj2.getTime() - dateObj1.getTime();

                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    int diffhours = (int) (diff / (60 * 60 * 1000));
                    String bookHourDiff = crunchifyFormatter.format(diffhours);
                    int diffmin = (int) (diff / (60 * 1000));
                    String bookMinuteDiff = crunchifyFormatter.format(diffmin);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
