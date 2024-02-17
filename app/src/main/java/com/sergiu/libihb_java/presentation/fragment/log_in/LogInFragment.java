package com.sergiu.libihb_java.presentation.fragment.log_in;

import static com.sergiu.libihb_java.presentation.utils.Constants.MIN_PASSWORD_LEN;

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
    private static final Pattern PASSWORD_PATTERN =
        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");

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
                if(!validateForm()){
                    Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_mainFragment);
                }
            }
        });
    }


    private boolean validateEmail(String string){
        if(string.isEmpty()){
            binding.emailInputTextFieldLayout.setError("Email field can't be empty");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(string).matches()){
            binding.emailInputTextFieldLayout.setError("The email is not valid");
            return false;
        }
        binding.emailInputTextFieldLayout.setError(null);
        return true;
    }

    private boolean validatePassword(String string){
        if(string.isEmpty()){
            binding.passwordInputTextFieldLayout.setError("Password field can't be empty");
            return false;
        }
        if(string.length() < MIN_PASSWORD_LEN){
            binding.passwordInputTextFieldLayout.setError("Password too short, must be at least " + MIN_PASSWORD_LEN + " characters long");
            return false;
        }
        if(!PASSWORD_PATTERN.matcher(string).matches()){
            binding.passwordInputTextFieldLayout.setError("The password must include: lowercase and uppercase letters, digits, no white spaces");
            return false;
        }
        binding.passwordInputTextFieldLayout.setError(null);
        return true;
    }

    public boolean validateForm(){
        if(!validateEmail(binding.emailInputTextField.getText().toString().trim()) | !validatePassword(binding.passwordInputTextField.getText().toString().trim())){
            return true;
        }
        return false;
    }
}