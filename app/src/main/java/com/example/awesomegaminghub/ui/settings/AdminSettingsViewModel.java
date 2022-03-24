package com.example.awesomegaminghub.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminSettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminSettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is admin settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
