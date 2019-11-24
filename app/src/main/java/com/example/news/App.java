package com.example.news;

import android.app.Application;

import com.example.news.api.INetWorkManager;
import com.example.news.api.NetworkManager;

public class App extends Application {
    private INetWorkManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = new NetworkManager(this);
    }

    public INetWorkManager getManager() {
        return manager;
    }
}
