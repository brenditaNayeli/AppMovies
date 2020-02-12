package com.example.appmovies.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appmovies.data.local.dao.MovieDao;
import com.example.appmovies.data.local.entity.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDataBase extends RoomDatabase {
    public abstract MovieDao getMovieDao();

}
