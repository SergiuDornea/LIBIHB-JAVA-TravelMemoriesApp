package com.sergiu.libihb_java.presentation.fragment.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentRegisterBinding;
import com.sergiu.libihb_java.presentation.events.RegisterFormEvent;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(RegisterFragment.this);
        setTextWatchers();
        setListeners();
        setObservers();
    }

    private void setListeners() {
        binding.logInHereLinkTextView.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_authFragment_to_logInFragment));

        binding.registerButton.setOnClickListener(view -> viewModel.onEvent(RegisterFormEvent.RegisterClicked));

        viewModel.getNavigationEvent().observe(getViewLifecycleOwner(), navigationEvent -> {
            if (navigationEvent != null) {
                int navDestination = navigationEvent.getDestinationId();
                if (navDestination == R.id.mainFragment) {
                    navigateWithMessage(navDestination, getString(R.string.registration_successful));
                } else if (navDestination == R.id.logInFragment) {
                    navigateWithMessage(navDestination, getString(R.string.login_failed_after_register_successful));
                } else {
                    navigateWithMessage(navDestination, getString(R.string.registration_failed));
                }
            }
        });
    }

    private void navigateWithMessage(int navDestination, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        navController.navigate(navDestination);
    }


    private void setObservers() {
        viewModel.getFormState().observe(getViewLifecycleOwner(), formState -> {
            binding.emailInputTextFieldLayout.setError(formState.getEmailError());
            binding.passwordInputTextFieldLayout.setError(formState.getPasswordError());
            binding.nameInputTextFieldLayout.setError(formState.getNameError());
            binding.phoneInputTextFieldLayout.setError(formState.getPhoneError());
            binding.repeatPasswordInputTextFieldLayout.setError(formState.getRepeatPasswordError());
        });
    }

    private void setTextWatchers() {
        binding.emailInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new RegisterFormEvent.EmailChanged(s.toString()));
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
                viewModel.onEvent(new RegisterFormEvent.PasswordChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.repeatPasswordInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new RegisterFormEvent.RepeatedPasswordChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.nameInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new RegisterFormEvent.NameChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.phoneInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new RegisterFormEvent.PhoneChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}