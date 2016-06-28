package com.codepath.listdemo;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application{

    public static Context  appContext;

    @Override
    public void onCreate() {
        appContext = this;
        super.onCreate();
    }

    public Context getContext(){
        return appContext;
    }
}
