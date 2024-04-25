package com.sergiu.libihb_java.presentation.fragment.settings;

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

import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentSettingsBinding;
import com.sergiu.libihb_java.presentation.events.SosFromEvent;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {
    private static final String METRIC = "metric";
    private static final String IMPERIAL = "imperial";
    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar();
        setObservers();
        setTextWatchers();
        setListeners();
    }

    private void setListeners() {
        binding.settingsEmergencySaveButton.setOnClickListener(click -> viewModel.onEvent(SosFromEvent.SubmitClicked));
        binding.settingsSaveButton.setOnClickListener(click -> viewModel.onSettingsSave());
        binding.settingsCelsiusRadioButton.setOnClickListener(click -> viewModel.setUnitOfMeasurement(METRIC));
        binding.settingsFahrenheitRadioButton.setOnClickListener(click -> viewModel.setUnitOfMeasurement(IMPERIAL));
        binding.settingsNumberOfDiscoverTilesSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser) {
                viewModel.setExploreNumberTilesSetting((int) value);
            }
        });
    }

    private void setObservers() {
        viewModel.getSosFormState().observe(getViewLifecycleOwner(), sosFormState -> {
            binding.settingsPhoneInputTextFieldLayout.setError(sosFormState.getPhoneError());
            binding.settingsNameInputTextFieldLayout.setError(sosFormState.getNameError());
        });

        viewModel.getExploreNumberTilesSetting().observe(getViewLifecycleOwner(), number ->
                binding.settingsNumberOfDiscoverTilesSlider.setValue(number));
        viewModel.getUnitOfMeasurement().observe(getViewLifecycleOwner(), unit -> {
            if (unit.equals(METRIC)) {
                binding.settingsCelsiusRadioButton.setChecked(true);
            }
            if (unit.equals(IMPERIAL)) {
                binding.settingsFahrenheitRadioButton.setChecked(true);
            }
        });
    }

    private void setTextWatchers() {
        binding.settingsNameInputTextField.addTextChangedListener(new TextWatcher() {
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
        binding.settingsPhoneInputTextField.addTextChangedListener(new TextWatcher() {
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

    private void setToolbar() {
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.settings_title);
        }
    }
}