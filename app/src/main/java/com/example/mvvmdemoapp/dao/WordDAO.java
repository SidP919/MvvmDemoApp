package com.example.mvvmdemoapp.dao;

import com.example.mvvmdemoapp.entity.Word;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

//02
@Dao
public interface WordDAO {
    @Insert
            //13
            //Your app uses the word itself as the primary key, and each primary key must be unique.
            //You can specify a conflict strategy to tell your app what to do
            // when the user tries to add an existing word.
            (onConflict = OnConflictStrategy.IGNORE)
        //13 ends.
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

    //14
    //add a method to get any word so that we can know if word_table is empty or not:
    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();
    //14 ends. Go to WordRoomDatabase for //15

    //19
    @Delete
    void deleteWord(Word word);
    //19 ends. Go to WordRepository for //20

}
//02 ends. Go to package database --> WordRoomDatabase and find //03