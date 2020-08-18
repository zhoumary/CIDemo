package com.example.cidemo.viewmodel;

import android.content.Context;
import android.widget.Toast;

import com.example.cidemo.R;
import com.example.cidemo.model.Match;
import com.example.cidemo.model.MatchItem;
import com.example.cidemo.other.DatabaseHelper;
import com.example.cidemo.other.LoadListener;
import com.example.cidemo.view.MainActivity;

import java.util.List;

public class MainViewModel {

    private static final String TAG = "MainViewModel";
    private MainActivity mActivity;

    public MainViewModel(MainActivity activity) {
        mActivity = activity;
    }

    public void loadMatches(DatabaseHelper mDatabaseHelper) {
        // 加载下载的数据
        Match match = new Match();
        match.loadData(mDatabaseHelper, new LoadListener<MatchItem>() {
            @Override
            public void loadSuccess(List<MatchItem> list) {
                // 加载数据成功
                mActivity.mMatchList.clear();
                mActivity.mMatchList.addAll(list);
                mActivity.mMatchItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void loadFailure(String message) {
                // 加载数据失败
            }
        });
    }
}
