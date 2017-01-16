package com.example.android.myapplication.sync;

import android.content.Context;
import android.util.Log;

import com.example.android.myapplication.data.LottoPreferences;
import com.example.android.myapplication.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by han sb on 2017-01-16.
 */

public class LottoSyncTask {
    synchronized public static void syncLotto(Context context){
        try{
            URL url = new URL(NetworkUtils.LOTTO_URL);

            String result = NetworkUtils.getResponseFromHttpUrl(url);
            Log.d("LOG",result.toString());
            LottoPreferences.setLottoPreference(context,result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
