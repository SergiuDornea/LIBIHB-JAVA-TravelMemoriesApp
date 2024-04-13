package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_COORDINATES;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DATE;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DESCRIPTION;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_IMG_LIST;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_NAME;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentMemoryOverviewBinding;
import com.sergiu.libihb_java.presentation.adapters.MemoryOverviewAdapter;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MemoryOverviewFragment extends Fragment {
    private MemoryOverviewViewModel viewModel;
    private FragmentMemoryOverviewBinding binding;
    private SupportMapFragment mapFragment;
    private NavController navController;
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
        navController = NavHostFragment.findNavController(MemoryOverviewFragment.this);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_details);
        setListeners();
        setObservers();
    }

    private void setListeners() {
        memoryOverviewAdapter = new MemoryOverviewAdapter(position -> memoryOverviewAdapter.removeImgUriFromList(position));
        binding.saveMemoryMaterialButton.setOnClickListener(view -> viewModel.onEvent(MemoryFormEvent.SubmitClicked));
    }

    private void setObservers() {
        viewModel.observeMemoryFormState().observe(getViewLifecycleOwner(), this::setUpMemoryForm);
        viewModel.getSaveMemoryClickedEvent().observe(getViewLifecycleOwner(), saveMemoryClickedEvent -> {
            if (saveMemoryClickedEvent != null) {
                String cause = saveMemoryClickedEvent.getCause();
                switch (cause) {
                    case CAUSE_NAME:
                        navigateWithInteractiveMessage(R.id.addMemoryFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DESCRIPTION:
                        navigateWithInteractiveMessage(R.id.addMemoryFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DATE:
                        navigateWithInteractiveMessage(R.id.addMemoryFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_IMG_LIST:
                        navigateWithInteractiveMessage(R.id.addMemoryFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_COORDINATES:
                        navigateWithInteractiveMessage(R.id.mapsFragment, saveMemoryClickedEvent.getMessage());
                        break;
                    default:
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
//        navController.navigate(navDestination);
    }

    private void setUpMemoryForm(MemoryFormState form) {
        binding.nameTextView.setText(form.getMemoryName());
        binding.descriptionTextView.setText(form.getMemoryDescription());
        binding.locationNameTextView.setText(form.getPlaceLocationName());
        binding.dateTextView.setText(form.getDateOfTravel().toString());

        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                googleMap.addMarker(new MarkerOptions().position(form.getCoordinates()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(form.getCoordinates(), 8));
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
}