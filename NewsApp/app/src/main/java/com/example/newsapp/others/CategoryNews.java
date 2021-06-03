package com.example.newsapp.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class CategoryNews extends AppCompatActivity implements RecyclerViewAdapter.OnSaveListener {


    List<Article> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);

        if (getIntent().getExtras() != null)
            ((TextView) findViewById(R.id.textViewCategory)).setText(getIntent().getStringExtra("categoryName"));

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
                "COVID " + " OR " +
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
                            if (news.getDescription().length() > 80)
                                news.setDescription(news.getDescription().trim().substring(0, 80) + "...");
                        }

                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        recyclerView.setAdapter(new RecyclerViewAdapter(newsList, CategoryNews.this, CategoryNews.this));
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
}