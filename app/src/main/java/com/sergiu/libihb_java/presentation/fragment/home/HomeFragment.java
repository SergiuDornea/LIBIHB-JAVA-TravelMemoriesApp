package com.sergiu.libihb_java.presentation.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sergiu.libihb_java.R;

import com.sergiu.libihb_java.databinding.FragmentHomeBinding;
import com.sergiu.libihb_java.presentation.adapters.TravelMemoryAdapter;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NavController navController;
    private HomeViewModel viewModel;
    private TravelMemoryAdapter travelMemoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        setListeners();

        setUpRecyclerview();
    }

    private void setListeners() {
        travelMemoryAdapter = new TravelMemoryAdapter(position -> Log.d("click", "onItemClick: item clicked"));

        binding.fab.setOnClickListener(v -> {
            navController.navigate(R.id.addMemorySliderFragment);
        });
    }

    private void setUpRecyclerview() {
        travelMemoryAdapter.updateMemoryList(viewModel.getDummyMemories());
        binding.memoryRecyclerView.setAdapter(travelMemoryAdapter);
        binding.memoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}