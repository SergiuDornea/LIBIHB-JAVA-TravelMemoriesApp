package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentMemoryOverviewBinding;
import com.sergiu.libihb_java.presentation.adapters.MemoryOverviewAdapter;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MemoryOverview extends Fragment {
    private static final String TAG = MemoryOverview.class.getSimpleName();
    private MemoryOverviewViewModel viewModel;
    private FragmentMemoryOverviewBinding binding;
    private SupportMapFragment mapFragment;
    private MemoryOverviewAdapter memoryOverviewAdapter;

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
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_overview);
        setListeners();
        setObservers();
    }

    private void setListeners() {
        memoryOverviewAdapter = new MemoryOverviewAdapter(position -> Log.d(TAG, "setListeners: on deletion click"));
    }

    private void setObservers() {
        viewModel.observeMemoryName().observe(getViewLifecycleOwner(), s -> binding.nameTextView.setText(s));
        viewModel.observeMemoryDescription().observe(getViewLifecycleOwner(), s -> binding.descriptionTextView.setText(s));
        viewModel.observePlaceLocationName().observe(getViewLifecycleOwner(), s -> binding.locationNameTextView.setText(s));
        viewModel.observeDateOfTravel().observe(getViewLifecycleOwner(), date -> binding.dateTextView.setText(date.toString()));
        viewModel.observeCoordinates().observe(getViewLifecycleOwner(), latLng -> {
            if (mapFragment != null) {
                mapFragment.getMapAsync(googleMap -> {
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
                });
            }
        });
        viewModel.observeListOfImgUri().observe(getViewLifecycleOwner(), strings -> {
            if (strings.size() > 0) {
                setUpRecyclerview();
            }
        });
    }

    private void setUpRecyclerview() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.memoryOverviewRecycleView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.memoryOverviewRecycleView.getContext(), R.drawable.overview_img_item_divider)));
        memoryOverviewAdapter.updateImgUriList(viewModel.observeListOfImgUri().getValue());
        binding.memoryOverviewRecycleView.setAdapter(memoryOverviewAdapter);
    }
}