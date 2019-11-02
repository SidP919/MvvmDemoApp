package com.example.mvvmdemoapp;

import android.os.Bundle;

import com.example.mvvmdemoapp.adapter.WordListAdapter;
import com.example.mvvmdemoapp.entity.Word;
import com.example.mvvmdemoapp.viewmodel.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //09
    //create a member variable for the ViewModel,
    // because all the activity's interactions are with the WordViewModel only.
    private WordViewModel mWordViewModel;
    //09 paused...

    //00
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //06
        //for showing words in a RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //06 ends. Go to Adapter package ---> WordListAdapter.java and find //07

        //09 continued...
        //get a ViewModel from the ViewModelProviders class.
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        //Use ViewModelProviders to associate your ViewModel with your UI controller.
        // When your app first starts, the ViewModelProviders class creates the ViewModel.
        // When the activity is destroyed,
        // for example through a configuration change, the ViewModel persists.
        // When the activity is re-created, the ViewModelProviders return the existing ViewModel.

        //add an observer for the LiveData returned by getAllWords().
        //When the observed data changes while the activity is in the foreground,
        // the onChanged() method is invoked and updates the data cached in the adapter.
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });
        //09 ends.
    }

    //00
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //00
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //00 ends. go to package entity --> class Word --> find //01
}
