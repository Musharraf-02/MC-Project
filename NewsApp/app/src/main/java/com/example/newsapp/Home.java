package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;
import com.google.type.DateTime;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        NewsApiClient newsApiClient = new NewsApiClient("0d074aa66c774b2c8b1a6385746436c0");

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("health")
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        recyclerView.setAdapter(new RecyclerViewAdapter(response.getArticles(), Home.this));
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );

    }
}