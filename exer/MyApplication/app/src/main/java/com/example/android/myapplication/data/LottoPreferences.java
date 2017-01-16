package com.example.android.myapplication.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;

/**
 * Created by han sb on 2017-01-16.
 */

public class LottoPreferences {

    public static final String PREF_LOTTO_RESULT = "lotto_result";

    public static void setLottoPreference(Context context,String result){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_LOTTO_RESULT,result);
    }


}
