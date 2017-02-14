package com.waracle.androidtest;

import android.app.Application;

/**
 * Created by Ruairi on 14/02/2017.
 */

public class WaracleTestApplication extends Application {

    private static WaracleTestApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static WaracleTestApplication getInstance(){
        return instance;
    }
}
