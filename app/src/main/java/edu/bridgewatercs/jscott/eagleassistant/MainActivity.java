package edu.bridgewatercs.jscott.eagleassistant;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //makes text clock and text date
        TextClock textClockTime = findViewById(R.id.mainScreenTextClockTime);
        TextClock textClockDate = findViewById(R.id.mainScreenTextClockDate);

        //makes buttons that go to various screens
        Button mainScreenToScheduleButton = findViewById(R.id.mainScreenScheduleButton);
        Button mainScreenToMemoButton = findViewById(R.id.mainScreenMemoButton);
        Button mainScreenToMapButton = findViewById(R.id.mainScreenMapButton);
        Button mainScreenToSettingsButton = findViewById(R.id.mainScreenSettingsButton);

        //makes intents that help buttons go to various screens
        final Intent mainScreenToScheduleIntent = new Intent (this, Schedule.class);
        final Intent mainScreenToMemoIntent = new Intent (this, Memo.class);
        final Intent mainScreenToMapIntent = new Intent (this, Maps.class);
        final Intent mainScreenToSettingsIntent = new Intent (this, AppSettings.class);

        //makes buttons clickable, sending them to their respective screens
        mainScreenToScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainScreenToScheduleIntent);
            }
        });
        mainScreenToMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mainScreenToMemoIntent.putExtra("newMemoSubject", "Test Memo     -     5/3/2018 @ 6:20 AM");
                //mainScreenToMemoIntent.putExtra("newMemoContent", "This is a test memo. This will check to see if the memo class works. This next sentence is to test the multiple line idea." +
                        //" This may go on for a little bit longer, but this is only to see if the adapter does word wrap or if it makes me upset.");
                startActivity(mainScreenToMemoIntent);
            }
        });
        mainScreenToMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mainScreenToMapIntent);
            }
        });
        mainScreenToSettingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mainScreenToSettingsIntent);
            }
        });

    }

    protected void onResume() {
        super.onResume();

        /*Makes a calendar*/
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);

        /*Makes usable data for RGB Clock*/
        String rgbHour = "" + hour;
        String rgbMin = "" + min;
        String rgbSec = "" + sec;
        String rgbClock = "#";
        if(hour < 10) rgbHour = "0" + rgbHour;
        if(min < 10) rgbMin = "0" + rgbMin;
        if(sec < 10) rgbSec = "0" + rgbSec;
        rgbClock = rgbClock + rgbHour + rgbMin + rgbSec;


        /*Begin bringing in Shared Preferences from AppSettings*/
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        //Time and Date
        boolean timeFormat = settings.getBoolean("appSettingsScreenSwitchTimeFormat", true);
        boolean dateFormat = settings.getBoolean("appSettingsScreenSwitchDateFormat", true);
        //Background
        String backgroundChoice = settings.getString("appSettingsScreenListBackground", "BC Wallpaper");
        String backgroundColor = settings.getString("appSettingsScreenListBackgroundColor", "FFFFFF");
        String backgroundCustomColor = settings.getString("appSettingsScreenEditTextCustomBackgroundColor", "FFFFFF");
        //Text
        String textColor = settings.getString("appSettingsScreenListTextColor", "D7C5A1");
        String textCustomColor = settings.getString("appSettingsScreenEditTextCustomTextColor", "FFFFFF");
        //Button
        String buttonColor = settings.getString("appSettingsScreenListButtonColor", "D7C5A1");
        String buttonCustomColor = settings.getString("appSettingsScreenEditTextCustomButtonColor", "FFFFFF");
        //Button Text
        String buttonTextColor = settings.getString("appSettingsScreenListButtonTextColor", "B3093A");
        String buttonTextCustomColor = settings.getString("appSettingsScreenEditTextCustomButtonTextColor", "FFFFFF");



        /*Time and Date Format*/
        TextClock textClockTime = findViewById(R.id.mainScreenTextClockTime);
        TextClock textClockDate = findViewById(R.id.mainScreenTextClockDate);
        //Time
        if(timeFormat) textClockTime.setFormat12Hour("h:mm a");
        if(!timeFormat) textClockTime.setFormat12Hour("h:mm:ss a");
        //Date
        if(dateFormat) textClockDate.setFormat12Hour("MM/dd/yyyy");
        if(!dateFormat) textClockDate.setFormat12Hour("MMM dd, yyyy");



        /*Helpers that allow for parse color to work*/
        //Background
        String backgroundColorHelper = "#" + backgroundColor;
        String backgroundCustomColorHelper = "#" + backgroundCustomColor;
        //Text
        String textColorHelper = "#" + textColor;
        String textCustomColorHelper = "#" + textCustomColor;
        //Button
        String buttonColorHelper = "#" + buttonColor;
        String buttonCustomColorHelper = "#" + buttonCustomColor;
        //Button Text
        String buttonTextColorHelper = "#" + buttonTextColor;
        String buttonTextCustomColorHelper = "#" + buttonTextCustomColor;



        /*You must first onCreate the buttons, then onResume them to alter them*/
        Button mainScreenToScheduleButton = findViewById(R.id.mainScreenScheduleButton);
        Button mainScreenToMemoButton = findViewById(R.id.mainScreenMemoButton);
        Button mainScreenToMapButton = findViewById(R.id.mainScreenMapButton);
        Button mainScreenToSettingsButton = findViewById(R.id.mainScreenSettingsButton);



        /*Captures the desired colors*/
        //Background
        int backgroundColorSelection;
        boolean bcWallpaper = false;
        if((backgroundChoice.equals("Color")) && (backgroundColor.equals("Custom"))) backgroundColorSelection = Color.parseColor(backgroundCustomColorHelper);
        else if ((backgroundChoice.equals("Color")) && !(backgroundColor.equals("Custom"))) backgroundColorSelection = Color.parseColor(backgroundColorHelper);
        else if (backgroundChoice.equals("RGB Clock")) backgroundColorSelection = Color.parseColor(rgbClock);
        else {
            backgroundColorSelection = Color.BLACK;
            bcWallpaper = true;
        }
        //Text
        int textColorSelection;
        if(textColor.equals("Custom")) textColorSelection = Color.parseColor(textCustomColorHelper);
        else textColorSelection = Color.parseColor(textColorHelper);
        //Button
        int buttonColorSelection;
        if(buttonColor.equals("Custom")) buttonColorSelection = Color.parseColor(buttonCustomColorHelper);
        else buttonColorSelection = Color.parseColor(buttonColorHelper);
        //Button Text
        int buttonTextColorSelection;
        if(buttonTextColor.equals("Custom")) buttonTextColorSelection = Color.parseColor(buttonTextCustomColorHelper);
        else buttonTextColorSelection = Color.parseColor(buttonTextColorHelper);



        /*Changes Colors*/
        LinearLayout eagleAssistantHomeLinearLayout = findViewById(R.id.activity_main);
        //Background
        if(!bcWallpaper) eagleAssistantHomeLinearLayout.setBackgroundColor(backgroundColorSelection);
        else eagleAssistantHomeLinearLayout.setBackgroundResource(R.drawable.bridgewater_disney_background);
        //Text
        textClockTime.setTextColor(textColorSelection);
        textClockDate.setTextColor(textColorSelection);
        //Button
        mainScreenToScheduleButton.setBackgroundColor(buttonColorSelection);
        mainScreenToMemoButton.setBackgroundColor(buttonColorSelection);
        mainScreenToMapButton.setBackgroundColor(buttonColorSelection);
        mainScreenToSettingsButton.setBackgroundColor(buttonColorSelection);
        //Button Text
        mainScreenToScheduleButton.setTextColor(buttonTextColorSelection);
        mainScreenToMemoButton.setTextColor(buttonTextColorSelection);
        mainScreenToMapButton.setTextColor(buttonTextColorSelection);
        mainScreenToSettingsButton.setTextColor(buttonTextColorSelection);
        //
        ActionBar bar = getActionBar();
        if(bar != null) bar.setBackgroundDrawable(new ColorDrawable(backgroundColorSelection));
    }
}
