package com.example.triviaapp.DrawerOptions;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.triviaapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.shared_pref);
    }
}
