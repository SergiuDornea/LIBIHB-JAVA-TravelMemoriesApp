package com.sergiu.libihb_java.presentation.fragment.memoryoverview;

import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_COORDINATES;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DATE;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DEFAULT;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DESCRIPTION;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_IMG_LIST;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_NAME;
import static com.sergiu.libihb_java.presentation.utils.DateUtil.formDateToString;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentOverviewBinding;
import com.sergiu.libihb_java.presentation.adapters.OverviewAdapter;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;

import java.util.Date;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OverviewFragment extends Fragment {
    private static final LatLng DEFAULT_COORDINATES = new LatLng(0, 0);
    private static final Date DEFAULT_DATE = new Date(0);
    private OverviewViewModel viewModel;
    private FragmentOverviewBinding binding;
    private SupportMapFragment mapFragment;
    private NavController navController;
    private OverviewAdapter memoryOverviewAdapter;
    private NavigateCallback navigateCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OverviewViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOverviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(OverviewFragment.this);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_details);
        memoryOverviewAdapter = new OverviewAdapter();
        binding.saveMemoryMaterialButton.setOnClickListener(v -> viewModel.onEvent(MemoryFormEvent.SubmitClicked));
        setObservers();
    }

    public void setNavigateCallback(NavigateCallback navigateCallback) {
        this.navigateCallback = navigateCallback;
    }

    private void setObservers() {
        viewModel.observeMemoryFormState().observe(getViewLifecycleOwner(), this::setUpMemoryForm);
        viewModel.getSaveMemoryClickedEvent().observe(getViewLifecycleOwner(), saveMemoryClickedEvent -> {
            if (saveMemoryClickedEvent != null) {
                String cause = saveMemoryClickedEvent.getCause();
                switch (cause) {
                    case CAUSE_NAME:
                        navigateWithInteractiveMessage(R.id.addFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DESCRIPTION:
                        navigateWithInteractiveMessage(R.id.addFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DATE:
                        navigateWithInteractiveMessage(R.id.addFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_IMG_LIST:
                        navigateWithInteractiveMessage(R.id.addFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_COORDINATES:
                        navigateWithInteractiveMessage(R.id.mapsFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DEFAULT:
                        Toast.makeText(requireContext(), getString(R.string.memory_saved), Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.mainFragment);
                }
            }
        });
    }

    private void navigateWithInteractiveMessage(int navDestination, String message) {
        Snackbar snackbar = Snackbar.make(
                binding.fragmentMemoryOverviewConstraintLayout,
                message,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.ok, v -> {
        });
        snackbar.show();
        triggerNavigationCallback(navDestination);
    }

    private void setUpMemoryForm(MemoryFormState form) {
        binding.nameTextView.setText(form.getMemoryName());
        binding.descriptionTextView.setText(form.getMemoryDescription());
        binding.locationNameTextView.setText(form.getPlaceLocationName());
        binding.dateTextView.setText(form.getDateOfTravel() != null ? formDateToString(form.getDateOfTravel()) : formDateToString(DEFAULT_DATE));

        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                googleMap.addMarker(new MarkerOptions().position(form.getLatitude() != 0 || form.getLongitude() != 0 ? new LatLng(form.getLatitude(), form.getLongitude()) : DEFAULT_COORDINATES));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(form.getLatitude() != 0 || form.getLongitude() != 0 ? new LatLng(form.getLatitude(), form.getLongitude()) : DEFAULT_COORDINATES, 8));
            });
        }
        memoryOverviewAdapter.updateImgUriList(form.getListOfImgUri());
        if (form.getListOfImgUri().size() > 0) {
            setUpRecyclerview();
        }
    }

    private void setUpRecyclerview() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.memoryOverviewRecycleView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.memoryOverviewRecycleView.getContext(), R.drawable.overview_img_item_divider)));
        binding.memoryOverviewRecycleView.setAdapter(memoryOverviewAdapter);
    }

    private void triggerNavigationCallback(int destinationId) {
        if (navigateCallback != null) {
            navigateCallback.navigationCallback(destinationId);
        }
    }

    public interface NavigateCallback {
        void navigationCallback(int navigationDestination);
    }
}