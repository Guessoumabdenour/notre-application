package com.neuer.healthyphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

        NavController navController = Navigation.findNavController(
                this,R.id.fragment
        );
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}