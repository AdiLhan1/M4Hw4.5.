package com.taskapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.taskapp.Task;

@Database(entities = {Task.class},version = 3)
public abstract class MyDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
