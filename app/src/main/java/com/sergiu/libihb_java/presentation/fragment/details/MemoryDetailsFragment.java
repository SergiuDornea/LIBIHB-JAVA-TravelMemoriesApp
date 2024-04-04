package com.sergiu.libihb_java.presentation.fragment.details;

import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_POSITION_KEY;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentMemoryDetailsBinding;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MemoryDetailsFragment extends Fragment {
    private static final String TAG  = MemoryDetailsFragment.class.getSimpleName();
    private MemoryDetailsViewModel viewModel;
    private FragmentMemoryDetailsBinding binding;
    private TravelMemory memory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MemoryDetailsViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int position = bundle.getInt(MEMORY_POSITION_KEY);
            memory = viewModel.getMemoryFromPosition(position);
            Log.d(TAG, "onCreate: memory bundle not null");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMemoryDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}