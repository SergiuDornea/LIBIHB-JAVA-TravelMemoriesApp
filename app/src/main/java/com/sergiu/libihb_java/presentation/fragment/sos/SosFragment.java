package com.sergiu.libihb_java.presentation.fragment.sos;

import static com.sergiu.libihb_java.presentation.utils.Constants.NO_EMERGENCY_CONTACT;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentSosBinding;
import com.sergiu.libihb_java.presentation.events.SosFromEvent;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SosFragment extends Fragment {
    private static final int SMS_PERMISSION_REQUEST_CODE = 101;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private SosViewModel viewModel;
    private FragmentSosBinding binding;
    private Pair<String, String> currentContact = new Pair<>(NO_EMERGENCY_CONTACT, NO_EMERGENCY_CONTACT);
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SosViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
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
        setToolbar();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSOSMessage();
            } else {
                Toast.makeText(requireContext(), getString(R.string.sms_permission_denied), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSOSMessage();
            } else {
                Toast.makeText(requireContext(), getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setListeners() {
        binding.sosButton.setOnClickListener(click -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
            } else {
                sendSOSMessage();
            }
        });

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
            currentContact = contact;
            if (currentContact.first.equals(NO_EMERGENCY_CONTACT) || currentContact.second.equals(NO_EMERGENCY_CONTACT)) {
                binding.sosButton.setEnabled(false);
            } else {
                binding.sosButton.setEnabled(true);
                binding.sosButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_error));
                binding.emergencyFormGroup.setVisibility(View.GONE);
                binding.sosExplained.setPadding(0, 0, 0, 300);
            }
        });

        viewModel.getFormState().observe(getViewLifecycleOwner(), sosFormState -> {
            binding.emergencyPhoneInputTextFieldLayout.setError(sosFormState.getPhoneError());
            binding.emergencyNameInputTextFieldLayout.setError(sosFormState.getNameError());
        });

        viewModel.getCallback().observe(getViewLifecycleOwner(), callback -> {
            if (callback.isSuccess()) {
                Toast.makeText(requireContext(), getString(R.string.emergency_contact_saved), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), getString(R.string.emergency_contact_failed_to_save), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSOSMessage() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            sendSMS(buildLocationMessage(location));
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.retrieve_location_failed), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void sendSMS(String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(currentContact.second, null, message, null, null);
        Toast.makeText(requireContext(), getString(R.string.sos_sent) + " " + currentContact.first, Toast.LENGTH_SHORT).show();
    }

    private String buildLocationMessage(Location location) {
        return getString(R.string.sos_message_1) + " " + currentContact.first + ". " +
                getString(R.string.sos_message_2) + getString(R.string.latitude) + " "
                + location.getLatitude() + getString(R.string.longitude) + location.getLongitude();
    }

    private void setToolbar() {
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.sos);
        }
    }
}
