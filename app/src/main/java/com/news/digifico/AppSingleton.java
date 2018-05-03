package com.news.digifico;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class AppSingleton {
    private static AppSingleton singleton;
    private MutableLiveData<Boolean> liveData = new MutableLiveData<>();

    private AppSingleton() {
    }

    public static AppSingleton getInstance (){
        if (singleton == null){
            singleton = new AppSingleton();
        }
        return singleton;
    }

    public LiveData<Boolean> getLiveData() {
        return liveData;
    }

    public void setLiveData(Boolean value) {
        this.liveData.setValue(value);
    }
}
