package com.example.newsapp.adaptors;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Article> newsList;
    Activity mAct;
    OnSaveListener onSaveListener;

    public RecyclerViewAdapter(List<Article> newsList, Activity mAct, OnSaveListener onSaveListener) {
        this.newsList = newsList;
        this.mAct = mAct;
        this.onSaveListener = onSaveListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news, parent, false);
        return new MyViewHolder(itemView, onSaveListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.data = newsList.get(position);

        holder.textViewShare.setOnClickListener(v -> {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageViewNews.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            String path = MediaStore.Images.Media.insertImage(mAct.getContentResolver(), bitmap, "title", null);
            Uri uri = Uri.parse(path);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "*" + holder.data.getTitle() + "*\n" + holder.data.getUrl());
            mAct.startActivity(Intent.createChooser(intent, "Share via"));
        });

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
        return newsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView imageViewNews;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewSource;
        TextView textViewDate;
        TextView textViewSaveNews;
        Article data;
        OnSaveListener onSaveListener;
        TextView textViewShare;

        public MyViewHolder(@NonNull View itemView, OnSaveListener onSaveListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageViewNews = itemView.findViewById(R.id.newsImage);
            textViewTitle = itemView.findViewById(R.id.newsTitle);
            textViewDescription = itemView.findViewById(R.id.newsDescription);
            textViewSource = itemView.findViewById(R.id.newsSource);
            textViewDate = itemView.findViewById(R.id.newsDate);
            textViewSaveNews = itemView.findViewById(R.id.saveNews);
            textViewShare = itemView.findViewById(R.id.textViewShare);
            this.onSaveListener = onSaveListener;
            textViewSaveNews.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSaveListener.OnSaveClick(getAdapterPosition());
        }
    }

    public interface OnSaveListener {
        void OnSaveClick(int position);
    }
}