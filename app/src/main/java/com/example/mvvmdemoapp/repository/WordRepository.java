package com.example.mvvmdemoapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mvvmdemoapp.dao.WordDAO;
import com.example.mvvmdemoapp.database.WordRoomDatabase;
import com.example.mvvmdemoapp.entity.Word;

import java.util.List;

import androidx.lifecycle.LiveData;

//04
public class WordRepository {
    //Add member variables for the DAO and the list of words.
    private WordDAO mWordDAO;
    private LiveData<List<Word>> mAllWords;

    //Add a constructor that gets a handle to the database and initializes the member variables.
    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDAO = db.wordDAO();
        mAllWords = mWordDAO.getAllWords();
    }

    //Add a wrapper method called getAllWords() that returns the cached words as LiveData.
    // Room executes all queries on a separate thread.
    // Observed LiveData notifies the observer when the data changes.
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    //Add a wrapper for the insert() method. Use an AsyncTask to call insert() on a non-UI thread,
    // or your app will crash.
    // Room ensures that you don't do any long-running operations on the main thread,
    // which would block the UI.
    public void insert(Word word) {
        new insertAsyncTask(mWordDAO).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDAO mAsyncTaskDAO;

        insertAsyncTask(WordDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        new deleteAllWordsAsyncTask(mWordDAO).execute();
    }

    public void deleteWord(Word word) {
        new deleteWordAsyncTask(mWordDAO).execute(word);
    }
    //16 ends Goto WordViewModel for //17

    //16
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDAO mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    //20
    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDAO mAsyncTaskDao;

        deleteWordAsyncTask(WordDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
    //20 ends. Goto WordViewModel for //21
}
//For more complex implementation check out:
// https://github.com/googlesamples/android-architecture-components/tree/master/BasicSample
//04 ends. Go to ViewModel package --> WordViewModel and find //05