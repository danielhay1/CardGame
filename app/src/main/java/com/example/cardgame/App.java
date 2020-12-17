package com.example.cardgame;

import android.app.Application;

import untils.ImgLoader;
import untils.MyLocationServices;
import untils.Sound;
import untils.MyPreference;
import untils.MySignal;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyPreference.Init(this);
        ImgLoader.Init(this);
        MySignal.Init(this);
        Sound.Init(this);
        MyLocationServices.Init(this);
    }
}
