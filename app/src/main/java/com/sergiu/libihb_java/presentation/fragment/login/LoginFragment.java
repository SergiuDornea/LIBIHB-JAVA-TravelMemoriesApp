package com.sergiu.libihb_java.presentation.fragment.login;

import static com.sergiu.libihb_java.presentation.utils.Constants.DEFAULT_SCREEN_DESTINATION;
import static com.sergiu.libihb_java.presentation.utils.ScreenSizeUtil.isScreenSmall;

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
import com.sergiu.libihb_java.databinding.FragmentLoginBinding;
import com.sergiu.libihb_java.presentation.events.LoginFormEvent;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(LoginFragment.this);
        setTextWatchers();
        setObservers();
        setListeners();
    }

    private void setListeners() {
        binding.registerHereLinkTextView.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_authFragment));
        binding.logInButton.setOnClickListener(view -> viewModel.onEvent(LoginFormEvent.LoginClicked));
    }

    private void navigateWithMessage(int navDestination, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        if (navDestination == DEFAULT_SCREEN_DESTINATION) {
            return;
        }
        navController.navigate(navDestination);
    }

    private void setObservers() {
        setUpFormErrorAccordingToScreenSize();
        viewModel.getNavigationEvent().observe(getViewLifecycleOwner(), navigationEvent -> {
            if (navigationEvent != null) {
                int navDestination = navigationEvent.getDestinationId();
                if (navDestination == R.id.mainFragment) {
                    navigateWithMessage(navDestination, getString(R.string.login_successful));
                } else {
                    navigateWithMessage(DEFAULT_SCREEN_DESTINATION, getString(R.string.incorrect_email_or_password));
                }
            }
        });
    }

    private void setUpFormErrorAccordingToScreenSize() {
        if (isScreenSmall(requireContext())) {
            viewModel.observeFormState().observe(getViewLifecycleOwner(), formState -> {
                binding.emailInputTextField.setError(formState.getEmailError());
                binding.passwordInputTextField.setError(formState.getPasswordError());
                binding.passwordInputTextFieldLayout.setPasswordVisibilityToggleEnabled(false);
            });
        } else {
            viewModel.observeFormState().observe(getViewLifecycleOwner(), formState -> {
                binding.emailInputTextFieldLayout.setError(formState.getEmailError());
                binding.passwordInputTextFieldLayout.setError(formState.getPasswordError());
            });
        }
    }

    private void setTextWatchers() {
        binding.emailInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new LoginFormEvent.EmailChanged(s.toString()));
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
                viewModel.onEvent(new LoginFormEvent.PasswordChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}