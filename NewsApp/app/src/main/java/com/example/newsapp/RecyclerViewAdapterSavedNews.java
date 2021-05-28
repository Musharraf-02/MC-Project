package com.example.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class  RecyclerViewAdapterSavedNews extends RecyclerView.Adapter<RecyclerViewAdapterSavedNews.MyViewHolder>{
    List<News> newsList;
    Activity mAct;
    public RecyclerViewAdapterSavedNews(List<News> news, Activity mAct) {
        this.newsList = news;
        this.mAct = mAct;
    }
    @NonNull
    @Override
    public RecyclerViewAdapterSavedNews.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savednews, parent, false);
        return new RecyclerViewAdapterSavedNews.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterSavedNews.MyViewHolder holder, int position) {
        holder.data=newsList.get(position);
        holder.textViewTitle.setText(holder.data.getTitle());
        holder.textViewDescription.setText(holder.data.getDescription());
        holder.textViewSource.setText(holder.data.getSource());
        holder.textViewDate.setText(holder.data.getDate());
        holder.imageViewNews.setImageResource(R.drawable.bbc);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewNews;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewSource;
        TextView textViewDate;
        News data;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewNews = itemView.findViewById(R.id.newsImageSaved);
            textViewTitle = itemView.findViewById(R.id.newsTitleSaved);
            textViewDescription = itemView.findViewById(R.id.newsDescriptionSaved);
            textViewSource = itemView.findViewById(R.id.newsSourceSaved);
            textViewDate = itemView.findViewById(R.id.newsDateSaved);
        }
    }

}
