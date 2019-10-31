package com.example.mvvmdemoapp.dao;

import com.example.mvvmdemoapp.entity.Word;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

//02
@Dao
public interface WordDAO {
    @Insert
    public void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
}
//02 ends.