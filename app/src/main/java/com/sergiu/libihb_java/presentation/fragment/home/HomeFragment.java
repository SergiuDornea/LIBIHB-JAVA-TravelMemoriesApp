package com.sergiu.libihb_java.presentation.fragment.home;

import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_ID_BY_POSITION_KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentHomeBinding;
import com.sergiu.libihb_java.presentation.activity.MainActivity;
import com.sergiu.libihb_java.presentation.adapters.DiscoverAdapter;
import com.sergiu.libihb_java.presentation.adapters.TravelMemoryAdapter;
import com.sergiu.libihb_java.presentation.fragment.details.DetailsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;
    private NavController navController;
    private HomeViewModel viewModel;
    private TravelMemoryAdapter travelMemoryAdapter;
    private DiscoverAdapter discoverAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setObservers();
        setListeners();
        setUpRecyclerview();
        setAppBarVisibility();
    }

    private void setListeners() {
        travelMemoryAdapter = new TravelMemoryAdapter(this::navigateWithId);
        discoverAdapter = new DiscoverAdapter(currentMountainId -> Log.d(TAG, "setListeners: click on discover"));
        binding.fab.setOnClickListener(v -> navController.navigate(R.id.addMemorySliderFragment));
    }

    private void setObservers() {
        viewModel.observeDiscoverableMountains();
        viewModel.getMountainLiveData().observe(getViewLifecycleOwner(), currentMountains -> {
            int numberOfDiscoverItems = 10;
            discoverAdapter.updateMountainsToDiscoverList(currentMountains, numberOfDiscoverItems);
        });
        viewModel.getMemoriesLiveData().observe(getViewLifecycleOwner(), memoryList -> travelMemoryAdapter.updateMemoryList(memoryList));
    }

    private void navigateWithId(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(MEMORY_ID_BY_POSITION_KEY, id);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        navController.navigate(R.id.action_mainFragment_to_memoryDetailsFragment, bundle);
    }

    private void setUpRecyclerview() {
        binding.memoryRecyclerView.setAdapter(travelMemoryAdapter);
        binding.discoverRecyclerView.setAdapter(discoverAdapter);
    }

    private void setAppBarVisibility() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setDrawerLocked(false);
        }
        AppBarLayout appBarLayout = requireActivity().findViewById(R.id.main_app_bar_layout);
        if (appBarLayout != null) {
            appBarLayout.setVisibility(View.VISIBLE);
        }
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.setTitle(R.string.home_title);
        }
    }
}