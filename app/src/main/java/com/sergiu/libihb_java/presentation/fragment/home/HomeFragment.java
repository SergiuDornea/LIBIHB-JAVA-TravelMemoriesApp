package com.sergiu.libihb_java.presentation.fragment.home;

import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_POSITION_KEY;

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
import com.sergiu.libihb_java.presentation.fragment.details.MemoryDetailsFragment;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
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
        setObservers();
        setListeners();
        setUpRecyclerview();
    }

    private void setListeners() {
        travelMemoryAdapter = new TravelMemoryAdapter(this::navigateWithPosition);
        binding.fab.setOnClickListener(v -> navController.navigate(R.id.addMemorySliderFragment));
    }

    private void setObservers() {
        viewModel.getMemoriesLiveData().observe(getViewLifecycleOwner(), memoryList -> travelMemoryAdapter.updateMemoryList(memoryList));
    }

    private void navigateWithPosition(int position) {
        Bundle bundle = new Bundle();
        Log.d("HomeFragment", "navigateWithPosition: value " + viewModel.getMemoriesLiveData().getValue().get(position).getImageList());
        bundle.putParcelable(MEMORY_POSITION_KEY, Objects.requireNonNull(viewModel.getMemoriesLiveData().getValue()).get(position));

        MemoryDetailsFragment memoryDetailsFragment = new MemoryDetailsFragment();
        memoryDetailsFragment.setArguments(bundle);

        navController.navigate(R.id.action_mainFragment_to_memoryDetailsFragment, bundle);
    }

    private void setUpRecyclerview() {
        binding.memoryRecyclerView.setAdapter(travelMemoryAdapter);
        binding.memoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}