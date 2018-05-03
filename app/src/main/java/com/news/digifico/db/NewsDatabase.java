package com.news.digifico.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.news.digifico.db.dao.NewsDao;
import com.news.digifico.db.entities.NewsEntity;
import com.news.digifico.utils.Constants;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
@Database(entities = {NewsEntity.class}, version = NewsDatabase.VERSION, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    static final int VERSION = 1;
    private static final String DB_NAME = Constants.APP_DB_NAME;
    private static volatile NewsDatabase instance;

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static NewsDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                NewsDatabase.class,
                DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public abstract NewsDao getNewsDao();
}
