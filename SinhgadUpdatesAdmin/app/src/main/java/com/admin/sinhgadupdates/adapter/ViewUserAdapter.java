package com.admin.sinhgadupdates.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.sinhgadupdates.R;
import com.admin.sinhgadupdates.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ViewUserAdapter extends RecyclerView.Adapter<ViewUserAdapter.ViewHolder>{

    private Context context;
    private List<UserModel> list;
    private Activity activity;

    public ViewUserAdapter(Context context, List<UserModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_view_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewUserAdapter.ViewHolder holder, int position) {
        UserModel user = list.get(position);

        holder.username.setText(user.getUsername());
        holder.mobile.setText(user.getMobile());
        holder.emailId.setText(user.getEmailId());
        holder.password.setText(user.getPassword());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username, mobile, emailId, password;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.vua_username);
            mobile = itemView.findViewById(R.id.vua_mobile);
            emailId = itemView.findViewById(R.id.vua_emailId);
            password = itemView.findViewById(R.id.vua_password);
        }
    }
}
