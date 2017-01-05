package android.example.com.visualizerpreferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by han sb on 2017-01-06.
 */

public class SettingsFragment extends PreferenceFragmentCompat{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //onCreate 함수를 만들었으니 XML preference file  을 만들어보자.
        addPreferencesFromResource(R.xml.pref_visualizer);
    }
}
