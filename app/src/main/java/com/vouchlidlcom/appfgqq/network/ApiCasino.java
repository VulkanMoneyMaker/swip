package com.vouchlidlcom.appfgqq.network;


import com.vouchlidlcom.appfgqq.network.model.ModelRequest;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("vul-54")
    Call<ModelRequest> check();

}
