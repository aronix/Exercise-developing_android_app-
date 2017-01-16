package com.example.android.myapplication.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by han sb on 2017-01-16.
 */

public class LottoIntentService extends IntentService {
    public LottoIntentService(){
        super(LottoIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LottoSyncTask.syncLotto(this);
    }
}
