package com.sergiu.libihb_java.presentation.fragment.splash;

import static androidx.navigation.Navigation.findNavController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;

import com.google.android.material.appbar.AppBarLayout;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentSplashBinding;
import com.sergiu.libihb_java.presentation.activity.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
public class SplashScreen extends Fragment {
    private SplashViewModel viewModel;
    private FragmentSplashBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        disableNavDrawerAnAppBar();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (binding.getRoot().getContext() != null) {
                navigateAccordingToUserSession(binding.getRoot());
            }
        }, 1500);
        return binding.getRoot();
    }

    public void navigateAccordingToUserSession(View view) {
        viewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
            if (isLoggedIn) {
                navigateWithTransition(view, R.id.mainFragment);
            } else {
                navigateWithTransition(view, R.id.loginFragment);
            }
        });
    }

    private void navigateWithTransition(View view, int destination) {
        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_from_right)
                .setExitAnim(R.anim.slide_out_to_left)
                .setPopEnterAnim(R.anim.slide_in_from_left)
                .setPopExitAnim(R.anim.slide_out_to_right)
                .build();
        findNavController(view).navigate(destination, null, navOptions);
    }

    private void disableNavDrawerAnAppBar() {
        AppBarLayout appBar = requireActivity().findViewById(R.id.main_app_bar_layout);
        if (appBar != null) {
            appBar.setVisibility(View.GONE);
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setDrawerLocked(true);
        }
    }
}
