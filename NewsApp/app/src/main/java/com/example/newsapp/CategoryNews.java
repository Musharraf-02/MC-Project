package com.example.newsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.adaptors.RecyclerViewAdapter;
import com.example.newsapp.firebaseauth.SignIn;
import com.example.newsapp.helpers.SavedNewsFirestoreHelper;
import com.example.newsapp.interfaces.NewsSaveListener;
import com.example.newsapp.interfaces.OnSaveListener;
import com.example.newsapp.models.Category;
import com.google.firebase.auth.FirebaseAuth;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.List;

public class CategoryNews extends AppCompatActivity implements OnSaveListener, NewsSaveListener {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Article> newsList;
    Category newsCategory;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("category")) {
            newsCategory = (Category) getIntent().getSerializableExtra("category");
        } else {
            finish();
        }

        TextView textView = findViewById(R.id.textViewCategory);
        textView.setText(newsCategory.getName().substring(0, 1).toUpperCase() + newsCategory.getName().substring(1));

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        String query = getQuery(newsCategory.getKeywords());

        String sortBy = "relevancy";

        if (newsCategory.getName().equals("Headlines")) {
            sortBy = "publishedAt";
        }

        fetchNewsFromAPI(query, sortBy);

        String finalSortBy = sortBy;

        swipeRefreshLayout.setOnRefreshListener(() -> fetchNewsFromAPI(query, finalSortBy));
    }

    String getQuery(List<String> keywords) {

        StringBuilder query = new StringBuilder();
        int i = 0;
        for (; i < keywords.size(); ++i) {
            query.append(keywords.get(i)).append(" OR ");
        }
        return query.toString().substring(0, query.toString().length() - 4);
    }

    public void fetchNewsFromAPI(String query, String sortBy) {

        String API_KEY = "0d074aa66c774b2c8b1a6385746436c0";
        NewsApiClient newsApiClient = new NewsApiClient(API_KEY);
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(query)
                        .language("en")
                        .sortBy(sortBy)
                        .pageSize(100)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        newsList = response.getArticles();

                        for (int i = 0; i < newsList.size(); ++i) {
                            Article news = newsList.get(i);

                            if (news.getTitle() == null || news.getDescription() == null || news.getPublishedAt() == null
                                    || news.getUrl() == null || news.getUrlToImage() == null || news.getSource() == null
                                    || news.getSource().getName() == null) {
                                newsList.remove(i--);
                            } else {

                                news.setTitle(news.getTitle().trim());
                                news.setDescription(Html.fromHtml(news.getDescription().trim()).toString());

                                if (news.getDescription().length() > 90)
                                    news.setDescription(news.getDescription().substring(0, 90) + "...");
                            }
                        }
                        adapter = new RecyclerViewAdapter("News", newsList, CategoryNews.this, CategoryNews.this, null);
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                });
    }

    @Override
    public void OnSaveClick(int position) {

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
        } else {
            SavedNewsFirestoreHelper.SaveNews(newsList.get(position), this::onNewsSave);
        }
    }

    @Override
    public void onNewsSave(boolean saveStatus) {
        Toast.makeText(this, saveStatus ? "New saved successfully" : "News saved already", Toast.LENGTH_SHORT).show();
    }
}