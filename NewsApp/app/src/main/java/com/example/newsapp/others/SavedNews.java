package com.example.newsapp.others;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.R;
import com.example.newsapp.adaptors.RecyclerViewAdapterSavedNews;
import com.example.newsapp.utils.SavedNewsFirestoreDB;

public class SavedNews extends AppCompatActivity implements RecyclerViewAdapterSavedNews.OnDeleteListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapterSavedNews adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textViewResfresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        textViewResfresh = findViewById(R.id.textViewRefresh);
        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(() -> {

            if (!SavedNewsFirestoreDB.savedNewsList.isEmpty()) {
                textViewResfresh.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
            swipeRefreshLayout.setRefreshing(false);
        });

        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new RecyclerViewAdapterSavedNews(SavedNewsFirestoreDB.savedNewsList, SavedNews.this, SavedNews.this);

        if (SavedNewsFirestoreDB.savedNewsList.isEmpty())
            textViewResfresh.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnDeleteClick(int position) {

        SavedNewsFirestoreDB.deleteSavedNews(position);
        Toast.makeText(getApplicationContext(), "News deleted successfully", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
}