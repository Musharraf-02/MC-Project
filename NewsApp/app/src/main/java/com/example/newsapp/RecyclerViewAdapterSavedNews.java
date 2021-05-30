package com.example.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class  RecyclerViewAdapterSavedNews extends RecyclerView.Adapter<RecyclerViewAdapterSavedNews.MyViewHolder> {

    List<Article> newsArticles;
    Activity mAct;

    public RecyclerViewAdapterSavedNews(List<Article> news, Activity mAct) {
        this.newsArticles = news;
        this.mAct = mAct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.data = newsArticles.get(position);

        holder.cardView.setOnClickListener(v -> {
            mAct.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(holder.data.getUrl())));
        });

        Picasso.get().load(holder.data.getUrlToImage())
                .fit()
                .centerCrop()
                .into(holder.imageViewNews);

        holder.textViewTitle.setText(holder.data.getTitle());
        holder.textViewDescription.setText(holder.data.getDescription());
        holder.textViewSource.setText(holder.data.getSource().getName());
        holder.textViewDate.setText(holder.data.getPublishedAt().substring(0, 10));
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewNews;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewSource;
        TextView textViewDate;
        Article data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageViewNews = itemView.findViewById(R.id.newsImage);
            textViewTitle = itemView.findViewById(R.id.newsTitle);
            textViewDescription = itemView.findViewById(R.id.newsDescription);
            textViewSource = itemView.findViewById(R.id.newsSource);
            textViewDate = itemView.findViewById(R.id.newsDate);
        }
    }
}