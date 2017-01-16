package com.example.android.myapplication;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.myapplication.data.LottoPreferences;
import com.example.android.myapplication.data.dataContract;
import com.example.android.myapplication.sync.LottoIntentService;
import com.example.android.myapplication.sync.LottoSyncTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        SharedPreferences.OnSharedPreferenceChangeListener{

    RecyclerView rv_showdata;
    dataAdapter mAdapter;
    EditText mWishText;
    TextView mAPIView;
    private final static int LOADER_ID=1;
    private final static int LOADER_GETREQUEST=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_showdata = (RecyclerView)findViewById(R.id.rv_list);
        mWishText = (EditText)findViewById(R.id.et_wish);
        mAPIView = (TextView)findViewById(R.id.lottoAPI_reulst);
        rv_showdata.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new dataAdapter(this);
        rv_showdata.setAdapter(mAdapter);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int)viewHolder.itemView.getTag();
                String stringId = Integer.toString(id);
                Uri uri = dataContract.WishEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
                getContentResolver().delete(uri,null,null);
                getSupportLoaderManager().restartLoader(LOADER_ID,null,MainActivity.this);
            }
        }).attachToRecyclerView(rv_showdata);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id){
            case LOADER_ID:
                return new CursorLoader(this,
                        dataContract.WishEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);


        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.SwapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.SwapCursor(null);
    }

    public void onclickBtn(View view) {
        String wish = mWishText.getText().toString().trim();
        if(wish.length()==0){
            return;
        }
        String lotto =getLottoNum(wish);
        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


        ContentValues contentValues = new ContentValues();
        Log.d("TAG",sdf.format(d).toString());
       // contentValues.put(dataContract.WishEntry.COLUMN_DATE,"123");
        contentValues.put(dataContract.WishEntry.COLUMN_DATE,sdf.format(d).toString());
        contentValues.put(dataContract.WishEntry.COLUMN_WISH,wish);
        contentValues.put(dataContract.WishEntry.COLUMN_LOTTO,lotto);
        getContentResolver().insert(dataContract.WishEntry.CONTENT_URI,contentValues);

    }

    public String getLottoNum(String wishStr) {
        int num[] = new int[6];
        Random r = new Random(wishStr.length());

        for(int i=0;i<6;i++){
            num[i] = r.nextInt(45)+1;
            for(int j=0;j<i;j++){
                if(num[i] == num[j]){
                    i--;
                    break;
                }
            }
        }
        String returnStr ="";

        Arrays.sort(num);

        for(int i=0;i<6;i++){
            if(i==5)returnStr = returnStr + num[i];
            else   returnStr = returnStr + num[i] +"-";
        }
        return returnStr;

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(LottoPreferences.PREF_LOTTO_RESULT)){
            mAPIView.setText(sharedPreferences.getString(key,"null"));
        }
    }

    public void startServiceTest(View view) {
        Intent getLottoIntent = new Intent(this, LottoIntentService.class);
        startService(getLottoIntent);
    }
}
