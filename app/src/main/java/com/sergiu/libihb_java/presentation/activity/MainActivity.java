package com.sergiu.libihb_java.presentation.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
        setupNavigation();
        setDrawerCallback();
        setOnBackPressedCallback();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainFragment) {
            navController.navigate(R.id.mainFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.aboutFragment) {
            navController.navigate(R.id.aboutFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.contactFragment) {
            navController.navigate(R.id.contactFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.settingsFragment) {
            navController.navigate(R.id.settingsFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.shareFragment) {
            navController.navigate(R.id.shareFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.logOutFragment) {
            navController.navigate(R.id.logOutFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
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

    private void initUI() {
        drawerLayout = binding.fragmentContainerViewMain;
        setSupportActionBar(binding.toolbar);
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        NavigationView navigationView = binding.navView;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setOnBackPressedCallback() {
        ArrayList<Integer> getBackHomeDestinations = new ArrayList<Integer>() {{
            add(R.id.aboutFragment);
            add(R.id.contactFragment);
            add(R.id.settingsFragment);
            add(R.id.shareFragment);
        }};
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                int currentDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();
                if (getBackHomeDestinations.contains(currentDestinationId)) {
                    navController.navigate(R.id.mainFragment);
                } else {
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }
}