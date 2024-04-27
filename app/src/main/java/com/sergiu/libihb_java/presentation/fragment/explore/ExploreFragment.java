package com.sergiu.libihb_java.presentation.fragment.explore;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentExploreBinding;
import com.sergiu.libihb_java.presentation.adapters.ExploreAdapter;
import com.sergiu.libihb_java.presentation.fragment.memoryoverview.OverviewFragment;
import com.sergiu.libihb_java.presentation.utils.ZoomOutFragmentAnimation;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends Fragment implements OverviewFragment.NavigateCallback {
    private ExploreViewModel viewModel;
    private NavController navController;
    private FragmentExploreBinding binding;
    private ExploreAdapter exploreAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        exploreAdapter = new ExploreAdapter();
        setObservers();
        setToolbar();
        setUpViewPager();
    }

    @Override
    public void navigationCallback(int navigationDestination) {
        if (navigationDestination == R.id.mapsFragment) {
            binding.viewPagerExplore.setCurrentItem(1);
        } else {
            binding.viewPagerExplore.setCurrentItem(0);
        }
    }

    private void setObservers() {
        viewModel.getDiscoverableMountains().observe(getViewLifecycleOwner(), currentMountainList -> exploreAdapter.updateExploreList(currentMountainList));
    }

    private void setUpViewPager() {
        binding.viewPagerExplore.setAdapter(exploreAdapter);
        MarginPageTransformer marginPageTransformer = new MarginPageTransformer(40);
        binding.viewPagerExplore.setPageTransformer(marginPageTransformer);
        binding.viewPagerExplore.setOffscreenPageLimit(3);
        binding.viewPagerExplore.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        binding.viewPagerExplore.setPageTransformer(new ZoomOutFragmentAnimation());

    }

    private void setToolbar() {
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.explore);
        }
    }
}