package com.news.digifico.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.news.digifico.AppSingleton;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private static int countOfCallDuringLackOfInternet = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isConnect(context)){
            AppSingleton.getInstance().setLiveData(true);
            countOfCallDuringLackOfInternet = 0;
        } else {
            if (countOfCallDuringLackOfInternet < 1){
                AppSingleton.getInstance().setLiveData(false);
                countOfCallDuringLackOfInternet++;
            }
        }
    }

    private boolean isConnect(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
