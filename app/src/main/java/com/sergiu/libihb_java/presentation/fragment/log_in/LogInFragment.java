package com.sergiu.libihb_java.presentation.fragment.log_in;

import static com.sergiu.libihb_java.presentation.utils.Constants.MIN_PASSWORD_LEN;
import static com.sergiu.libihb_java.presentation.utils.Constants.PASSWORD_PATTERN;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentLogInBinding;
import com.sergiu.libihb_java.presentation.events.LogInFormEvent;

import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;


public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;
    private LogInViewModel viewModel;

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
        viewModel =  new ViewModelProvider(this).get(LogInViewModel.class);
        viewModel.getFormState().observe(getViewLifecycleOwner(), formState -> {
            binding.emailInputTextFieldLayout.setError(formState.getEmailError());
            binding.passwordInputTextFieldLayout.setError(formState.getPasswordError());
        });

        setTextWatchers();
        setListeners();
    }


    private void setListeners(){
        binding.registerHereLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_authFragment);
            }
        });

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.onEvent(LogInFormEvent.SubmitClicked);
            }
        });
    }

    private void setTextWatchers(){
        binding.emailInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new LogInFormEvent.EmailChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.passwordInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new LogInFormEvent.PasswordChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}