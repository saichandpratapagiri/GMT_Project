package com.example.gmtandroid.PostLogin.project_story;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectStoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProjectStoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}