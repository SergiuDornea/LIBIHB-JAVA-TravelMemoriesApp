package com.sergiu.libihb_java.presentation.fragment.details;

import static com.sergiu.libihb_java.presentation.utils.Constants.MEMORY_POSITION_KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselStrategy;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentMemoryDetailsBinding;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.presentation.adapters.DetailsCarouselAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MemoryDetailsFragment extends Fragment {
    private static final String TAG = MemoryDetailsFragment.class.getSimpleName();
    private MemoryDetailsViewModel viewModel;
    private FragmentMemoryDetailsBinding binding;
    private TravelMemory memory;
    private DetailsCarouselAdapter detailsCarouselAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MemoryDetailsViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            memory = bundle.getParcelable(MEMORY_POSITION_KEY);
            Log.d(TAG, "onCreate: memory bundle not null");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMemoryDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        setUpRecyclerView();
    }

    private void setListeners() {}


    private void setObservers() {

    }

    private void setUpRecyclerView() {
        detailsCarouselAdapter = new DetailsCarouselAdapter(memory.getImageList(), position -> Log.d(TAG, "setListeners: pos"));
        binding.photoCarouselRecycleView.setAdapter(detailsCarouselAdapter);
    }
}