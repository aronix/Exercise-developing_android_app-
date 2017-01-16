package com.example.android.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by han sb on 2017-01-14.
 */

public class dataProvider extends ContentProvider{
    public static final int CODE_WISHS = 100;
    public static final int CODE_WISH =101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    dataDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {

        /*
         * All paths added to the UriMatcher have a corresponding code to return when a match is
         * found. The code passed into the constructor of UriMatcher here represents the code to
         * return for the root URI. It's common to use NO_MATCH as the code for this case.
         */
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = dataContract.CONTENT_AUTHORITY;

        /*
         * For each type of URI you want to add, create a corresponding code. Preferably, these are
         * constant fields in your class so that you can use them throughout the class and you no
         * they aren't going to change. In Sunshine, we use CODE_WEATHER or CODE_WEATHER_WITH_DATE.
         */

        /* This URI is content://com.example.android.sunshine/weather/ */
        matcher.addURI(authority, dataContract.PATH_WISH, CODE_WISHS);
        matcher.addURI(authority, dataContract.PATH_WISH+"/#", CODE_WISH);

        /*
         * This URI would look something like content://com.example.android.sunshine/weather/1472214172
         * The "/#" signifies to the UriMatcher that if PATH_WEATHER is followed by ANY number,
         * that it should return the CODE_WEATHER_WITH_DATE code
         */


        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new dataDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case CODE_WISHS:
                cursor = mOpenHelper.getReadableDatabase().query(
                        dataContract.WishEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);
                return cursor;

            default:
              return null;
        }


    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
       final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)){
            case CODE_WISHS:
                long id = db.insert(dataContract.WishEntry.TABLE_NAME,null,contentValues);
                if(id>0) {
                    returnUri = ContentUris.withAppendedId(dataContract.WishEntry.CONTENT_URI, id);
                    Toast.makeText(getContext(), returnUri.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                    throw new android.database.SQLException("failed to insert row into "+uri);
                break;
            default:
                return null;

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        switch (sUriMatcher.match(uri)){
            case CODE_WISH:
                String id = uri.getLastPathSegment();
                SQLiteDatabase db = mOpenHelper.getWritableDatabase();
                int delnum = db.delete(dataContract.WishEntry.TABLE_NAME,dataContract.WishEntry._ID+"="+id,null);
                Toast.makeText(getContext(),"DEL: "+uri.toString(),Toast.LENGTH_SHORT).show();
                return delnum;
            default:
                throw new UnsupportedOperationException("unknown uri="+uri);
        }


    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
