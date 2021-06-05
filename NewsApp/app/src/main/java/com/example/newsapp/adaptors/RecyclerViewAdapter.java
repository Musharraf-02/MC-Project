package com.example.newsapp.adaptors;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.SavedNews;
import com.example.newsapp.interfaces.OnDeleteListener;
import com.example.newsapp.interfaces.OnSaveListener;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder> {

    List<Article> newsList;
    Activity mAct;
    OnSaveListener onSaveListener;
    OnDeleteListener onDeleteListener;
    String itemType;

    public RecyclerViewAdapter(String itemType, List<Article> newsList, Activity mAct, OnSaveListener onSaveListener, OnDeleteListener onDeleteListener) {

        this.newsList = newsList;
        this.mAct = mAct;
        this.onSaveListener = onSaveListener;
        this.onDeleteListener = onDeleteListener;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaseViewHolder baseViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        if (itemType.equals(SavedNews.SAVED_NEWS_TYPE)) {
            itemView = inflater.inflate(R.layout.savednews, parent, false);
            baseViewHolder = new SavedNewsViewHolder(itemView, onDeleteListener);
        } else {
            itemView = inflater.inflate(R.layout.news, parent, false);
            baseViewHolder = new NewsViewHolder(itemView, onSaveListener);
        }

        return baseViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {

        baseViewHolder.data = newsList.get(position);

        baseViewHolder.textViewShare.setOnClickListener(v -> {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) baseViewHolder.imageViewNews.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            String path = MediaStore.Images.Media.insertImage(mAct.getContentResolver(), bitmap, "title", null);
            Uri uri = Uri.parse(path);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "*" + baseViewHolder.data.getTitle() + "*\n" + baseViewHolder.data.getUrl());
            mAct.startActivity(Intent.createChooser(intent, "Share via"));
        });

        baseViewHolder.cardView.setOnClickListener(v -> {
            mAct.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(baseViewHolder.data.getUrl())));
        });

        Picasso.get().load(baseViewHolder.data.getUrlToImage())
                .fit()
                .centerCrop()
                .into(baseViewHolder.imageViewNews);

        baseViewHolder.textViewTitle.setText(baseViewHolder.data.getTitle());
        baseViewHolder.textViewDescription.setText(baseViewHolder.data.getDescription());
        baseViewHolder.textViewSource.setText(baseViewHolder.data.getSource().getName());
        baseViewHolder.textViewDate.setText(baseViewHolder.data.getPublishedAt().substring(0, 10));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageViewNews;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewSource;
        TextView textViewDate;
        TextView textViewShare;
        Article data;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageViewNews = itemView.findViewById(R.id.newsImage);
            textViewTitle = itemView.findViewById(R.id.newsTitle);
            textViewDescription = itemView.findViewById(R.id.newsDescription);
            textViewSource = itemView.findViewById(R.id.newsSource);
            textViewDate = itemView.findViewById(R.id.newsDate);
            textViewShare = itemView.findViewById(R.id.textViewShare);
        }
    }

    public static class NewsViewHolder extends BaseViewHolder implements View.OnClickListener {

        TextView textViewSaveNews;
        OnSaveListener onSaveListener;

        public NewsViewHolder(@NonNull View itemView, OnSaveListener onSaveListener) {
            super(itemView);
            textViewSaveNews = itemView.findViewById(R.id.saveNews);
            this.onSaveListener = onSaveListener;
            textViewSaveNews.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSaveListener.OnSaveClick(getAdapterPosition());
        }
    }

    public static class SavedNewsViewHolder extends BaseViewHolder implements View.OnClickListener {

        TextView textViewDeleteSavedNews;
        OnDeleteListener onDeleteListener;

        public SavedNewsViewHolder(@NonNull View itemView, OnDeleteListener onDeleteListener) {
            super(itemView);
            textViewDeleteSavedNews = itemView.findViewById(R.id.deleteSavedNews);
            textViewDeleteSavedNews.setOnClickListener(this);
            this.onDeleteListener = onDeleteListener;
        }

        @Override
        public void onClick(View v) {
            onDeleteListener.OnDeleteClick(getAdapterPosition());
        }
    }
}