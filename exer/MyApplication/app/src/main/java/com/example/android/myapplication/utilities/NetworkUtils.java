package com.example.android.myapplication.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by han sb on 2017-01-16.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String LOTTO_URL = "http://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo=";
    //뒤에 회차 붙이면 해당 회차 아무것도 안 붙이면 가장 최신.


    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if(hasInput){
                response = scanner.next();
            }
            scanner.close();
            return response;

        }finally {
            urlConnection.disconnect();
        }
    }

}
