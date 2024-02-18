package com.sergiu.libihb_java.presentation.fragment.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentRegisterBinding;
import com.sergiu.libihb_java.presentation.events.RegisterFormEvent;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getFormState().observe(getViewLifecycleOwner(), formState -> {
            binding.emailInputTextFieldLayout.setError(formState.getEmailError());
            binding.passwordInputTextFieldLayout.setError(formState.getPasswordError());
            binding.nameInputTextFieldLayout.setError(formState.getNameError());
            binding.phoneInputTextFieldLayout.setError(formState.getPhoneError());
            binding.repeatPasswordInputTextFieldLayout.setError(formState.getRepeatPasswordError());
        });

        setTextWatchers();
        setListeners();
    }

    private void setListeners() {
        binding.logInHereLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_authFragment_to_logInFragment);
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.onEvent(RegisterFormEvent.SubmitClicked);
            }
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