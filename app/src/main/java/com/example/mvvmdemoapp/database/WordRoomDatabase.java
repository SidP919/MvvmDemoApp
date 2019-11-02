package com.example.mvvmdemoapp.database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mvvmdemoapp.dao.WordDAO;
import com.example.mvvmdemoapp.entity.Word;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    //08 continue...
    //To delete all content and repopulate the database whenever the app is started,
    // you create a RoomDatabase.Callback and override the onOpen() method.
    // Because you cannot do Room database operations on the UI thread,
    // onOpen() creates and executes an AsyncTask to add content to the database.
    //
    //Add the onOpen() callback in the WordRoomDatabase class:
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    //Define the DAOs that work with the database. Provide an abstract "getter" method for each @Dao.
    public abstract WordDAO wordDAO();

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
                            //08
                            //Add the callback to the database build sequence in WordRoomDatabase,
                            // right before you call .build():
                            .addCallback(sRoomDatabaseCallback)
                            //08 paused...
                            .build();
                }
            }//lock is released here.
        }
        return INSTANCE;
    }

    //Create an inner class PopulateDbAsync that extends AsycTask.
    // Implement the doInBackground() method to delete all words, then create new ones.
    // Here is the code for the AsyncTask that deletes the contents of the database,
    // then populates it with an initial list of words.
    ///**
    //* Populate the database in the background.
    //*/
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDAO mDao;
        String[] words = {"Hello", "World", "What's", "going", "on", "there"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            for (int i = 0; i <= words.length - 1; i++) {
                Word word = new Word(words[i]);
                mDao.insert(word);
            }
            return null;
        }
    }
    //08 ends. go to MainActivity for //09
}
//03 ends. GO to package repository --> WordRepository and find //04