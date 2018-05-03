package com.news.digifico.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.news.digifico.AppSingleton;
import com.news.digifico.R;
import com.news.digifico.adapter.NewsListAdapter;
import com.news.digifico.adapter.OnItemClickListener;
import com.news.digifico.db.entities.NewsEntity;
import com.news.digifico.dialogs.Dialogs;
import com.news.digifico.mvp.presenters.MainPresenter;
import com.news.digifico.mvp.view.MainView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class MainActivity extends MvpAppCompatActivity implements MainView, OnItemClickListener {
    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private NewsListAdapter adapter;
    private LinearLayoutManager manager;

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        catchInternetChanges();
        presenter.getDataNews();
    }

    private void catchInternetChanges() {
        LiveData<Boolean> liveData = AppSingleton.getInstance().getLiveData();
        liveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isConnected) {
                if (!isConnected) {
                    Dialogs.noInternetConnectionDialog(MainActivity.this);
                } else {
                    presenter.getDataNews();
                }
            }
        });
    }

    private void init() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);


        adapter = new NewsListAdapter(this);
        manager = new LinearLayoutManager(getApplicationContext());
        rvNews.setLayoutManager(manager);
        rvNews.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void setListNews(List<NewsEntity> data) {
        adapter.update(data);
    }

    @Override
    public void setNewsItem(NewsEntity item) {
        adapter.updateItem(item);
    }

    @Override
    public void showDialog() {
        Dialogs.noInternetConnectionDialog(MainActivity.this);
    }

    @Override
    public void showToast() {
        Toast.makeText(this, "Toasr", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(long id) {
        startActivity(NewsActivity.getIntent(this, id));
    }
}
