package com.sergiu.libihb_java.presentation.discover;

import static com.sergiu.libihb_java.presentation.utils.Constants.DISCOVER_ID_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentDiscoverBinding;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;
import com.sergiu.libihb_java.presentation.activity.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiscoverFragment extends Fragment {
    private DiscoverViewModel viewModel;
    private NavController navController;
    private FragmentDiscoverBinding binding;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString(DISCOVER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setToolbar();
        setObservers();
    }

    private void setObservers() {
        viewModel.observeCurrentMountainById(id).observe(getViewLifecycleOwner(), this::setUi);
    }

    private void setUi(CurrentMountain currentMountain) {
        Glide.with(requireContext())
                .load(currentMountain.getMountainImg())
                .placeholder(R.drawable.img_memory_placeholder)
                .error(R.drawable.i_unavailable_img)
                .into(binding.discoverBackgroundImageView);
        Glide.with(requireContext())
                .load(currentMountain.getCountryFlagImg())
                .placeholder(R.drawable.img_memory_placeholder)
                .error(R.drawable.i_unavailable_img)
                .into(binding.locationFlagImageView);
        binding.discoverTitleTextView.setText(currentMountain.getName());
        binding.discoverAltitudeTextView.setText(currentMountain.getAltitude());
        binding.discoverFirstClimberTextView.setText(currentMountain.getFirstClimber());
        binding.discoverFirstClimbedDateTextView.setText(currentMountain.getFirstClimbedDate());
        binding.discoverLocationTextView.setText(currentMountain.getLocation());
        binding.discoverDescriptionTextView.setText(currentMountain.getDescription());
    }

    private void setToolbar() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setDrawerLocked(true);
        }
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.setTitle(R.string.discover_title);
            toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
            toolbar.setNavigationOnClickListener(v -> navController.popBackStack());
        }
    }
}