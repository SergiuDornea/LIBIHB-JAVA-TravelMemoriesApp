package com.sergiu.libihb_java.presentation.fragment.details;

import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_ID_BY_POSITION_KEY;
import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_ID_KEY;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentDetailsBinding;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.model.weather.CurrentWeather;
import com.sergiu.libihb_java.presentation.activity.MainActivity;
import com.sergiu.libihb_java.presentation.adapters.DetailsAdapter;
import com.sergiu.libihb_java.presentation.fragment.edit.EditFragment;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends Fragment {
    private DetailsViewModel viewModel;
    private SupportMapFragment mapFragment;
    private NavController navController;
    private FragmentDetailsBinding binding;
    private DetailsAdapter detailsCarouselAdapter;
    private TravelMemory currentMemory;
    private MaterialToolbar toolbar;
    private long id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong(MEMORY_ID_BY_POSITION_KEY);
            viewModel.setCurrentId(id);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(DetailsFragment.this);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_details);
        setToolbar();
        setObservers();
        setListeners();
        setUpRecyclerView();
    }

    @SuppressLint("CheckResult")
    private void setObservers() {
        viewModel.getMemoryById(id).subscribe(memory -> {
            currentMemory = memory;
            setUi(memory);
            if (getView() != null) {
                viewModel.getCurrentWeatherByLatAndLong(memory.getCoordinates())
                        .observe(getViewLifecycleOwner(), this::setCurrentWeatherUi);
            }
        });

        viewModel.getIsMemoryInFavorites().observe(getViewLifecycleOwner(), this::updateFavoriteButtonState);
    }

    private void updateFavoriteButtonState(Boolean isFavorite) {
        MenuItem favoriteIcon = toolbar.getMenu().findItem(R.id.favourite_memory);
        if (favoriteIcon != null) {
            favoriteIcon.setIcon(isFavorite ? R.drawable.ic_favorite_full : R.drawable.ic_favorite_blank);
        }
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

    private void setToolbar() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setDrawerLocked(true);
        }
        toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.setTitle(R.string.details_title);
            toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
            toolbar.setNavigationOnClickListener(v -> navController.popBackStack());
            setMenuHost();
        }
    }

    private void setMenuHost() {
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.details_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.favourite_memory) {
                    menuItem.setOnMenuItemClickListener(favItem -> {
                        viewModel.toggleFavoriteIcon();
                        return true;
                    });
                }
                if (menuItem.getItemId() == R.id.delete_memory) {
                    showDeleteAlertDialog();
                    return true;
                }

                if (menuItem.getItemId() == R.id.edit_memory) {
                    navigateWithId(id);
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void setUi(TravelMemory memory) {
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
        detailsCarouselAdapter.setImgUriList(memory.getImageList());
    }

    private void setCurrentWeatherUi(CurrentWeather currentWeather) {
        binding.weatherMainTextView.setText(currentWeather.getMain());
        binding.weatherTempTextView.setText(currentWeather.getTemp());
        binding.sunriseTextView.setText(currentWeather.getSunrise());
        binding.sunsetTextView.setText(currentWeather.getSunset());
        binding.feelsLikeActualDataTextView.setText(currentWeather.getFeelsLike());
        binding.windTextView.setText(currentWeather.getSpeed());
        binding.maxTextView.setText(currentWeather.getTempMax());
        binding.minTextView.setText(currentWeather.getTempMin());
        binding.humidityTextView.setText(currentWeather.getHumidity());
    }

    private void setUpRecyclerView() {
        detailsCarouselAdapter = new DetailsAdapter(this::zoomClickedPictureIn);
        binding.photoCarouselRecycleView.setAdapter(detailsCarouselAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.photoCarouselRecycleView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.photoCarouselRecycleView.getContext(), R.drawable.item_divider)));
        binding.photoCarouselRecycleView.addItemDecoration(dividerItemDecoration);
    }

    private void zoomClickedPictureIn(int position) {
    }

    private void showDeleteAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.delete_memory)
                .setMessage(R.string.delete_alert_dialog)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    viewModel.deleteMemory(currentMemory);
                    Toast.makeText(requireContext(), getString(R.string.memory_deleted), Toast.LENGTH_SHORT).show();
                    navController.popBackStack();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setIcon(R.drawable.ic_delete)
                .create()
                .show();
    }

    private void navigateWithId(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(MEMORY_ID_KEY, id);
        EditFragment editFragment = new EditFragment();
        editFragment.setArguments(bundle);

        navController.navigate(R.id.action_detailsFragment_to_editFragment, bundle);
    }
}