package com.example.news.api;

import com.example.news.models.News;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    //BASE URL https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=4aff91ee10694dcbb8c4c61aaaa271df
    @GET("top-headlines")
    Single<News> getNews(
            @Query("sources") String sources,
            @Query("apiKey") String apiKey
    );
}
