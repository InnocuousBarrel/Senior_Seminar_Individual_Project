package edu.bridgewatercs.jscott.eagleassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class Schedule extends AppCompatActivity {
    ListView eventListView;
    ListView eventListView1;
    ListView eventListView2;
    View clickSource;
    View touchSource;

    int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //makes buttons that go to various screens
        Button scheduleScreenToMainButton = findViewById(R.id.scheduleScreenMainButton);
        Button scheduleScreenToNewEventButton = findViewById(R.id.scheduleScreenNewEventButton);

        //makes intents that help buttons go to various screens
        final Intent scheduleScreenToMainIntent = new Intent (this, MainActivity.class);
        final Intent scheduleScreenToNewEventIntent = new Intent (this, NewEvent.class);

        //makes buttons clickable, sending them to their respective screens
        scheduleScreenToMainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(scheduleScreenToMainIntent);
            }
        });
        scheduleScreenToNewEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(scheduleScreenToNewEventIntent);
            }
        });

        //makes list views, makes them scroll together, and makes arrays of event pieces that appear in the views
        eventListView = findViewById(R.id.eventScreenListOfEvents);
        eventListView1 = findViewById(R.id.eventScreenListOfEvents1);
        eventListView2 = findViewById(R.id.eventScreenListOfEvents2);
        makeListViewsScrollTogether(eventListView, eventListView1, eventListView2);
        makeListViewsScrollTogether(eventListView1, eventListView2, eventListView);
        makeListViewsScrollTogether(eventListView2, eventListView, eventListView1);
        ArrayList<String> arrayOfEventNames = new ArrayList<>();
        ArrayList<String> arrayOfEventTimes = new ArrayList<>();
        ArrayList<String> arrayOfEventDays = new ArrayList<>();

        /*tests ability of the list views to work together without having to make 15 events
        for(int i = 0; i < 15; i++) {
            arrayOfEventNames.add("Item " + i);
            arrayOfEventTimes.add("Item1 " + i);
            arrayOfEventDays.add("Item2" + i);;
        }
        //*/

        //brings in and forms a brand new event
        String eventTime = "";
        String eventName = getIntent().getStringExtra("newEventName");
        String eventStartTime = getIntent().getStringExtra("newEventStartTime");
        String eventEndTime = getIntent().getStringExtra("newEventEndTime");
            if(eventName != null) eventTime = eventStartTime + " - " + eventEndTime;
        String eventDays = getIntent().getStringExtra("newEventDays");

        /*load past memos*/
        SharedPreferences loadArray = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences loadArray1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences loadArray2 = PreferenceManager.getDefaultSharedPreferences(this);
        arrayOfEventNames.clear();
        arrayOfEventTimes.clear();
        arrayOfEventDays.clear();
        int loadSize = loadArray.getInt("eventName_Size", 0);
        if(loadSize > 0) {
            for(int i =  0; i < loadSize; i++) {
                arrayOfEventNames.add(loadArray.getString("EventName_" + i, null));
                arrayOfEventTimes.add(loadArray1.getString("EventTime_" + i, null));
                arrayOfEventDays.add(loadArray2.getString("EventDay_" + i, null));
            }
        }
        //*/

        /*if there was a brand new event, add it*/
        if(eventName != null) {
            arrayOfEventNames.add(eventName);
            arrayOfEventTimes.add(eventTime);
            arrayOfEventDays.add(eventDays);
        }

        /*Makes array appear on screen in the listview*/
        ArrayAdapter<String> eventListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayOfEventNames);
        ArrayAdapter<String> eventListAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayOfEventTimes);
        ArrayAdapter<String> eventListAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayOfEventDays);
        eventListView.setAdapter(eventListAdapter);
        eventListView1.setAdapter(eventListAdapter1);
        eventListView2.setAdapter(eventListAdapter2);

        /*saves the array so it appears when user returns to listview*/
        SharedPreferences saveArray = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences saveArray1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences saveArray2 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = saveArray.edit();
        SharedPreferences.Editor editor1 = saveArray1.edit();
        SharedPreferences.Editor editor2 = saveArray2.edit();
        editor.putInt("eventName_Size", arrayOfEventNames.size());
        editor1.putInt("eventTime_Size", arrayOfEventTimes.size());
        editor2.putInt("eventDay_Size", arrayOfEventDays.size());
        for(int i = 0; i < arrayOfEventNames.size(); i++) {
            editor.remove("EventName_" + i);
            editor.putString("EventName_" + i, arrayOfEventNames.get(i));
            editor1.remove("EventTime_" + i);
            editor1.putString("EventTime_" + i, arrayOfEventTimes.get(i));
            editor2.remove("EventDay_" + i);
            editor2.putString("EventDay_" + i, arrayOfEventDays.get(i));
        }
        //*/

        /*editors apply the information, perma saving*/
        editor.apply();
        editor1.apply();
        editor2.apply();
        //editor.clear().apply();
        //editor1.clear().apply();
        //editor2.clear().apply();
        //*/
    }

    public void makeListViewsScrollTogether(ListView listView, final ListView listView1, final ListView listView2) {
        listView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(touchSource == null)
                    touchSource = v;

                if(v == touchSource) {
                    listView1.dispatchTouchEvent(event);
                    listView2.dispatchTouchEvent(event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        clickSource = v;
                        touchSource = null;
                    }
                }

                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent == clickSource) {
                    // Do something with the ListView was clicked
                }
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view == clickSource) {
                    listView1.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop() + offset);
                    listView2.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop() + offset);
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
        });
    }
}
