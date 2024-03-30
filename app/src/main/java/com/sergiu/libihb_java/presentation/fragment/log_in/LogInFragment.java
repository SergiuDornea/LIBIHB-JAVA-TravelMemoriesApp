package com.sergiu.libihb_java.presentation.fragment.log_in;

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
import com.sergiu.libihb_java.databinding.FragmentLogInBinding;
import com.sergiu.libihb_java.presentation.events.LogInFormEvent;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;
    private LogInViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LogInViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(LogInFragment.this);

        setTextWatchers();
        setListeners();
        setObservers();
    }


    private void setListeners() {
        binding.registerHereLinkTextView.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_authFragment));

        binding.logInButton.setOnClickListener(view -> viewModel.onEvent(LogInFormEvent.LoginClicked));
        viewModel.getNavigationEvent().observe(getViewLifecycleOwner(), navigationEvent -> {
            if (navigationEvent != null) {
                int navDestination = navigationEvent.getDestinationId();
                if (navDestination == R.id.mainFragment) {
                    navigateWithMessage(navDestination, getString(R.string.login_successful));
                } else {
                    navigateWithMessage(navDestination, getString(R.string.login_failed));
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
        });
    }

    private void setTextWatchers() {
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