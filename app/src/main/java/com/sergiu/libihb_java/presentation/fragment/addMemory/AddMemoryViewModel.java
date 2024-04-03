package com.sergiu.libihb_java.presentation.fragment.addMemory;

import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddMemoryViewModel extends ViewModel {
    private final MemoriesRoomRepository memoriesRoomRepository;

    @Inject
    public AddMemoryViewModel(MemoriesRoomRepository memoriesRoomRepository) {
        this.memoriesRoomRepository = memoriesRoomRepository;
    }

    public void setMemoryName(String name) {
        memoriesRoomRepository.setMemoryName(name);
    }

    public void setMemoryDescription(String description) {
        memoriesRoomRepository.setMemoryDescription(description);
    }

    public void setListOfImgUri(List<String> uriStrings) {
        memoriesRoomRepository.setListOfImgUri(uriStrings);
    }

    public void setDateOfTravel(Date date) {
        memoriesRoomRepository.setDateOfTravel(date);
    }
}
