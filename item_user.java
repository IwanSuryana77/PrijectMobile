package com.example.projektest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public UserAdapter(Context context, List<User> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.txtName.setText(user.first_name + " " + user.last_name);
        holder.txtEmail.setText(user.email);
        holder.wait (context).load(user.avatar).into(holder.imgAvatar);
        holder.itemView.setOnClickListener(v -> listener.onUserClick(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUsers(List<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clear() {
        userList.clear();
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail;
        ImageView imgAvatar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.et_name);
            txtEmail = itemView.findViewById(R.id.tv_email);
            imgAvatar = itemView.findViewById(R.id.top_bar);
        }
    }
}