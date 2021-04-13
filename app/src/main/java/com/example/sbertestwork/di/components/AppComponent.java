package com.example.sbertestwork.di.components;


import android.app.Application;

import com.example.sbertestwork.MainActivity;
import com.example.sbertestwork.di.module.AppModule;
import com.example.sbertestwork.di.module.ContextModule;
import com.example.sbertestwork.di.module.DbModule;
import com.example.sbertestwork.di.module.NetworkModule;
import com.example.sbertestwork.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = {
        NetworkModule.class,
        DbModule.class, ContextModule.class, ViewModelModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder withApplication(Application application);

        @BindsInstance
        Builder dbModule(DbModule dbModule);

        @BindsInstance
        Builder networkModule(NetworkModule networkModule);

        AppComponent build();
    }

    void inject(MainActivity mainActivity);
}
