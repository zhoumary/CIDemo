package com.example.cidemo.other;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {
    @GET("token.xsjs/")
    Call<Response> getToken(
            @Header("X-CSRF-Token") String token
    );

    @FormUrlEncoded
    @POST("login.xscfunc/")
    Call<ResponseBody> login(
            @Header("X-CSRF-Token") String token,
            @Field("xs-username") String username,
            @Field("xs-password") String password
    );

}
