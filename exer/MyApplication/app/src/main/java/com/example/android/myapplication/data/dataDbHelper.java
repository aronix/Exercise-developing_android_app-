package com.example.android.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by han sb on 2017-01-14.
 */

public class dataDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "wishlotto.db";
    private static final int DATABASE_VERSION =3;

    public dataDbHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_DATA_TABLE=
                "CREATE TABLE " +dataContract.WishEntry.TABLE_NAME+" ("+
                        dataContract.WishEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        dataContract.WishEntry.COLUMN_DATE+" TEXT NOT NULL, "+
                        dataContract.WishEntry.COLUMN_WISH+" TEXT NOT NULL, "+
                        dataContract.WishEntry.COLUMN_LOTTO+" TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_DATA_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ dataContract.WishEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
