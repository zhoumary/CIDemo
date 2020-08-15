package com.example.cidemo.viewmodel;

import com.example.cidemo.R;
import com.example.cidemo.model.Match;
import com.example.cidemo.model.MatchItem;
import com.example.cidemo.other.LoadListener;
import com.example.cidemo.view.MainActivity;

import java.util.List;

public class MainViewModel {

    private static final String TAG = "MainViewModel";
    private MainActivity mActivity;
    private String feedUrl;

    public MainViewModel(MainActivity activity) {
        mActivity = activity;
    }

    public void loadMatches() {
        // 获取url
        feedUrl = mActivity.getResources().getString(R.string.feed_api_url);

        // 加载数据
        Match match = new Match();
        match.loadData(feedUrl, new LoadListener<MatchItem>() {
            @Override
            public void loadSuccess(List<MatchItem> list) {
                // 加载数据成功
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
