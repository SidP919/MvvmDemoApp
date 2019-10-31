package com.example.mvvmdemoapp.viewmodel;

import android.app.Application;

import com.example.mvvmdemoapp.entity.Word;
import com.example.mvvmdemoapp.repository.WordRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

//05
//single responsibility principle:
// Your activities and fragments are responsible for drawing data to the screen,
// while your ViewModel is responsible for holding and processing all the data needed for the UI.
public class WordViewModel extends AndroidViewModel {

    /*
    Warning:
    1>> Never pass context into ViewModel instances.
    2>> Do not store Activity, Fragment, or View instances or their Context in the ViewModel.
     */

    //Add a private member variable to hold a reference to the Repository.
    private WordRepository mRepository;

    //Add a private LiveData member variable to cache the list of words.
    private LiveData<List<Word>> mAllWords;

    //Add a constructor that gets a reference to the WordRepository
    // and gets the list of all words from the WordRepository.
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    //Add a "getter" method that gets all the words.
    // This completely hides the implementation from the UI.
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    //create a wrapper insert() method that calls the Repository's insert() method. In this way,
    // the implementation of insert() is completely hidden from the UI.
    public void insert(Word word) {
        mRepository.insert(word);
    }
}
//To Learn more: https://www.youtube.com/watch?v=c9-057jC1ZA
//05 ends. Go to MainActivity for //06