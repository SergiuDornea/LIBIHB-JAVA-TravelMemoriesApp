package com.sergiu.libihb_java.presentation.fragment.favorites;

import static com.sergiu.libihb_java.presentation.utils.Constants.FAVORITE_MEMORY_ID_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentFavoritesBinding;
import com.sergiu.libihb_java.presentation.activity.MainActivity;
import com.sergiu.libihb_java.presentation.adapters.FavoritesAdapter;
import com.sergiu.libihb_java.presentation.fragment.details.DetailsFragment;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private FavoritesViewModel viewModel;
    private NavController navController;
    private FavoritesAdapter favoritesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setToolbar();
        setRecycleView();
        setObserver();
    }

    private void setObserver() {
        viewModel.getFavoriteMemories().observe(getViewLifecycleOwner(), memoryList -> {
            if (memoryList.size() == 0) {
                binding.noMemoryTextView.setVisibility(View.VISIBLE);
            } else {
                binding.favoritesRecycleView.scheduleLayoutAnimation();
                favoritesAdapter.updateMemoryList(memoryList);
                binding.noMemoryTextView.setVisibility(View.GONE);
            }
        });
    }

    private void setRecycleView() {
        favoritesAdapter = new FavoritesAdapter(this::navigateToDetailsWithId);
        binding.favoritesRecycleView.setAdapter(favoritesAdapter);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycle_view_layout_animation);
        binding.favoritesRecycleView.setLayoutAnimation(animationController);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.favoritesRecycleView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.favoritesRecycleView.getContext(), R.drawable.item_divider_vertical)));
        binding.favoritesRecycleView.addItemDecoration(dividerItemDecoration);
    }

    private void setToolbar() {
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.setTitle(R.string.favorites_title);
            toolbar.setNavigationIcon(R.drawable.ic_hamburger);
            toolbar.setNavigationOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    DrawerLayout drawerLayout = mainActivity.findViewById(R.id.fragment_container_view_main);
                    if (drawerLayout != null) {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    }
                }
            });
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setDrawerLocked(false);
        }
        AppBarLayout appBarLayout = requireActivity().findViewById(R.id.main_app_bar_layout);
        if (appBarLayout != null) {
            appBarLayout.setVisibility(View.VISIBLE);
        }
    }

    private void navigateToDetailsWithId(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(FAVORITE_MEMORY_ID_KEY, id);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        navController.navigate(R.id.action_favoritesFragment_to_detailsFragment, bundle);
    }
}