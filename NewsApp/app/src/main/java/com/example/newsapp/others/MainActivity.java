package com.example.newsapp.others;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.newsapp.R;
import com.example.newsapp.firebase.SignIn;
import com.example.newsapp.utils.SavedNewsFirestoreDB;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
            finish();
        }

        SavedNewsFirestoreDB.fetchSavedNews();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    moveToHomePage();
                    break;
                case R.id.preferences:
                    moveToPreferences();
                    break;
                case R.id.saved:
                    savedNews();
                    break;
                case R.id.aboutUs:
                    moveToAboutUs();
                    break;
                case R.id.signOut:
                    logout();
                    break;
            }
            return true;
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), SignIn.class));
        finish();
    }

    public void savedNews() {

        if (FirebaseAuth.getInstance().getUid() == null)
            startActivity(new Intent(getApplicationContext(), SignIn.class));

        else
            startActivity(new Intent(getApplicationContext(), SavedNews.class));

    }

    public void moveToHomePage() {
        startActivity(new Intent(getApplicationContext(), Home.class));
    }

    public void moveToAboutUs() {
        startActivity(new Intent(getApplicationContext(), AboutUs.class));
    }

    public void moveToPreferences() {
        startActivity(new Intent(getApplicationContext(), Preferences.class));
    }
}