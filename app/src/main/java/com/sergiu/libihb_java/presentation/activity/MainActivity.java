package com.sergiu.libihb_java.presentation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sergiu.libihb_java.R;

import dagger.hilt.android.AndroidEntryPoint;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}