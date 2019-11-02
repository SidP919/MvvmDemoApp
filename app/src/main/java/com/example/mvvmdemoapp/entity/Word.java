package com.example.mvvmdemoapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//01
//Each @Entity class represents an entity in a table.
// Annotate your class declaration to indicate that the class is an entity.
// Specify the name of the table if
// you want it to be different from the name of the class.
@Entity(tableName = "word_table")
public class Word {

    @NonNull //Denotes that a parameter, field, or method return value can never be null.
    // The primary key should always use this annotation.
    // Use this annotation for any mandatory fields in your rows.

    @PrimaryKey    //Every entity needs a primary key. To keep things simple,
    // each word in the RoomWordsSample app acts as its own primary key.
    // To learn how to auto-generate unique keys, see the tip below.

    @ColumnInfo(name = "word")//Specify the name of a column in the table, if you want the
    // column name to be different from the name of the member variable.
    private String mWord;

    public Word(@NonNull String word){
        this.mWord = word;
    }

    public String getWord() {
        return this.mWord;
    }
}
//01 ends. Go to dao package --> WordDAO.java and find //02
/*
Tip on auto-generating keys:
To auto-generate a unique key for each entity,
 you would add and annotate a primary integer key
  with autoGenerate=true. See Defining data using Room entities.
  like this: @PrimaryKey( autoGenerate=true)
*/
