package com.example.newsapp.utils;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.kwabenaberko.newsapilib.models.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SavedNewsFirestoreDB {

    @SuppressLint("StaticFieldLeak")
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static public List<Article> savedNewsList = new ArrayList<>();

    public static void SaveNews(Article news) {

        String savedNewsId = news.getUrl().replace("/", "+");

        db
                .collection("savedNews")
                .document(savedNewsId)
                .set(news);

        Map<String, String> map = new HashMap<>();

        map.put("userId", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        map.put("savedNewsId", savedNewsId);

        db
                .collection("userSavedNews")
                .add(map);

        savedNewsList.add(news);

    }

    static public void fetchSavedNews() {

        savedNewsList.clear();

        db
                .collection("userSavedNews")
                .whereEqualTo("userId", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            db
                                    .collection("savedNews")
                                    .document(Objects.requireNonNull(document.getString("savedNewsId")))
                                    .get()
                                    .addOnCompleteListener(task1 -> savedNewsList.add(Objects.requireNonNull(task1.getResult()).toObject(Article.class)));
                        }
                    }
                });
    }

    static public void deleteSavedNews(int position) {

        db
                .collection("userSavedNews")
                .whereEqualTo("userId", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .whereEqualTo("savedNewsId", savedNewsList.get(position).getUrl().replace("/", "+"))
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        db
                                .collection("userSavedNews")
                                .document(Objects.requireNonNull(task.getResult()).getDocuments().get(0).getId())
                                .delete();
                    }
                });

        savedNewsList.remove(position);
    }
}