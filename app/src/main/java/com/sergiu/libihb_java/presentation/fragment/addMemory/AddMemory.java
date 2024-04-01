package com.sergiu.libihb_java.presentation.fragment.addMemory;

import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sergiu.libihb_java.databinding.FragmentAddMemoryBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddMemory extends Fragment {
    private static final String TAG = AddMemory.class.getName();
    private static final Integer MAX_MEMORY_IMG = 5;
    private FragmentAddMemoryBinding binding;
    private AddMemoryViewModel viewModel;
    private Address address;
    private Date date;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityResultLauncher();
        viewModel = new ViewModelProvider(this).get(AddMemoryViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMemoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    private void setListeners() {
        binding.pickADateMaterialButton.setOnClickListener(view -> {
            DatePicker datePicker = new DatePicker(new DatePicker.OnDateSelectedListener() {
                @Override
                public void onDateSelected(int year, int month, int day) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    date = calendar.getTime();
                }
            });
            datePicker.show(requireActivity().getSupportFragmentManager(), "datePiker");
        });

        binding.choosePhotosMaterialButton.setOnClickListener(view -> {
            choosePhotosFromGallery();
        });
    }

    private void initializeActivityResultLauncher() {
        pickMultipleMedia = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(MAX_MEMORY_IMG), uris -> {
            if (uris.isEmpty()) {
                Log.d(TAG, "No media selected");
            } else {
                Log.d(TAG, "Number of items selected: " + uris.size());
                List<String> uriStrings = new ArrayList<>();
                for (Uri uri : uris) {
                    uriStrings.add(uri.toString());
                }
                Log.d(TAG, "URIs selected: " + uriStrings);
                viewModel.setListOfImgUri(uriStrings);
            }
        });
    }

    private void choosePhotosFromGallery() {
        pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }
}