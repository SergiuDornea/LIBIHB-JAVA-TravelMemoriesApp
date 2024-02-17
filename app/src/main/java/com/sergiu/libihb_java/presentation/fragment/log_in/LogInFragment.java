package com.sergiu.libihb_java.presentation.fragment.log_in;

import static com.sergiu.libihb_java.presentation.utils.Constants.MIN_PASSWORD_LEN;
import static com.sergiu.libihb_java.presentation.utils.Constants.PASSWORD_PATTERN;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentLogInBinding;

import java.util.regex.Pattern;

public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerHereLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_authFragment);
            }
        });

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!validateForm()){
//                    Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_mainFragment);
//                }
            }
        });
    }
}