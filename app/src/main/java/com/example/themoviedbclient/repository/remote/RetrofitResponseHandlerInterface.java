package com.example.themoviedbclient.repository.remote;

import retrofit2.Call;
import retrofit2.Response;

public interface RetrofitResponseHandlerInterface {

    public void onResponse(Call call, Response response);

    public void onFailure(Call call, Throwable t);
}
