package com.sergiu.libihb_java.presentation.fragment.add_memory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentAddMemoryBinding;
import com.sergiu.libihb_java.presentation.fragment.map.MapsFragment;


public class AddMemory extends Fragment implements MapsFragment.OnLatLngSelectedListener {
    private FragmentAddMemoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMemoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapsFragment mapsFragment = new MapsFragment();
        mapsFragment.setOnLatLngSelectedListener(this);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.map_container, mapsFragment)
                .commit();

        setListeners();
    }

    private void setListeners() {
        binding.pickADateMaterialButton.setOnClickListener(view -> {
            DatePicker datePicker = new DatePicker();
            datePicker.show(requireActivity().getSupportFragmentManager(), "datePiker");
        });

    }

    @Override
    public void onLatLngSelected(LatLng latLng) {
        Log.d("lat", "Received LatLng: " + latLng.toString());
    }
}