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

import com.google.android.material.tabs.TabLayoutMediator;
import com.sergiu.libihb_java.databinding.FragmentAddMemorySliderBinding;
import com.sergiu.libihb_java.presentation.adapters.AddMemoryFragmentsAdapter;
import com.sergiu.libihb_java.presentation.fragment.addMemory.AddMemory;
import com.sergiu.libihb_java.presentation.fragment.memoryOverview.MemoryOverview;
import com.sergiu.libihb_java.presentation.fragment.map.MapsFragment;
import com.sergiu.libihb_java.presentation.fragment.splash.SplashScreen;
import com.sergiu.libihb_java.presentation.utils.ZoomOutFragmentAnimation;

public class AddMemorySliderFragment extends Fragment {
    private AddMemorySliderViewModel viewModel;
    private FragmentAddMemorySliderBinding binding;
    private NavController navController;

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
    }

    private void setUpViewPager() {
        AddMemoryFragmentsAdapter fragmentAdapter = new AddMemoryFragmentsAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());

        fragmentAdapter.addFragment(new AddMemory());
        fragmentAdapter.addFragment(new MapsFragment());
        fragmentAdapter.addFragment(new MemoryOverview());

        binding.fragmentViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.fragmentViewPager.setAdapter(fragmentAdapter);
        binding.fragmentViewPager.setPageTransformer(new ZoomOutFragmentAnimation());

        new TabLayoutMediator(binding.tabLayout, binding.fragmentViewPager, (tab, position) -> {
            tab.setText(Integer.toString(position + 1));
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
}