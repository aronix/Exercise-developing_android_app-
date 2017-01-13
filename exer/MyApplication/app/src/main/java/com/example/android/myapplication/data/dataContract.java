package com.example.android.myapplication.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by han sb on 2017-01-14.
 */

public class dataContract
{
    public static final String CONTENT_AUTHORITY = "com.example.android.myapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WISH = "wish";

    public static final class WishEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WISH)
                .build();

        public static final String TABLE_NAME = "wishData";
        public static final String COLUMN_DATE ="date";
        public static final String COLUMN_WISH ="wish";
        public static final String COLUMN_LOTTO="lotto";

    }
}
