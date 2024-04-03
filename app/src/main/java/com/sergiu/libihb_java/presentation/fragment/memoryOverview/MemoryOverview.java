package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentMemoryOverviewBinding;
import com.sergiu.libihb_java.presentation.fragment.map.MapsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MemoryOverview extends Fragment {
    private MemoryOverviewViewModel viewModel;
    private FragmentMemoryOverviewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MemoryOverviewViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMemoryOverviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setObservers();
    }

    private void setObservers() {
        viewModel.getMemoryName().observe(getViewLifecycleOwner(), s -> binding.nameTextView.setText(s));
        viewModel.getMemoryDescription().observe(getViewLifecycleOwner(), s -> binding.descriptionTextView.setText(s));
        viewModel.getPlaceLocationName().observe(getViewLifecycleOwner(), s -> binding.locationNameTextView.setText(s));
        viewModel.getDateOfTravel().observe(getViewLifecycleOwner(), date -> binding.dateTextView.setText(date.toString()));
        viewModel.getCoordinates().observe(getViewLifecycleOwner(), latLng -> {

        });
        viewModel.getListOfImgUri().observe(getViewLifecycleOwner(), strings -> {
            binding.textView.setText(strings.get(0));
        });
    }
}