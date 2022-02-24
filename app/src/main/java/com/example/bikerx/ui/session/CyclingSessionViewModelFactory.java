package com.example.bikerx.ui.session;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CyclingSessionViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public CyclingSessionViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new CyclingSessionViewModel(this.context);
    }
}
