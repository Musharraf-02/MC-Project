package com.example.newsapp.others;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.adaptors.RecyclerViewAdapter;
import com.example.newsapp.firebase.SignIn;
import com.example.newsapp.utils.SavedNewsFirestoreDB;
import com.google.firebase.auth.FirebaseAuth;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.List;

public class Home extends AppCompatActivity implements RecyclerViewAdapter.OnSaveListener {

    List<Article> newsList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        NewsApiClient newsApiClient = new NewsApiClient("0d074aa66c774b2c8b1a6385746436c0");

        String QUERY = "entertainment " + " OR " +
                "politics " + " OR " +
                "pakistan " + " OR " +
                "technology " + " OR " +
                "bank " + " OR " +
                "finance " + " OR " +
                "games " + " OR " +
                "amazon " + " OR " +
                "global " + " OR " +
                "health " + " OR " +
                "covid " + " OR " +
                "funny ";

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(QUERY)
                        .language("en")
                        .sortBy("publishedAt")
                        .pageSize(100)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        newsList = response.getArticles();

                        for (int i = 0; i < newsList.size(); ++i) {
                            Article news = newsList.get(i);
                            news.setTitle(news.getTitle().trim());
                            news.setDescription(Html.fromHtml(news.getDescription()).toString());
                            if (news.getDescription().length() > 80)
                                news.setDescription(news.getDescription().trim().substring(0, 80) + "...");
                        }

                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        recyclerView.setAdapter(new RecyclerViewAdapter(newsList, Home.this, Home.this));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );

    }

    @Override
    public void OnSaveClick(int position) {

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            startActivity(new Intent(getApplicationContext(), SignIn.class));

        else {

            if (SavedNewsFirestoreDB.savedNewsList.contains(newsList.get(position)))
                Toast.makeText(getApplicationContext(), "News saved already", Toast.LENGTH_SHORT).show();

            else {
                SavedNewsFirestoreDB.SaveNews(newsList.get(position));
                Toast.makeText(getApplicationContext(), "News saved successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void headlines(View view) {

        Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
        intent.putExtra("categoryName", "Headlines");
        startActivity(intent);
    }

    public void entertainmentNews(View view) {

        Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
        intent.putExtra("categoryName", "Entertainment");
        startActivity(intent);
    }

    public void technologyNews(View view) {

        Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
        intent.putExtra("categoryName", "Technology");
        startActivity(intent);
    }

    public void healthNews(View view) {

        Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
        intent.putExtra("categoryName", "Heath");
        startActivity(intent);
    }
}