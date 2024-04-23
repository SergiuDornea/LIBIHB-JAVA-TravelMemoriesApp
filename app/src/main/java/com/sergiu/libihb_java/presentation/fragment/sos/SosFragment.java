package com.sergiu.libihb_java.presentation.fragment.sos;

import static com.sergiu.libihb_java.presentation.utils.Constants.NO_EMERGENCY_CONTACT;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sergiu.libihb_java.databinding.FragmentSosBinding;
import com.sergiu.libihb_java.presentation.events.SosFromEvent;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SosFragment extends Fragment {
    private static final int SMS_PERMISSION_REQUEST_CODE = 101;
    private static final String MESSAGE = "Ma arunc in cap cu asta pe fundal cate o data!";
    private SosViewModel viewModel;
    private FragmentSosBinding binding;
    private Pair<String, String> currentContact = new Pair<>(NO_EMERGENCY_CONTACT, NO_EMERGENCY_CONTACT);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SosViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObservers();
        setListeners();
    }

    private void setListeners() {
        binding.sosButton.setOnClickListener(click -> sendSMS());
        binding.saveEmergencyContactMaterialButton.setOnClickListener(click -> viewModel.onEvent(SosFromEvent.SubmitClicked));

        binding.emergencyNameInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new SosFromEvent.NameChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.emergencyPhoneInputTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEvent(new SosFromEvent.PhoneChanged(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setObservers() {
        viewModel.observeEmergencyContact().observe(getViewLifecycleOwner(), contact -> {
            if (contact.equals(NO_EMERGENCY_CONTACT)) {
                binding.sosButton.setEnabled(false);
            } else {
                binding.currentEmergencyContactPhone.setText(contact.second);
                binding.currentEmergencyContactName.setText(contact.first);
                currentContact = contact;
                binding.sosButton.setEnabled(true);
            }
        });

        viewModel.getFormState().observe(getViewLifecycleOwner(), sosFormState -> {
            binding.emergencyPhoneInputTextFieldLayout.setError(sosFormState.getPhoneError());
            binding.emergencyNameInputTextFieldLayout.setError(sosFormState.getNameError());
        });

        viewModel.getCallback().observe(getViewLifecycleOwner(), callback -> {
            if (callback.isSuccess()) {
                Toast.makeText(requireContext(), "Emergency contact saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Failed to save the contact", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSMS() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(currentContact.second, null, MESSAGE, null, null);
            Toast.makeText(requireContext(), "Message sent!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            } else {
                Toast.makeText(requireContext(), "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}