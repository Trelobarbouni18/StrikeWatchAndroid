package com.nestoras.strikewatch;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class AddStrike extends AppCompatActivity {

    private EditText aDateText;
    private EditText aTimeText;
    private EditText locationText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button addButton;
    private EditText strikeName;
    private EditText timeText;
    public ArrayList<Strike> stikesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_strike);

        aDateText = findViewById(R.id.dateText);
        aTimeText = findViewById(R.id.timeText);
        addButton = findViewById(R.id.addButton);
        strikeName = findViewById(R.id.strikeName);
        timeText = findViewById(R.id.timeText);
        locationText = findViewById(R.id.locationId);

        aDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddStrike.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        aTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStrike.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        aTimeText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                aDateText.setText(date);
            }
        };

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aStrikeName =  strikeName.getText().toString();
                String atimeText = timeText.getText().toString();
                String dateText = aDateText.getText().toString();
                String alocationText = locationText.getText().toString();

                Strike newStrike = new Strike(aStrikeName, dateText, atimeText ,alocationText);


                Intent intent = new Intent(AddStrike.this, StrikeList.class);
                intent.putExtra("Strike", newStrike);
                startActivity(intent);
            }
        });
    }
}
