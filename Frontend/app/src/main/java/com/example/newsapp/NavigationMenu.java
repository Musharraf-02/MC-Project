package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NavigationMenu extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.categories:
                        Toast.makeText(getApplicationContext(), "Categories", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.prefrences:
                        Toast.makeText(getApplicationContext(), "Preferences", Toast.LENGTH_SHORT).show();
                    case R.id.saved:
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    case R.id.aboutUs:
                        Toast.makeText(getApplicationContext(), "About us", Toast.LENGTH_SHORT).show();
                    case R.id.signOut:
                        Toast.makeText(getApplicationContext(), "Sign out", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START));
            Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }
}
