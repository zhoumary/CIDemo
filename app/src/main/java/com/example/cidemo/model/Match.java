package com.example.cidemo.model;

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
    public void loadData(String feedUrl, final LoadListener<MatchItem> loadListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(feedUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMatch iMatch = retrofit.create(IMatch.class);
        Call<Match> match = iMatch.getMatch();
        match.enqueue(new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {
                // 获取成功
                List<MatchItem> matchesList = new ArrayList<>();
                for (int i = 0; i < response.body().getData().size(); i++) {
                    matchesList.add(response.body().getData().get(i));
                }
                loadListener.loadSuccess(matchesList);
            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                // 获取失败
                loadListener.loadFailure(t.getMessage());
            }
        });
    }
}
