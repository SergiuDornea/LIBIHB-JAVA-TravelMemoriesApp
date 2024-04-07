package com.sergiu.libihb_java.presentation.fragment.details;

import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_POSITION_KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentMemoryDetailsBinding;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.presentation.adapters.DetailsCarouselAdapter;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MemoryDetailsFragment extends Fragment {
    private static final String TAG = MemoryDetailsFragment.class.getSimpleName();
    private MemoryDetailsViewModel viewModel;
    private SupportMapFragment mapFragment;
    private NavController navController;
    private FragmentMemoryDetailsBinding binding;
    private TravelMemory memory;

    private DetailsCarouselAdapter detailsCarouselAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MemoryDetailsViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            memory = bundle.getParcelable(MEMORY_POSITION_KEY);
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
        navController = NavHostFragment.findNavController(MemoryDetailsFragment.this);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_details);
        setListeners();
        setUpRecyclerView();
        setUi();
    }

    private void setListeners() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navController.popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    private void setUi() {
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                googleMap.addMarker(new MarkerOptions().position(memory.getCoordinates()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(memory.getCoordinates(), 9));
            });
        }

        binding.titleTextView.setText(memory.getMemoryName());
        binding.detailsDateTextView.setText(memory.getFormattedDate());
        binding.detailsLocationCountryTextView.setText(memory.getPlaceCountryName());
        binding.detailsLocationAdminAreaTextView.setText(memory.getPlaceAdminAreaName());
        binding.detailsLocationNameTextView.setText(memory.getPlaceLocationName());
        binding.descriptionTextView.setText(memory.getMemoryDescription());
    }

    private void setUpRecyclerView() {
        detailsCarouselAdapter = new DetailsCarouselAdapter(memory.getImageList(), position -> Log.d(TAG, "setListeners: pos"));
        binding.photoCarouselRecycleView.setAdapter(detailsCarouselAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.photoCarouselRecycleView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.photoCarouselRecycleView.getContext(), R.drawable.item_divider)));
        binding.photoCarouselRecycleView.addItemDecoration(dividerItemDecoration);
    }
}