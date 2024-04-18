package com.sergiu.libihb_java.presentation.fragment.addmemoryslider;

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
import com.sergiu.libihb_java.databinding.FragmentSliderBinding;
import com.sergiu.libihb_java.presentation.activity.MainActivity;
import com.sergiu.libihb_java.presentation.adapters.SliderAdapter;
import com.sergiu.libihb_java.presentation.fragment.addmemory.AddFragment;
import com.sergiu.libihb_java.presentation.fragment.map.MapsFragment;
import com.sergiu.libihb_java.presentation.fragment.memoryoverview.OverviewFragment;
import com.sergiu.libihb_java.presentation.utils.ZoomOutFragmentAnimation;

import java.util.ArrayList;
import java.util.List;

public class SliderFragment extends Fragment implements OverviewFragment.NavigateCallback {
    private SliderViewModel viewModel;
    private FragmentSliderBinding binding;
    private NavController navController;
    private final AddFragment addFragment = new AddFragment();
    private final MapsFragment mapsFragment = new MapsFragment();
    private final OverviewFragment overviewFragment = new OverviewFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SliderViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSliderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(SliderFragment.this);
        setListeners();
        setUpViewPager();
        overviewFragment.setNavigateCallback(this);
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
        SliderAdapter sliderAdapter = new SliderAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());

        sliderAdapter.addFragment(addFragment);
        sliderAdapter.addFragment(mapsFragment);
        sliderAdapter.addFragment(overviewFragment);

        binding.fragmentViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.fragmentViewPager.setAdapter(sliderAdapter);
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
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setDrawerLocked(true);
        }
    }
}