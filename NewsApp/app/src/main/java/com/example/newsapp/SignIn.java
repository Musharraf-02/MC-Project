package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignIn extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String uEmail = email.getText().toString().trim();
        String uPassword = password.getText().toString();

        if (TextUtils.isEmpty(uEmail))
            email.setError("Email is required");

        else if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches())
            email.setError("Not a valid email");

        else if (TextUtils.isEmpty(uPassword))
            password.setError("Password is missing");

        else {

            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(task -> {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            });

        }
    }

    public void skipToHomePage(View view) {
        startActivity(new Intent(getApplicationContext(), Home.class));
        finish();
    }

    public void switchToSignUpPage(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp.class));
        finish();
    }
}