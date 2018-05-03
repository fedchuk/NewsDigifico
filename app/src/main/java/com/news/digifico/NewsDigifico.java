package com.news.digifico;

import android.app.Application;
import android.content.Context;

import com.news.digifico.db.NewsDatabase;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class NewsDigifico extends Application {
    private static NewsDigifico instance;
    private NewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = NewsDatabase.getInstance(this);
    }

    public static Context getAppContext(){
        return instance;
    }

     public static NewsDigifico getInstance(){
        return instance;
     }

     public NewsDatabase getDatabese(){
         return database;
     }
}
