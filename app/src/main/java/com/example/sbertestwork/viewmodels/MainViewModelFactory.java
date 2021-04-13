package com.example.sbertestwork.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sbertestwork.data.Repository;

import javax.inject.Inject;

public class MainViewModelFactory implements ViewModelProvider.Factory{

    private final Repository repository;

    @Inject
    public MainViewModelFactory(Repository repository){
        this.repository=repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
