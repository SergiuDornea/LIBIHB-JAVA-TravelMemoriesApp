package com.sergiu.libihb_java.presentation.fragment.add_memory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentAddMemoryBinding;

public class AddMemory extends Fragment {
    private FragmentAddMemoryBinding binding;
    private AddMemoryViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddMemoryViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMemoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
    }

    private void setListeners(){
        binding.pickADateMaterialButton.setOnClickListener(view -> {
            DatePicker datePicker = new DatePicker();
            datePicker.show(requireActivity().getSupportFragmentManager(), "datePiker");
        });
    }
}