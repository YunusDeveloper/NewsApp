package com.example.news.api;

import android.content.Context;
import android.util.Log;

import com.example.news.models.News;
import com.readystatesoftware.chuck.ChuckInterceptor;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager implements INetWorkManager {
    private String key = "4aff91ee10694dcbb8c4c61aaaa271df";
    private String BASE_URL = "https://newsapi.org/v2/";
    private NewsApi service;
    private CompositeDisposable cd;
    private Context context;
    private static final String TAG = "NetworkManager";

    public NetworkManager(Context context) {
        this.context=context;
        initData();
    }


    private void initData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(NewsApi.class);
        Log.d(TAG, "initData: "+service.getNews("techcrunch",key));
    }


    @Override
    public Single<News> getNews() {
        Single<News> getNews = service.getNews("techcrunch",key)
                .subscribeOn(Schedulers.io())
                .doOnError(throwable -> Log.d(TAG, "accept: " + throwable.getMessage()))
                .observeOn(AndroidSchedulers.mainThread());
        return getNews;
    }


}
