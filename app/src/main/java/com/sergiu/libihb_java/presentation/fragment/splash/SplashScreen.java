package com.sergiu.libihb_java.presentation.fragment.splash;

import static androidx.navigation.Navigation.findNavController;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergiu.libihb_java.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null && view.getContext() != null) {
                    navigateWithTransition(view);
                }
            }
        }, 1500);
        return view;
    }

    // a function that creates a smooth transition to the next fragment
    private void navigateWithTransition(View view) {
        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_from_right)
                .setExitAnim(R.anim.slide_out_to_left)
                .setPopEnterAnim(R.anim.slide_in_from_left)
                .setPopExitAnim(R.anim.slide_out_to_right)
                .build();
        //  navigate to the next screen after the delay
        findNavController(view).navigate(R.id.action_splashScreen_to_mainFragment, null, navOptions);
    }
}
