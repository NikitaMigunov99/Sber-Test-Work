package com.example.sbertestwork;

import android.app.Application;

import com.example.sbertestwork.di.components.AppComponent;

//import com.example.sbertestwork.di.components.DaggerAppComponent;
import com.example.sbertestwork.di.components.DaggerAppComponent;
import com.example.sbertestwork.di.module.AppModule;
import com.example.sbertestwork.di.module.ContextModule;
import com.example.sbertestwork.di.module.DbModule;
import com.example.sbertestwork.di.module.NetworkModule;

public class MainApplication extends Application {

    private AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().withApplication(this).dbModule(new DbModule()).
                networkModule(new NetworkModule()).build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
