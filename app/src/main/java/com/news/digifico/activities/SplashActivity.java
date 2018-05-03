package com.news.digifico.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.news.digifico.R;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.getIntent(SplashActivity.this));
                finish();
            }
        }, 2000);
    }
}
