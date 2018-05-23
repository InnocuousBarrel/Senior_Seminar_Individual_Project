package edu.bridgewatercs.jscott.eagleassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class NewEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        //make necessary widgets and components
        final EditText newEventScreenNameEditText = findViewById(R.id.newEventScreenEditTextEventName);
        final TimePicker newEventScreenStartTimePicker = findViewById(R.id.newEventScreenStartTimePicker);
        final TimePicker newEventScreenEndTimePicker = findViewById(R.id.newEventScreenEndTimePicker);
        final ToggleButton newEventScreenMondayToggleButton = findViewById(R.id.newEventScreenMondayToggleButton);
        final ToggleButton newEventScreenTuesdayToggleButton = findViewById(R.id.newEventScreenTuesdayToggleButton);
        final ToggleButton newEventScreenWednesdayToggleButton = findViewById(R.id.newEventScreenWednesdayToggleButton);
        final ToggleButton newEventScreenThursdayToggleButton = findViewById(R.id.newEventScreenThursdayToggleButton);
        final ToggleButton newEventScreenFridayToggleButton = findViewById(R.id.newEventScreenFridayToggleButton);
        Button newEventScreenCancelButton = findViewById(R.id.newEventScreenCancelEvent);
        Button newEventScreenOkButton = findViewById(R.id.newEventScreenOkEvent);

        //make intent that will return to schedule; happens regardless
        final Intent newEventScreenReturnToSchedule = new Intent(this, Schedule.class);

        //cancels new event and returns to schedule
        newEventScreenCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(newEventScreenReturnToSchedule);
            }
        });

        //accepts event info and returns to schedule
        newEventScreenOkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String eventName = newEventScreenNameEditText.getText().toString();
                String eventStartTime = getEvent_____Time(newEventScreenStartTimePicker);
                String eventEndTime = getEvent_____Time(newEventScreenEndTimePicker);
                String eventDays = eventDay(newEventScreenMondayToggleButton) + eventDay(newEventScreenTuesdayToggleButton)
                        + eventDay(newEventScreenWednesdayToggleButton) + eventDay(newEventScreenThursdayToggleButton)
                        + eventDay(newEventScreenFridayToggleButton);

                newEventScreenReturnToSchedule.putExtra("newEventName", eventName);
                newEventScreenReturnToSchedule.putExtra("newEventStartTime", eventStartTime);
                newEventScreenReturnToSchedule.putExtra("newEventEndTime", eventEndTime);
                newEventScreenReturnToSchedule.putExtra("newEventDays", eventDays);

                SharedPreferences saveNewEvent  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = saveNewEvent.edit();

                editor.putString("newEventName", eventName);
                editor.putString("newEventStartTime", eventStartTime);
                editor.putString("newEventEndTime", eventEndTime);
                editor.putString("newEventDays", eventDays);
                editor.apply();

                startActivity(newEventScreenReturnToSchedule);
            }
        });
    }

    //makes the text for the event times to be prettier
    public String getEvent_____Time(TimePicker time_picker) {

        String amPM = "AM";
        int eventHour = time_picker.getHour();
        if (eventHour > 12) {
            eventHour = eventHour - 12;
            amPM = "PM";
        }
        if (eventHour == 0) eventHour = 12;

        String eventMinute = "min";
        int eventMin = time_picker.getMinute();
        if (eventMin < 10) eventMinute = "0" + eventMin;
        else eventMinute = "" + eventMin;

        String time = eventHour + ":" + eventMinute + " " + amPM;
        return time;
    }

    //creates the M, T, W, R, F seen on the Schedule screen when user picks the event's day(s)
    public String eventDay(ToggleButton tb) {
        String days = "";
        if (tb.isChecked()) days = tb.getTextOn().toString();
        return days;
    }
}
