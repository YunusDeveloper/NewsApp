package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.example.news.adapter.NewsAdapter;
import com.example.news.api.INetWorkManager;
import com.example.news.api.NetworkManager;
import com.example.news.models.Article;
import com.example.news.models.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private List<Article> articles;
    private NewsAdapter adapter;
    private CompositeDisposable cd;
    private static final String TAG = "MainActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNet();


    }

    private void initView() {
        recyclerView = findViewById(R.id.rvMain);
        adapter = new NewsAdapter(articles, this);
        manager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void initNet() {
        articles=new ArrayList<>();
        cd = new CompositeDisposable();
        INetWorkManager manager = ((App) getApplication()).getManager();
        Disposable d = manager.getNews()
                .subscribe(news -> {
                    articles = news.getArticles();
                    initView();
                    Log.d(TAG, "initNet: "+articles.size());
                    Log.d(TAG, "initNet: "+news.getArticles().size());
                }, throwable -> {
                    Log.d(TAG, "initNet: "+throwable.getMessage());
                });
        cd.add(d);

       /* Disposable d = netWorkManager.getNews()
                .subscribe((news,throwable) -> {

                }));*/
       // cd.add(d);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cd != null) {
            cd.clear();
        }
    }
}
