package edu.bridgewatercs.jscott.eagleassistant;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

public class AppSettings extends AppCompatPreferenceActivity {

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            }
            else preference.setSummary(stringValue);
            return true;
        }
    };

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }
    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new GeneralPreferenceFragment()).commit();
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }
    protected boolean isValidFragment(String fragmentName) {
        return GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }





    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            //User info
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenEditTextUserName"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenListOccupation"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenEditTextOccupation"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenEditTextBio"));


            //Time and Date
            SwitchPreference timeFormat = (SwitchPreference) findPreference("appSettingsScreenSwitchTimeFormat");
            SwitchPreference dateFormat = (SwitchPreference) findPreference("appSettingsScreenSwitchDateFormat");

            //Background
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenListBackground"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenListBackgroundColor"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenEditTextCustomBackgroundColor"));
            ListPreference backgroundChoice = (ListPreference) findPreference("appSettingsScreenListBackground");
            ListPreference backgroundColor = (ListPreference) findPreference("appSettingsScreenListBackgroundColor");
            EditTextPreference backgroundCustomColor = (EditTextPreference) findPreference("appSettingsScreenEditTextCustomBackgroundColor");

            //Text
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenListTextColor"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenEditTextCustomTextColor"));
            ListPreference textColor = (ListPreference) findPreference("appSettingsScreenListTextColor");
            EditTextPreference textCustomColor = (EditTextPreference) findPreference("appSettingsScreenEditTextCustomTextColor");

            //Button
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenListButtonColor"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenEditTextCustomButtonColor"));
            ListPreference buttonColor = (ListPreference) findPreference("appSettingsScreenListButtonColor");
            EditTextPreference buttonCustomColor = (EditTextPreference) findPreference("appSettingsScreenEditTextCustomButtonColor");

            //Button Text
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenListButtonTextColor"));
            bindPreferenceSummaryToValue(findPreference("appSettingsScreenEditTextCustomButtonTextColor"));
            ListPreference buttonTextColor = (ListPreference) findPreference("appSettingsScreenListButtonTextColor");
            EditTextPreference buttonTextCustomColor = (EditTextPreference) findPreference("appSettingsScreenEditTextCustomButtonTextColor");

            //Map
            SwitchPreference showAcademicBuildingMapMarkers = (SwitchPreference) findPreference("appSettingsScreenSwitchShowAcademicBuildingMapMarkers");
            SwitchPreference showResidentHallMapMarkers = (SwitchPreference) findPreference("appSettingsScreenSwitchShowResidentHallMapMarkers");
            SwitchPreference showOtherBuildingMapMarkers = (SwitchPreference) findPreference("appSettingsScreenSwitchShowOtherBuildingMapMarkers");
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), AppSettings.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
