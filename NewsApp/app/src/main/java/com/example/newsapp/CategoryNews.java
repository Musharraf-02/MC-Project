package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryNews extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<News> newsList = new ArrayList<News>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);
        setListAdapter();
    }
    private void setListAdapter() {
        News n1 = new News("Lorem Ipsum is simply dummy text of the printing and typesetting industry."," Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.", "Source", "27-5-2021");
        News n2 = new News("Lorem Ipsum is simply dummy text of the printing and typesetting industry."," Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.", "Source", "27-5-2021");
        News n3 = new News("Lorem Ipsum is simply dummy text of the printing and typesetting industry."," Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.", "Source", "27-5-2021");
        News n4 = new News("Lorem Ipsum is simply dummy text of the printing and typesetting industry."," Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.", "Source", "27-5-2021");
        News n5 = new News("Lorem Ipsum is simply dummy text of the printing and typesetting industry."," Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.", "Source", "27-5-2021");
        News n6 = new News("Lorem Ipsum is simply dummy text of the printing and typesetting industry."," Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.", "Source", "27-5-2021");

        newsList.addAll(Arrays.asList(new News[]{n1,n2,n3,n4,n5,n6}));
        recyclerView = findViewById(R.id.recylcerViewNewsCategory);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(newsList,CategoryNews.this) {

        };
        recyclerView.setAdapter(adapter);
    }
}