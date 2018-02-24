package com.lidlvoucher.goapper.network;


import com.lidlvoucher.goapper.network.model.ModelRequest;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("vul-54")
    Call<ModelRequest> check();

}
