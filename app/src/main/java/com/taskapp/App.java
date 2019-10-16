package com.taskapp;

import android.app.Application;
import android.media.AsyncPlayer;

import androidx.room.Room;

import com.taskapp.room.MyDatabase;

public class App extends Application {
    public static App instance;
    private MyDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        database= Room.databaseBuilder(this,MyDatabase.class,"database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public MyDatabase getDatabase() {
        return database;
    }
}
