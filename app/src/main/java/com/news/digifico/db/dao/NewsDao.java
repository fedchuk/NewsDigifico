package com.news.digifico.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.news.digifico.db.entities.NewsEntity;

import java.util.List;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
@Dao
public interface NewsDao {
    @Insert
    void insertItem(NewsEntity item);

    @Insert
    void insertList(List<NewsEntity> itemsList);

    @Update
    void updateList(List<NewsEntity> itemsList);

    @Update
    void updateItem(NewsEntity item);

    @Delete
    void delete(List<NewsEntity> itemsList);

    @Query("SELECT * FROM news_entity WHERE id = :id")
    NewsEntity getItem(long id);

    @Query("SELECT * FROM news_entity")
    List<NewsEntity> getListItems();
}
