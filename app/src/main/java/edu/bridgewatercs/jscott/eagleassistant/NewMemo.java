package edu.bridgewatercs.jscott.eagleassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;

import org.w3c.dom.Text;

import java.util.Calendar;

public class NewMemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);

        //make info for the date text
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int amPM = cal.get(Calendar.AM_PM);

        //makes the created time look better on the memo screen
        String newAMPM = "AMPM";
        if(amPM == 0) newAMPM = "AM";
        else newAMPM = "PM";
        String newMIN = "00";
        if(min < 10) newMIN = "0" + min;
        else newMIN = "" + min;

        //string that shows user when exactly the memo was created
        final String newMemoCreationTime = month + "/" + day + "/" + year + " @ " + hour + ":" + newMIN + " " + newAMPM;

        //make cancel and ok buttons
        Button newMemoScreenCancelButton = findViewById(R.id.newMemoScreenCancelMemo);
        Button newMemoScreenOkButton = findViewById(R.id.newMemoScreenOkMemo);

        //make intent that will return to memo screen; happens regardless
        final Intent newMemoScreenReturnToMemo = new Intent(this, Memo.class);

        //cancels new memo
        newMemoScreenCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(newMemoScreenReturnToMemo);
            }
        });

        //accepts memo and gives info to memo screen
        newMemoScreenOkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText newMemoScreenEditTextSubject = findViewById(R.id.newMemoScreenEditTextSubject);
                EditText newMemoScreenEditTextContent = findViewById(R.id.newMemoScreenEditTextContent);

                String subjectOfNewMemo = newMemoScreenEditTextSubject.getText().toString() + "                              " + newMemoCreationTime;
                String contentOfNewMemo = newMemoScreenEditTextContent.getText().toString();

                newMemoScreenReturnToMemo.putExtra("newMemoSubject", subjectOfNewMemo);
                newMemoScreenReturnToMemo.putExtra("newMemoContent", contentOfNewMemo);
                startActivity(newMemoScreenReturnToMemo);
            }
        });
    }
}
