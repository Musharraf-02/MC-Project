package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Preferences extends AppCompatActivity {
    Button sports;
    Button politics;
    Button fashion;
    Button religion;
    Button hollywood;
    Button covid19;
    Button technology;
    Button health;
    Button entertainment;
    Button world;
    static boolean sportsCheck=false;
    static boolean politicsCheck=false;
    static boolean fashionCheck=false;
    static boolean religionCheck=false;
    static boolean hollywoodCheck=false;
    static boolean covid19Check=false;
    static boolean technologyCheck=false;
    static boolean healthCheck=false;
    static boolean entertainmentCheck=false;
    static boolean worldCheck=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        sports = findViewById(R.id.sports);
        politics = findViewById(R.id.politics);
        fashion = findViewById(R.id.fashion);
        religion = findViewById(R.id.religion);
        hollywood = findViewById(R.id.hollywood);
        covid19 = findViewById(R.id.covid19);
        technology = findViewById(R.id.technology);
        health = findViewById(R.id.health);
        entertainment = findViewById(R.id.entertainment);
        world = findViewById(R.id.world);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sportsCheck) {
                    sports.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    sportsCheck=true;
                }
                else
                {
                    sports.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_sports_basketball_24, 0,0, 0);
                    sportsCheck=false;
                }
            }
        });
        politics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!politicsCheck) {
                    politics.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    politicsCheck=true;
                }
                else
                {
                    politics.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_group_24, 0,0, 0);
                    politicsCheck=false;
                }
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fashionCheck) {
                    fashion.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    fashionCheck=true;
                }
                else
                {
                    fashion.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_wc_24, 0,0, 0);
                    fashionCheck=false;
                }
            }
        });
        religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!religionCheck) {
                    religion.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    religionCheck=true;
                }
                else
                {
                    religion.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_dark_mode_24, 0,0, 0);
                    religionCheck=false;
                }
            }
        });
        hollywood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hollywoodCheck) {
                    hollywood.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    hollywoodCheck=true;
                }
                else
                {
                    hollywood.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_local_movies_24, 0,0, 0);
                    hollywoodCheck=false;
                }
            }
        });
        covid19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!covid19Check) {
                    covid19.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    covid19Check=true;
                }
                else
                {
                    covid19.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_coronavirus_24, 0,0, 0);
                    covid19Check=false;
                }
            }
        });
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!technologyCheck) {
                    technology.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    technologyCheck=true;
                }
                else
                {
                    technology.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_computer_24, 0,0, 0);
                    technologyCheck=false;
                }
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!healthCheck) {
                    health.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    healthCheck=true;
                }
                else
                {
                    health.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_health_and_safety_24, 0,0, 0);
                    healthCheck=false;
                }
            }
        });
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!entertainmentCheck) {
                    entertainment.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    entertainmentCheck=true;
                }
                else
                {
                    entertainment.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_dark_mode_24, 0,0, 0);
                    entertainmentCheck=false;
                }
            }
        });
        world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!worldCheck) {
                    world.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
                    worldCheck=true;
                }
                else
                {
                    world.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_outline_blur_circular_24, 0,0, 0);
                    worldCheck=false;
                }
            }
        });
    }
}