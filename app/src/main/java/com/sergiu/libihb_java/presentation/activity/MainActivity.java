package com.sergiu.libihb_java.presentation.activity;

import android.os.Bundle;
import android.view.MenuItem;

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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();

        setupNavigation();
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         int id = item.getItemId();
         if (id == R.id.mainFragment) {
            navController.navigate(R.id.mainFragment);
             drawerLayout.closeDrawer(GravityCompat.START);
         }
        if (id == R.id.shareFragment) {
            navController.navigate(R.id.shareFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}