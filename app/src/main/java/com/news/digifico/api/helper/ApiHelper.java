package com.news.digifico.api.helper;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.news.digifico.NewsDigifico;
import com.news.digifico.db.entities.NewsEntity;
import com.news.digifico.utils.CommonUtils;
import com.news.digifico.utils.Constants;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class ApiHelper {
    private static ApiHelper instance;
    private final Handler handler = new Handler();
    private static int countSendItems = 0;

    private ApiHelper() {
    }

    public static ApiHelper getInstance() {
        if (instance == null) {
            instance = new ApiHelper();
        }
        return instance;
    }

    public Observable<List<NewsEntity>> getListNews() {
        return new Observable<List<NewsEntity>>() {
            @Override
            protected void subscribeActual(Observer<? super List<NewsEntity>> observer) {
                observer.onNext(generateListOfItems());
            }
        };
    }

    public Observable<NewsEntity> getNews(final long id){
        return new Observable<NewsEntity>() {
            @Override
            protected void subscribeActual(Observer<? super NewsEntity> observer) {
                for (NewsEntity item: allListOfItems()){
                    if (item.getId() == id){
                        observer.onNext(item);
                        break;
                    }
                }
            }
        };
    }

    public Flowable<NewsEntity> updateNews(){
        return new Flowable<NewsEntity>() {
            @Override
            protected void subscribeActual(final Subscriber<? super NewsEntity> s) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (countSendItems != 5){
                            s.onNext(generateItemsEveryMinute().get(countSendItems));
                            countSendItems++;
                            if (CommonUtils.isNetworkConnected(NewsDigifico.getAppContext())){
                                handler.postDelayed(this, Constants.DELAY);
                            }
                        }
                    }
                }, Constants.DELAY);
            }
        };
    }

    @NonNull
    private List<NewsEntity> generateItemsEveryMinute() {
        List<NewsEntity> listItems = new ArrayList<>();
        NewsEntity item;
        for (int i=11; i<16; i++) {
            item = new NewsEntity();
            item.setId(i);
            item.setTitle("Title " + i);
            item.setBody(Constants.NEWS_TEXT);
            listItems.add(item);
        }
        return listItems;
    }

    @NonNull
    private List<NewsEntity> generateListOfItems() {
        List<NewsEntity> listItems = new ArrayList<>();
        NewsEntity item;
        for (int i=1; i<11; i++) {
            item = new NewsEntity();
            item.setId(i);
            item.setTitle("Title " + i);
            item.setBody(Constants.NEWS_TEXT);
            listItems.add(item);
        }
        return listItems;
    }

    @NonNull
    private List<NewsEntity> allListOfItems() {
        List<NewsEntity> listItems = new ArrayList<>();
        NewsEntity item;
        for (int i=1; i<16; i++) {
            item = new NewsEntity();
            item.setId(i);
            item.setTitle("Title " + i);
            item.setBody(Constants.NEWS_TEXT);
            listItems.add(item);
        }
        return listItems;
    }
}
