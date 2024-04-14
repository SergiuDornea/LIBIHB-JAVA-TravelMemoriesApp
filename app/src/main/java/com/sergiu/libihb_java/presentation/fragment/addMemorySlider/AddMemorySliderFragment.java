package com.sergiu.libihb_java.presentation.fragment.addMemorySlider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentAddMemorySliderBinding;
import com.sergiu.libihb_java.presentation.adapters.AddMemoryFragmentsAdapter;
import com.sergiu.libihb_java.presentation.fragment.addMemory.AddMemoryFragment;
import com.sergiu.libihb_java.presentation.fragment.map.MapsFragment;
import com.sergiu.libihb_java.presentation.fragment.memoryOverview.MemoryOverviewFragment;
import com.sergiu.libihb_java.presentation.utils.ZoomOutFragmentAnimation;

import java.util.ArrayList;
import java.util.List;

public class AddMemorySliderFragment extends Fragment implements MemoryOverviewFragment.NavigateCallback {
    private AddMemorySliderViewModel viewModel;
    private FragmentAddMemorySliderBinding binding;
    private NavController navController;
    private final AddMemoryFragment addMemoryFragment = new AddMemoryFragment();
    private final MapsFragment mapsFragment = new MapsFragment();
    private final MemoryOverviewFragment memoryOverviewFragment = new MemoryOverviewFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddMemorySliderViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMemorySliderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(AddMemorySliderFragment.this);
        setListeners();
        setUpViewPager();
        memoryOverviewFragment.setNavigateCallback(this);
        setAppBarVisibility();
    }

    @Override
    public void navigationCallback(int navigationDestination) {
        if (navigationDestination == R.id.mapsFragment) {
            binding.fragmentViewPager.setCurrentItem(1);
        } else {
            binding.fragmentViewPager.setCurrentItem(0);
        }
    }

    private void setUpViewPager() {
        AddMemoryFragmentsAdapter fragmentAdapter = new AddMemoryFragmentsAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());

        fragmentAdapter.addFragment(addMemoryFragment);
        fragmentAdapter.addFragment(mapsFragment);
        fragmentAdapter.addFragment(memoryOverviewFragment);

        binding.fragmentViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.fragmentViewPager.setAdapter(fragmentAdapter);
        binding.fragmentViewPager.setPageTransformer(new ZoomOutFragmentAnimation());

        List<Integer> tabIconList = new ArrayList<Integer>() {{
            add(R.drawable.ic_edit_square);
            add(R.drawable.ic_map);
            add(R.drawable.ic_preview);
        }};

        new TabLayoutMediator(binding.tabLayout, binding.fragmentViewPager, (tab, position) -> {
            tab.setIcon(tabIconList.get(position));
        }).attach();
    }

    private void setListeners() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.fragmentViewPager.getCurrentItem() == 0) {
                    navController.popBackStack();
                } else {
                    binding.fragmentViewPager.setCurrentItem(binding.fragmentViewPager.getCurrentItem() - 1);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    private void setAppBarVisibility() {
        AppBarLayout appBarLayout = requireActivity().findViewById(R.id.main_app_bar_layout);
        if (appBarLayout != null) {
            appBarLayout.setVisibility(View.GONE);
        }
    }
}