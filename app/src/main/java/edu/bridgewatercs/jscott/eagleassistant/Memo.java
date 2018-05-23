package edu.bridgewatercs.jscott.eagleassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memo extends AppCompatActivity {
    ListView memoListView;
    SimpleAdapter memoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        //makes buttons that go to various screens
        Button memoScreenToMainButton = findViewById(R.id.memoScreenMainButton);
        Button memoScreenToNewMemoButton = findViewById(R.id.memoScreenNewMemoButton);

        //makes intents that help buttons go to various screens
        final Intent memoScreenToMainIntent = new Intent (this, MainActivity.class);
        final Intent memoScreenToNewMemoIntent = new Intent (this, NewMemo.class);

        //makes buttons clickable, sending them to their respective screens
        memoScreenToMainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(memoScreenToMainIntent);
            }
        });
        memoScreenToNewMemoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(memoScreenToNewMemoIntent);
            }
        });

        //makes list view and arrays of memo pieces that appear in the view
        memoListView = findViewById(R.id.memoScreenListOfMemos);
        ArrayList<String> arrayOfSubjects = new ArrayList<>();
        ArrayList<String> arrayOfContent = new ArrayList<>();

        //brings in a brand new memo
        String newMemoSubject = getIntent().getStringExtra("newMemoSubject");
        String newMemoContent = getIntent().getStringExtra("newMemoContent");

        /*load past memos*/
        SharedPreferences loadArrays = PreferenceManager.getDefaultSharedPreferences(this);
        arrayOfSubjects.clear();
        arrayOfContent.clear();
        int loadSize = loadArrays.getInt("ArraySize", 0);
        if(loadSize > 0) {
            for(int i =  0; i < loadSize; i++) {
                arrayOfSubjects.add(loadArrays.getString("Subject_" + i, null));
                arrayOfContent.add(loadArrays.getString("Content_" + i, null));
            }
        }
        //*/

        //if there was a brand new memo, add it
        if(newMemoSubject != null) {
            arrayOfSubjects.add(newMemoSubject);
            arrayOfContent.add(newMemoContent);
        }

        //make array adapter, which will appear on screen
        //ArrayAdapter<String> memoListAdapter = new ArrayAdapter<String>(this, android.R.layout.two_line_list_item, android.R.id.text1, arrayOfSubjects);
        List<Map<String, String>> memoListMap = new ArrayList<>();
        for(int i = 0; i < arrayOfSubjects.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("Subject", arrayOfSubjects.get(i));
            map.put("Content", arrayOfContent.get(i));
            memoListMap.add(map);
        }
        memoAdapter = new SimpleAdapter(this, memoListMap, android.R.layout.simple_list_item_2, new String[] {"Subject", "Content"}, new int[] {android.R.id.text1, android.R.id.text2});
        memoListView.setAdapter(memoAdapter);

        //saves the arrays so it re-appears
        SharedPreferences saveArrays  = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = saveArrays.edit();
        editor.putInt("ArraySize", arrayOfSubjects.size());
        for(int i = 0; i < arrayOfSubjects.size(); i++) {
            editor.remove("Subject_" + i);
            editor.remove("Content_" + i);
            editor.putString("Subject_" + i, arrayOfSubjects.get(i));
            editor.putString("Content_" + i, arrayOfContent.get(i));
        }

        editor.apply();
        //editor.clear().apply();
    }
}
