package com.sergiu.libihb_java.presentation.fragment.add_memory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddMemoryViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isMapFullScreen = new MutableLiveData<>(false);
    public LiveData<Boolean> getIisMapFullScreen() {
        return isMapFullScreen;
    }

    public void toggleFullScreen() {
        isMapFullScreen.setValue(Boolean.FALSE.equals(isMapFullScreen.getValue()));
    }

}
