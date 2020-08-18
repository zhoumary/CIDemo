package com.example.cidemo.model;

import android.database.Cursor;

import com.example.cidemo.other.DatabaseHelper;
import com.example.cidemo.other.IMatch;
import com.example.cidemo.other.LoadListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Match {
    private boolean has_more;
    private String message;
    private List<MatchItem> data;

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<MatchItem> data) {
        this.data = data;
    }

    public boolean getHas_more() {
        return has_more;
    }

    public String getMessage() {
        return message;
    }

    public List<MatchItem> getData() {
        return data;
    }

    // 通过传进来的url，利用retrofit获取网络数据，回调给viewModel
    public void loadData(DatabaseHelper mDatabaseHelper, final LoadListener<MatchItem> loadListener) {
        // get downloaded match data from "match_table"
        Cursor matchCursor = mDatabaseHelper.getMatchData();

        if (matchCursor != null) {
            List<MatchItem> matchesList = new ArrayList<>();
            while (matchCursor.moveToNext()) {
                String matchID = matchCursor.getString(1);
                String matchName = matchCursor.getString(2);
                String matchScore = matchCursor.getString(3);
                MatchItem matchItem = new MatchItem(matchName, matchID, matchScore);
                matchesList.add(matchItem);
            }
            loadListener.loadSuccess(matchesList);
        } else {
            loadListener.loadFailure("Load match data failed!");
        }
    }
}
