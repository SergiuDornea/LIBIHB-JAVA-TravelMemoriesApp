package com.sergiu.libihb_java.presentation.fragment.explore;

import static com.sergiu.libihb_java.presentation.utils.Constants.EXPLORE_POSITION_KEY;
import static com.sergiu.libihb_java.presentation.utils.Constants.INVALID_POSITION;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.MaterialToolbar;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentExploreBinding;
import com.sergiu.libihb_java.presentation.adapters.ExploreAdapter;
import com.sergiu.libihb_java.presentation.fragment.memoryoverview.OverviewFragment;
import com.sergiu.libihb_java.presentation.utils.ZoomOutFragmentAnimation;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends Fragment implements OverviewFragment.NavigateCallback {
    private ExploreViewModel viewModel;
    private NavController navController;
    private FragmentExploreBinding binding;
    private ExploreAdapter exploreAdapter;
    private final MutableLiveData<Integer> position = new MutableLiveData<>(INVALID_POSITION);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position.setValue(bundle.getInt(EXPLORE_POSITION_KEY));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        exploreAdapter = new ExploreAdapter();
        setObservers();
        setToolbar();
        setUpViewPager();
    }

    @Override
    public void navigationCallback(int navigationDestination) {
        if (navigationDestination == R.id.mapsFragment) {
            binding.viewPagerExplore.setCurrentItem(1);
        } else {
            binding.viewPagerExplore.setCurrentItem(0);
        }
    }

    private void setObservers() {
        viewModel.getDiscoverableMountains().observe(getViewLifecycleOwner(), currentMountainList -> {
            exploreAdapter.updateExploreList(currentMountainList);
            if (position.getValue() != INVALID_POSITION) {
                binding.viewPagerExplore.post(() -> {
                    binding.viewPagerExplore.setCurrentItem(position.getValue());
                });
            }
        });
    }

    private void setUpViewPager() {
        binding.viewPagerExplore.setAdapter(exploreAdapter);
        binding.viewPagerExplore.setPageTransformer(new ZoomOutFragmentAnimation());
    }

    private void setToolbar() {
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.explore_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.search_icon) {
                    navController.navigate(R.id.action_exploreFragment_to_searchFragment);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.explore);
        }
    }
}