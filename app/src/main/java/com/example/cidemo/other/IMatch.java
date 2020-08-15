package com.example.cidemo.other;
import com.example.cidemo.model.Match;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IMatch {
    @GET(".")
    Call<Match> getMatch();
}
