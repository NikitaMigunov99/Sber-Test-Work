package com.example.sbertestwork.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sbertestwork.di.ViewModelKey;
import com.example.sbertestwork.viewmodels.MainViewModel;
import com.example.sbertestwork.viewmodels.MainViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MainViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    protected abstract ViewModel githubListViewModel(MainViewModel viewModel);
}
