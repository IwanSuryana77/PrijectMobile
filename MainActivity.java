package com.example.projektest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editName, editText;
    Button btnCheck, btnNext;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.et_name);
        editText = findViewById(R.id.btn_next);
        btnCheck = findViewById(R.id.btn_check);
        btnNext = findViewById(R.id.btn_next);

        btnCheck.setOnClickListener(v -> {
            String text = editText.getText().toString().replaceAll("\\s+", "").toLowerCase();
            String reverse = new StringBuilder(text).reverse().toString();
            boolean isPalindrome = text.equals(reverse);

            new AlertDialog.Builder(this)
                    .setTitle("Hasil")
                    .setMessage(isPalindrome ? "Palindrome" : "Bukan Palindrome")
                    .setPositiveButton("OK", null)
                    .show();
        });

        btnNext.setOnClickListener(v -> {
            String name = editName.getText().toString();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("username", name);
            startActivity(intent);
        });
    }
}