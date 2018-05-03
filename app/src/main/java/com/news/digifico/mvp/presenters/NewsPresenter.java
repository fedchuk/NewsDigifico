package com.news.digifico.mvp.presenters;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.news.digifico.NewsDigifico;
import com.news.digifico.api.helper.ApiHelper;
import com.news.digifico.db.NewsDatabase;
import com.news.digifico.db.entities.NewsEntity;
import com.news.digifico.mvp.view.NewsView;
import com.news.digifico.utils.CommonUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {
    private NewsDatabase database;
    private NewsEntity item;

    public NewsPresenter() {
        database = NewsDigifico.getInstance().getDatabese();
    }

    public void getNews(long id){
        if (id != 0){
            item = database.getNewsDao().getItem(id);
            if (!CommonUtils.isNetworkConnected(NewsDigifico.getAppContext()) && item != null) {
                getViewState().setNews(item);
            } else {
                ApiHelper.getInstance().getNews(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver());
            }
        }
    }

    @NonNull
    private Observer<NewsEntity> getObserver() {
        return new Observer<NewsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NewsEntity value) {
                if (value != null){
                    database.getNewsDao().updateItem(value);
                    getViewState().setNews(value);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
