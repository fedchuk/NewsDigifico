package com.news.digifico.mvp.presenters;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.news.digifico.NewsDigifico;
import com.news.digifico.api.helper.ApiHelper;
import com.news.digifico.db.NewsDatabase;
import com.news.digifico.db.entities.NewsEntity;
import com.news.digifico.mvp.view.MainView;
import com.news.digifico.utils.CommonUtils;
import com.news.digifico.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private List<NewsEntity> listItems = new ArrayList<>();
    private NewsDatabase database;

    public MainPresenter() {
        database = NewsDigifico.getInstance().getDatabese();
    }

    public void getDataNews() {
        listItems = database.getNewsDao().getListItems();
        if (!CommonUtils.isNetworkConnected(NewsDigifico.getAppContext()) && listItems.isEmpty()) {
            getViewState().showDialog();
        } else if (!CommonUtils.isNetworkConnected(NewsDigifico.getAppContext()) && !listItems.isEmpty()) {
            getViewState().showDialog();
            getViewState().setListNews(listItems);
        } else {
            ApiHelper.getInstance().getListNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(getObserver());
        }
    }

    @NonNull
    private Observer<List<NewsEntity>> getObserver() {
        return new Observer<List<NewsEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<NewsEntity> value) {
                if (value != null && !value.isEmpty()) {
                    if (listItems.isEmpty()) {
                        database.getNewsDao().insertList(value);
                    } else {
                        database.getNewsDao().updateList(value);
                    }
                    getViewState().setListNews(value);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateNews();
                        }
                    }, Constants.DELAY);
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

    public void updateNews() {
        ApiHelper.getInstance().updateNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsEntity>() {
                    @Override
                    public void accept(NewsEntity newsModel) throws Exception {
                        if (newsModel != null) {
                            NewsEntity item = database.getNewsDao().getItem(newsModel.getId());
                            if (item == null) {
                                database.getNewsDao().insertItem(newsModel);
                            } else {
                                database.getNewsDao().updateItem(newsModel);
                            }
                            getViewState().setNewsItem(newsModel);
                        }
                    }
                });
    }
}
