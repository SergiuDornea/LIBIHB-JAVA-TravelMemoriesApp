package com.sergiu.libihb_java.presentation.fragment.map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentMapsBinding;

import java.util.List;

public class MapsFragment extends Fragment {
    private FragmentMapsBinding binding;
    private GoogleMap map;
    private OnLatLngSelectedListener onLatLngSelectedListener;
    private final OnMapReadyCallback callback = googleMap -> map = googleMap;

    public interface OnLatLngSelectedListener {
        void onLatLngSelected(LatLng latLng);
    }

    public void setOnLatLngSelectedListener(OnLatLngSelectedListener listener) {
        onLatLngSelectedListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeMap();
        setListeners();
    }

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void setListeners() {
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = binding.searchBar.getQuery().toString();
                List<Address> addressList = null;

                Geocoder geocoder = new Geocoder(requireContext());
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (Exception e) {
                    Log.d("err", "onQueryTextSubmit: " + e);
                }

                if (addressList != null && !addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                    binding.searchBar.clearFocus();

                    if (onLatLngSelectedListener != null) {
                        onLatLngSelectedListener.onLatLngSelected(latLng);
                    }
                } else {
                    // todo make text translatable
                    Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}