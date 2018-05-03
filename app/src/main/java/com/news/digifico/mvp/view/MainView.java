package com.news.digifico.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.news.digifico.db.entities.NewsEntity;

import java.util.List;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public interface MainView extends MvpView {
    void setListNews(List<NewsEntity> data);
    void setNewsItem(NewsEntity item);
    void showDialog();
    void showToast();
}
