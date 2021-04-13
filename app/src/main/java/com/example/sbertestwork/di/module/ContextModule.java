package com.example.sbertestwork.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ContextModule {

    @Singleton
    @Binds
    abstract Context getContext(Application application);
}