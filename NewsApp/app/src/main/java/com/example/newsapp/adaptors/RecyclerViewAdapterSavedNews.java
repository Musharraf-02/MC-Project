package com.example.newsapp.adaptors;

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

import com.example.newsapp.R;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterSavedNews extends RecyclerView.Adapter<RecyclerViewAdapterSavedNews.MyViewHolder> {

    public List<Article> savedNewsList;
    Activity mAct;
    OnDeleteListener onDeleteListener;

    public RecyclerViewAdapterSavedNews(List<Article> savedNewsList, Activity mAct, OnDeleteListener onDeleteListener) {
        this.savedNewsList = savedNewsList;
        this.mAct = mAct;
        this.onDeleteListener = onDeleteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savednews, parent, false);
        return new MyViewHolder(itemView, onDeleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.data = savedNewsList.get(position);

        holder.cardView.setOnClickListener(v -> mAct.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(holder.data.getUrl()))));

        Picasso.get().load(holder.data.getUrlToImage())
                .fit()
                .centerCrop()
                .into(holder.imageViewNews);

        holder.textViewTitle.setText(holder.data.getTitle());
        holder.textViewDescription.setText(holder.data.getDescription());
        holder.textViewSource.setText(holder.data.getSource().getName());
        holder.textViewDate.setText(holder.data.getPublishedAt().substring(0, 10));
    }

    public void removeAt(int position) {
        savedNewsList.remove(position);
        notifyDataSetChanged();
    }

    public void addNews(Article news) {
        savedNewsList.add(news);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return savedNewsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        ImageView imageViewNews;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewSource;
        TextView textViewDate;
        TextView textViewDeleteSavedNews;
        Article data;
        OnDeleteListener onDeleteListener;

        public MyViewHolder(@NonNull View itemView, OnDeleteListener onDeleteListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageViewNews = itemView.findViewById(R.id.newsImage);
            textViewTitle = itemView.findViewById(R.id.newsTitle);
            textViewDescription = itemView.findViewById(R.id.newsDescription);
            textViewSource = itemView.findViewById(R.id.newsSource);
            textViewDate = itemView.findViewById(R.id.newsDate);
            textViewDeleteSavedNews = itemView.findViewById(R.id.deleteSavedNews);
            textViewDeleteSavedNews.setOnClickListener(this);
            this.onDeleteListener = onDeleteListener;
        }

        @Override
        public void onClick(View v) {
            onDeleteListener.OnDeleteClick(getAdapterPosition());
        }
    }

    public interface OnDeleteListener {
        void OnDeleteClick(int position);
    }
}