package com.news.digifico.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.news.digifico.db.entities.NewsEntity;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public interface NewsView extends MvpView {
    void setNews(NewsEntity item);
    void showDialog();
}
