package com.example.news.api;

import com.example.news.models.News;

import io.reactivex.Single;

public interface INetWorkManager {
    Single<News> getNews();
}
