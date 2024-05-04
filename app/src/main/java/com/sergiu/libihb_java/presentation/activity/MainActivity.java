package com.sergiu.libihb_java.presentation.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
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
    private MainViewModel viewModel;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
        if (id == R.id.exploreFragment) {
            navController.navigate(R.id.exploreFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.favoritesFragment) {
            navController.navigate(R.id.favoritesFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.aboutFragment) {
            navController.navigate(R.id.aboutFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.settingsFragment) {
            navController.navigate(R.id.settingsFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.sosFragment) {
            navController.navigate(R.id.sosFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.logOut) {
            showLogoutAlertDialog();
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

    public void setDrawerHeaderText(String nameText, String emailText) {
        View headerView = binding.navView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.user_name);
        TextView email = headerView.findViewById(R.id.user_email);
        name.setText(nameText);
        email.setText(emailText);
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