package com.news.digifico.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.news.digifico.AppSingleton;
import com.news.digifico.R;
import com.news.digifico.db.entities.NewsEntity;
import com.news.digifico.dialogs.Dialogs;
import com.news.digifico.mvp.presenters.NewsPresenter;
import com.news.digifico.mvp.view.NewsView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class NewsActivity extends MvpAppCompatActivity implements NewsView {
    private static final String LONG_ID_KEY = "LONG_ID";
    @InjectPresenter
    NewsPresenter presenter;

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_news)
    TextView txtNews;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private long newsId;

    public static Intent getIntent(Context context, long id){
        Intent intent =  new Intent(context, NewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(LONG_ID_KEY, id);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        catchInternetChanges();
        initToolbar();

        Intent intent = getIntent();
        if (intent != null){
            newsId = intent.getLongExtra(LONG_ID_KEY, 0);
            presenter.getNews(newsId);
        }
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new BackBtnCkick());
    }

    private void catchInternetChanges() {
        LiveData<Boolean> liveData = AppSingleton.getInstance().getLiveData();
        liveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isConnected) {
                if (!isConnected){
                    Dialogs.noInternetConnectionDialog(NewsActivity.this);
                }
            }
        });
    }

    @Override
    public void setNews(NewsEntity item) {
        txtTitle.setText(item.getTitle());
        txtNews.setText(item.getBody());
    }

    @Override
    public void showDialog() {
        Dialogs.noInternetConnectionDialog(NewsActivity.this);
    }

    private class BackBtnCkick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }
}
