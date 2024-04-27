package com.sergiu.libihb_java.presentation.fragment.search;

import static com.sergiu.libihb_java.presentation.utils.Constants.EXPLORE_POSITION_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.sergiu.libihb_java.databinding.FragmentSearchBinding;
import com.sergiu.libihb_java.presentation.adapters.SearchAdapter;
import com.sergiu.libihb_java.presentation.fragment.explore.ExploreFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;
    private SearchAdapter searchAdapter;
    private NavController navController;
    private SearchView searchView;
    private final Bundle bundle = new Bundle();
    private final MutableLiveData<String> searchedName = new MutableLiveData<>("");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setObservers();
        setToolbar();
        setUpRecycleView();
    }

    private void setObservers() {
        searchedName.observe(getViewLifecycleOwner(), searchedText -> viewModel.searchCurrentMountainByName(searchedText)
                .observe(getViewLifecycleOwner(), currentMountainList -> {
                    searchAdapter.updateSearchList(currentMountainList);
                    binding.searchRecycleView.scheduleLayoutAnimation();
                }));
    }

    private void setUpRecycleView() {
        searchAdapter = new SearchAdapter(this::navigateToWithPosition);
        binding.searchRecycleView.setAdapter(searchAdapter);

        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycle_view_layout_animation);
        binding.searchRecycleView.setLayoutAnimation(animationController);
    }

    private void setToolbar() {
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.search);
        }
        setSearchView();
    }

    private void setSearchView() {
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.search_icon) {
                    menuItem.expandActionView();
                    searchView = (SearchView) menuItem.getActionView();
                    if (searchView != null) {
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                searchedName.setValue(newText);
                                return true;
                            }
                        });
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void navigateToWithPosition(String id) {
        int position = viewModel.getPositionById(id);
        bundle.putInt(EXPLORE_POSITION_KEY, position);
        ExploreFragment exploreFragment = new ExploreFragment();
        exploreFragment.setArguments(bundle);

        navController.navigate(R.id.action_searchFragment_to_exploreFragment, bundle);
    }
}