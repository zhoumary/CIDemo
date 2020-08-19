package com.example.cidemo.viewmodel;

import com.example.cidemo.model.MatchItem;
import com.example.cidemo.other.DatabaseHelper;
import com.example.cidemo.view.MainMatchActivity;

public class MainMatchViewModel {

    private static final String TAG = "MainMatchViewModel";
    private MainMatchActivity mMatchActivity;

    DatabaseHelper mDatabaseHelper;

    public MainMatchViewModel(MainMatchActivity mainMatchActivity) {
        mMatchActivity = mainMatchActivity;
    }

    public void loadMatchData(MatchItem match) {

    }
}
