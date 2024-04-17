package com.sergiu.libihb_java.presentation.fragment.edit;

import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_COORDINATES;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DATE;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DEFAULT;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DESCRIPTION;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_IMG_LIST;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_NAME;
import static com.sergiu.libihb_java.presentation.utils.Constants.MAX_MEMORY_IMG;
import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_ID_KEY;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentEditBinding;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.presentation.adapters.EditAdapter;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;
import com.sergiu.libihb_java.presentation.fragment.addMemory.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditFragment extends Fragment {
    private static final String TAG = EditFragment.class.getSimpleName();
    private FragmentEditBinding binding;
    private EditViewModel viewModel;
    private EditAdapter editAdapter;
    private NavController navController;
    private Date date;
    private GoogleMap map;
    private long id;
    @SuppressLint("CheckResult")
    private final OnMapReadyCallback callback = googleMap -> {
        map = googleMap;
        viewModel.getMemoryById(id).subscribe(this::setUi);
    };
    private ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong(MEMORY_ID_KEY);
        }
        initializeActivityResultLauncher();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(EditFragment.this);
        initializeMap();
        setToolbar();
        setObservers();
        setListeners();
        setUpRecyclerview();
    }

    private void setObservers() {
        viewModel.getSaveEditedMemoryClickedEvent().observe(getViewLifecycleOwner(), saveEditedMemoryClickedEvent -> {
            if (saveEditedMemoryClickedEvent != null) {
                String cause = saveEditedMemoryClickedEvent.getCause();
                switch (cause) {
                    case CAUSE_NAME:
                        displayInteractiveMessage(saveEditedMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DESCRIPTION:
                        displayInteractiveMessage(saveEditedMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DATE:
                        displayInteractiveMessage(saveEditedMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_IMG_LIST:
                        displayInteractiveMessage(saveEditedMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_COORDINATES:
                        displayInteractiveMessage(saveEditedMemoryClickedEvent.getMessage());
                        break;
                    case CAUSE_DEFAULT:
                        Toast.makeText(requireContext(), getString(R.string.memory_saved), Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                }
            }
        });

    }

    private void displayInteractiveMessage(String message) {
        Snackbar snackbar = Snackbar.make(
                binding.editConstraintLayout,
                message,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.ok, v -> {
        });
        snackbar.show();
    }

    private void setListeners() {
        editAdapter = new EditAdapter(position -> editAdapter.removeImgUriFromList(position));
        binding.choosePhotosEditMaterialButton.setOnClickListener(view -> choosePhotosFromGallery());
        setChooseADate();
        setSearchBar();
    }

    private void setToolbar() {
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.setTitle(R.string.edit);
        }
    }

    private void setUpRecyclerview() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.memoryEditRecycleView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.memoryEditRecycleView.getContext(), R.drawable.overview_img_item_divider)));
        binding.memoryEditRecycleView.setAdapter(editAdapter);
    }

    private void setUi(TravelMemory memory) {
        binding.nameEditTextView.setText(memory.getMemoryName());
        binding.descriptionEditTextView.setText(memory.getMemoryDescription());
        binding.dateTextView.setText(memory.getFormattedDate());
        binding.locationNameEditTextView.setText(memory.getPlaceLocationName());
        binding.locationCountryEditTextView.setText(memory.getPlaceCountryName());
        editAdapter.updateImgUriList(memory.getImageList());
        setMarkerAtGivenLatLng(memory.getCoordinates());
    }

    private void setSearchBar() {
        binding.editSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = binding.editSearchBar.getQuery().toString();
                List<Address> addressList = null;

                Geocoder geocoder = new Geocoder(requireContext());
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (Exception e) {
                    Log.e(TAG, "onQueryTextSubmit: " + e);
                }

                if (addressList != null && !addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    Log.d(TAG, "onQueryTextSubmit: address " + address.toString());
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    setMarkerAtGivenLatLng(latLng);
                    binding.locationNameEditTextView.setText(address.getFeatureName());
                    binding.locationCountryEditTextView.setText(address.getCountryName());
                    binding.editSearchBar.clearFocus();

                    viewModel.onEvent(new MemoryFormEvent.MemoryCoordinatesChanged(latLng));
                    viewModel.onEvent(new MemoryFormEvent.MemoryPlaceCountryNameChanged(address.getCountryName()));
                    viewModel.onEvent(new MemoryFormEvent.MemoryPlaceLocationNameChanged(address.getFeatureName()));
                    viewModel.onEvent(new MemoryFormEvent.MemoryPlaceAdminNameChanged(address.getAdminArea()));
                } else {
                    Toast.makeText(requireContext(), R.string.location_not_found, Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setChooseADate() {
        binding.chooseADateMaterialButton.setOnClickListener(view -> {
            DatePicker datePicker = new DatePicker((year, month, day) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                date = calendar.getTime();
                viewModel.onEvent(new MemoryFormEvent.MemoryDateOfTravelChanged(date));
            });
            datePicker.show(requireActivity().getSupportFragmentManager(), "datePiker");
        });
    }

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_edit);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void setMarkerAtGivenLatLng(LatLng latLng) {
        if (map != null) {
            map.clear();
            map.addMarker(new MarkerOptions().position(latLng));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        }
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
                viewModel.onEvent(new MemoryFormEvent.ImgListChanged(uriStrings));
            }
        });
    }

    private void choosePhotosFromGallery() {
        pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }
}