package com.attracttest.attractgroup.retrofittask;

import com.attracttest.attractgroup.retrofittask.pojos.GitResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nexus on 30.09.2017.
 */
public interface GitHubService {

    String BASE_URL = "https://api.github.com/";

    @GET("search/repositories")
    Call<GitResponse> getListReposByLang(@Query("q") String lang);



}
