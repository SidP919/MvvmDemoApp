package com.example.mvvmdemoapp.database;

import android.content.Context;

import com.example.mvvmdemoapp.dao.WordDAO;
import com.example.mvvmdemoapp.entity.Word;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//03
/*
Annotate the class to be a Room database. Declare the entities that belong in the database—
in this case there is only one entity, Word.
 (Listing the entities class or classes creates corresponding tables in the database.)
 Set the version number.
 Also set export schema to false, exportSchema keeps a history of schema versions.
 For this practical you can disable it, since you are not migrating the database.
 */
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static WordRoomDatabase INSTANCE;

    private WordRoomDatabase() {
    }

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {//Synchronized method is used
                // to lock an object for any shared resource.
                // When a thread invokes a synchronized method,
                // it automatically acquires the lock for that object
                // and releases it when the thread completes its task.
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical for which go to:
                            //https://medium.com/google-developers/understanding-migrations-with-room-f01e04b07929
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }//lock is released here.
        }
        return INSTANCE;
    }

    //Define the DAOs that work with the database. Provide an abstract "getter" method for each @Dao.
    public abstract WordDAO wordDAO();
}
//03 ends. GO to package repository --> WordRepository and find //04