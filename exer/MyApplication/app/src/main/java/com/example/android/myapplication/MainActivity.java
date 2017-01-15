package com.example.android.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.myapplication.data.dataContract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView rv_showdata;
    dataAdapter mAdapter;
    EditText mWishText;
    private final static int LOADER_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_showdata = (RecyclerView)findViewById(R.id.rv_list);
        mWishText = (EditText)findViewById(R.id.et_wish);

        rv_showdata.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new dataAdapter(this);
        rv_showdata.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
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
        String lotto ="1-2-3-4-5-6";
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
}
