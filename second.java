package com.example.projektest;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    TextView txtWelcome, txtName, txtSelectedUser;
    Button btnPickUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtWelcome = findViewById(R.id.txtWelcome);
        txtName = findViewById(R.id.txtName);
        txtSelectedUser = findViewById(R.id.txtSelectedUser);
        btnPickUser = findViewById(R.id.btnPickUser);

        // Ambil nama dari intent
        String name = getIntent().getStringExtra("username");
        txtName.setText(name);

        // Setup result launcher
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String selectedUser = result.getData().getStringExtra("selectedUser");
                        txtSelectedUser.setText(selectedUser);
                    }
                }
        );

        btnPickUser.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            launcher.launch(intent);
        });
    }
}