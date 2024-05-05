package com.sergiu.libihb_java.presentation.fragment.home;

import static com.sergiu.libihb_java.presentation.utils.Constants.BASE_DISCOVER_TILE_COUNT;
import static com.sergiu.libihb_java.presentation.utils.Constants.DISCOVER_ID_KEY;
import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_ID_BY_POSITION_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentHomeBinding;
import com.sergiu.libihb_java.presentation.activity.MainActivity;
import com.sergiu.libihb_java.presentation.adapters.DiscoverAdapter;
import com.sergiu.libihb_java.presentation.adapters.TravelMemoryAdapter;
import com.sergiu.libihb_java.presentation.discover.DiscoverFragment;
import com.sergiu.libihb_java.presentation.fragment.details.DetailsFragment;
import com.sergiu.libihb_java.presentation.utils.ZoomOutFragmentAnimation;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NavController navController;
    private HomeViewModel viewModel;
    private TravelMemoryAdapter travelMemoryAdapter;
    private DiscoverAdapter discoverAdapter;
    private final Bundle bundle = new Bundle();

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
        setUpHorizontalScrollableLists();
        setAppBarVisibility();
    }

    private void setListeners() {
        travelMemoryAdapter = new TravelMemoryAdapter(this::navigateToDetailsWithId);
        discoverAdapter = new DiscoverAdapter(this::navigateToDiscoverWithId);
        binding.fab.setOnClickListener(v -> navController.navigate(R.id.addMemorySliderFragment));
        binding.homeAddNewMemoryMaterialButton.setOnClickListener(v -> navController.navigate(R.id.addMemorySliderFragment));
        binding.homeSosMaterialButton.setOnClickListener(v -> navController.navigate(R.id.sosFragment));
        binding.homeExploreMaterialButton.setOnClickListener(v -> navController.navigate(R.id.exploreFragment));
    }

    private void setObservers() {
        viewModel.observeDiscoverableMountains();
        viewModel.getMountainLiveData().observe(getViewLifecycleOwner(), currentMountains -> {
            Integer numberOfDiscoverItems = viewModel.getNumberOfTilesLiveData().getValue();
            discoverAdapter.updateMountainsToDiscoverList(currentMountains, numberOfDiscoverItems != null ? numberOfDiscoverItems : BASE_DISCOVER_TILE_COUNT);
        });
        viewModel.getMemoriesLiveData().observe(getViewLifecycleOwner(), memoryList -> {
            if (memoryList.size() == 0) {
                binding.memoriesGroup.setVisibility(View.GONE);
                binding.initialDashboardConstraintLayout.setVisibility(View.VISIBLE);
            } else {
                binding.memoryRecyclerView.scheduleLayoutAnimation();
                binding.memoriesGroup.setVisibility(View.VISIBLE);
                binding.initialDashboardConstraintLayout.setVisibility(View.GONE);
                travelMemoryAdapter.updateMemoryList(memoryList);
            }
        });
        viewModel.getUserDetails().observe(getViewLifecycleOwner(), user -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).setDrawerHeaderData(user.getName(), user.getEmail(), user.getProfilePicture());
            }
        });
    }

    private void navigateToDetailsWithId(Long id) {
        bundle.putLong(MEMORY_ID_BY_POSITION_KEY, id);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        navController.navigate(R.id.action_mainFragment_to_memoryDetailsFragment, bundle);
    }

    private void navigateToDiscoverWithId(String id) {
        bundle.putString(DISCOVER_ID_KEY, id);
        DiscoverFragment discoverFragment = new DiscoverFragment();
        discoverFragment.setArguments(bundle);

        navController.navigate(R.id.action_mainFragment_to_discoverFragment, bundle);
    }

    private void setUpHorizontalScrollableLists() {
        binding.memoryRecyclerView.setAdapter(travelMemoryAdapter);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycle_view_layout_animation);
        binding.memoryRecyclerView.setLayoutAnimation(animationController);

        binding.discoverViewPager.setAdapter(discoverAdapter);
        MarginPageTransformer marginPageTransformer = new MarginPageTransformer(getMarginAccordingToScreenDensity());
        binding.discoverViewPager.setPageTransformer(marginPageTransformer);
        binding.discoverViewPager.setOffscreenPageLimit(3);
        binding.discoverViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        binding.discoverViewPager.setPageTransformer(new ZoomOutFragmentAnimation());
    }

    private void setAppBarVisibility() {
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.setTitle(R.string.home_title);
            toolbar.setNavigationIcon(R.drawable.ic_hamburger);
            toolbar.setNavigationOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    DrawerLayout drawerLayout = mainActivity.findViewById(R.id.fragment_container_view_main);
                    if (drawerLayout != null) {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    }
                }
            });
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setDrawerLocked(false);
        }
        AppBarLayout appBarLayout = requireActivity().findViewById(R.id.main_app_bar_layout);
        if (appBarLayout != null) {
            appBarLayout.setVisibility(View.VISIBLE);
        }
    }

    private int getMarginAccordingToScreenDensity() {
        float density = getResources().getDisplayMetrics().density;
        return (int) (10 * density);
    }
}