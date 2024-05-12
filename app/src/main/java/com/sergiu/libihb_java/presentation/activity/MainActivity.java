package com.sergiu.libihb_java.presentation.activity;

import static com.sergiu.libihb_java.presentation.utils.Constants.FAIL;
import static com.sergiu.libihb_java.presentation.utils.Constants.SUCCESS;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View headerView;
    private TextView chooseProfileImgTextView;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia;
    private final NavController.OnDestinationChangedListener destinationChangeListener =
            (navController, destination, arguments) -> setAnimationOnFirstLaunch(destination.getId());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDrawer();
        setupNavigation();
        setDrawerCallback();
        setOnBackPressedCallback();
        initializeActivityResultLauncher();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainFragment) {
            navController.navigate(R.id.mainFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
            chooseProfileImgTextView.setVisibility(View.VISIBLE);
        }
        if (id == R.id.exploreFragment) {
            navController.navigate(R.id.exploreFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
            chooseProfileImgTextView.setVisibility(View.GONE);
        }
        if (id == R.id.favoritesFragment) {
            navController.navigate(R.id.favoritesFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
            chooseProfileImgTextView.setVisibility(View.GONE);
        }
        if (id == R.id.aboutFragment) {
            navController.navigate(R.id.aboutFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
            chooseProfileImgTextView.setVisibility(View.GONE);
        }
        if (id == R.id.settingsFragment) {
            navController.navigate(R.id.settingsFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
            chooseProfileImgTextView.setVisibility(View.GONE);
        }
        if (id == R.id.sosFragment) {
            navController.navigate(R.id.sosFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
            chooseProfileImgTextView.setVisibility(View.GONE);
        }
        if (id == R.id.logOut) {
            showLogoutAlertDialog();
            chooseProfileImgTextView.setVisibility(View.GONE);
        }
        return false;
    }

    public void setDrawerLocked(boolean shouldLock) {
        if (shouldLock) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            if (actionBarDrawerToggle != null) {
                actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            }
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            if (actionBarDrawerToggle != null) {
                actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            }
        }
    }

    public void setDrawerHeaderData(String nameText, String emailText, String imgUrl) {
        headerView.findViewById(R.id.profile_loading_indicator).setVisibility(View.GONE);
        TextView name = headerView.findViewById(R.id.user_name);
        TextView email = headerView.findViewById(R.id.user_email);
        ImageView profileImg = headerView.findViewById(R.id.profile_picture);
        chooseProfileImgTextView.setVisibility(View.VISIBLE);
        name.setText(nameText);
        email.setText(emailText);
        Glide.with(this)
                .load(imgUrl)
                .placeholder(R.drawable.ic_logo_transparent2)
                .error(R.drawable.ic_logo_transparent2)
                .into(profileImg);
    }

    private void setDrawerCallback() {
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        OnBackPressedCallback drawerCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        };
        onBackPressedDispatcher.addCallback(this, drawerCallback);
    }

    private void initDrawer() {
        headerView = binding.navView.getHeaderView(0);
        chooseProfileImgTextView = headerView.findViewById(R.id.change_profile_picture_text_view);
        drawerLayout = binding.fragmentContainerViewMain;
        setSupportActionBar(binding.toolbar);
        viewModel.getUploadProfileImageEvent().observe(this, uploadProfileImageEvent -> {
            if (uploadProfileImageEvent != null) {
                if (uploadProfileImageEvent.getResponse().equals(SUCCESS)) {
                    navigateHomeWithMessage(getString(R.string.profile_photo_changed));
                } else if (uploadProfileImageEvent.getResponse().equals(FAIL)) {
                    navigateHomeWithMessage(getString(R.string.profile_photo_changed_fail));
                } else {
                    navigateHomeWithMessage(getString(R.string.profile_photo_changed_error));
                }
            }
        });
        chooseProfileImgTextView.setOnClickListener(view -> choosePhotosFromGallery());
    }

    private void navigateHomeWithMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.mainFragment);
    }

    private void initializeActivityResultLauncher() {
        pickMultipleMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri == null) {
                Log.d(TAG, "No media selected");
            } else {
                viewModel.uploadProfileImage(uri);
            }
        });
    }

    private void choosePhotosFromGallery() {
        pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(destinationChangeListener);

        NavigationView navigationView = binding.navView;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setAnimationOnFirstLaunch(int destinationId) {
        if (destinationId == R.id.mainFragment && viewModel.isFirstLaunch()) {
            slideToolbarAnimation();
            viewModel.setFirstLaunch(false);
        }
    }

    private void slideToolbarAnimation() {
        TranslateAnimation slideInRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
        );
        slideInRight.setDuration(700);
        binding.toolbar.startAnimation(slideInRight);
    }

    private void setOnBackPressedCallback() {
        ArrayList<Integer> getBackHomeDestinations = new ArrayList<Integer>() {{
            add(R.id.aboutFragment);
            add(R.id.favoritesFragment);
            add(R.id.settingsFragment);
            add(R.id.sosFragment);
            add(R.id.discoverFragment);
            add(R.id.exploreFragment);
        }};
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                int currentDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();
                if (getBackHomeDestinations.contains(currentDestinationId)) {
                    navController.navigate(R.id.mainFragment);
                } else if (currentDestinationId == R.id.editFragment) {
                    navController.popBackStack();
                } else if (currentDestinationId == R.id.searchFragment) {
                    navController.navigate(R.id.exploreFragment);
                } else {
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    private void showLogoutAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.logout)
                .setMessage(R.string.logout_alert_dialog)
                .setPositiveButton(R.string.logout, (dialog, which) -> {
                    viewModel.logOut();
                    Toast.makeText(this, getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.loginFragment);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setIcon(R.drawable.ic_logout2)
                .create()
                .show();
    }
}