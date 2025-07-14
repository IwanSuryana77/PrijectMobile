package com.example.projektest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.DnsResolver;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    UserAdapter adapter;
    List<User> userList = new ArrayList<>();
    int currentPage = 1;
    int totalPages = 2;
    boolean isLoading = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_screen);

        recyclerView = findViewById(R.id.rv_user_list);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        adapter = new UserAdapter(this, userList, user -> {
            Intent result = new Intent();
            result.putExtra("selectedUser", user.first_name + " " + user.last_name);
            setResult(RESULT_OK, result);
            finish();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1;
            adapter.clear();
            fetchUsers(currentPage);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && !isLoading && currentPage < totalPages) {
                    fetchUsers(++currentPage);
                }
            }
        });

        fetchUsers(currentPage);
    }

    private void fetchUsers(int page) {
        isLoading = true;
        userList.getLast().getUsers(page, 5).enqueue(new DnsResolver.Callback<UserResponse>() {
            @Override public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    adapter.addUsers(response.body().data);
                    totalPages = response.body().total_pages;
                }
            }

            @Override public void onFailure(Call<UserResponse> call, Throwable t) {
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ThirdActivity.this, "Gagal fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }