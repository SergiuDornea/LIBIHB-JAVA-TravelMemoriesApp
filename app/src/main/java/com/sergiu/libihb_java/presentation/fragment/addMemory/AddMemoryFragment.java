package com.sergiu.libihb_java.presentation.fragment.addMemory;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
public class AddMemoryFragment extends Fragment {
    private static final String TAG = AddMemoryFragment.class.getName();
    private static final Integer MAX_MEMORY_IMG = 5;
    private FragmentAddMemoryBinding binding;
    private AddMemoryViewModel viewModel;
    private Date date;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        initializeActivityResultLauncher();
        setTextWatchers();
        setListeners();
    }

    private void setTextWatchers() {
        binding.memoryNameAutocompleteField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setMemoryName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.memoryDescriptionAutocompleteField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setMemoryDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setListeners() {
        binding.pickADateMaterialButton.setOnClickListener(view -> {
            DatePicker datePicker = new DatePicker((year, month, day) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                date = calendar.getTime();
                viewModel.setDateOfTravel(date);
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
                Log.d(TAG, "No media selected"); // todo change language maybe
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