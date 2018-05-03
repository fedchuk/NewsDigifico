package com.news.digifico.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class CommonUtils {

    public static boolean isNetworkConnected(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
