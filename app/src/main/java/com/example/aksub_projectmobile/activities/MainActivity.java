package com.example.aksub_projectmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aksub_projectmobile.R;
import com.example.aksub_projectmobile.fragments.FavoritesFragment;
import com.example.aksub_projectmobile.fragments.ProfileFragment;
import com.example.aksub_projectmobile.fragments.QuotesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    };
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);

        bottomNavigationView = findViewById(R.id.nav_bottom_main);
        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.quotes){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new QuotesFragment()).commit();
                return true;
            }else if(item.getItemId() == R.id.favorites){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new FavoritesFragment()).commit();
                return true;
            }else if(item.getItemId() == R.id.profile){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new ProfileFragment()).commit();
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.quotes);
    }
}