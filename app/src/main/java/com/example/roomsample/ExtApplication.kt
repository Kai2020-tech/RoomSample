package com.example.roomsample

import android.app.Application
import com.facebook.stetho.Stetho

class ExtApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}