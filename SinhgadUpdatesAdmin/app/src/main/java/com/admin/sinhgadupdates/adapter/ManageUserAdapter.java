package com.admin.sinhgadupdates.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.sinhgadupdates.AddUpdateBlogActivity;
import com.admin.sinhgadupdates.AddUpdateUserActivity;
import com.admin.sinhgadupdates.R;
import com.admin.sinhgadupdates.model.BlogModel;
import com.admin.sinhgadupdates.model.UserModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ManageUserAdapter extends RecyclerView.Adapter<ManageUserAdapter.ViewHolder> {

    private Context context;
    private List<UserModel> list;
    private Activity activity;

    public ManageUserAdapter(Context context, List<UserModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_manage_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user = list.get(position);
        holder.txtUsername.setText(user.getUsername());
        holder.txtMobile.setText(user.getMobile());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity)
                        .setCancelable(false)
                        .setTitle("Delete User")
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance()
                                        .getReference("users")
                                        .child(user.getUserID())
                                        .removeValue();
                                Toast.makeText(holder.deleteBtn.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.show();
            }
        });
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddUpdateUserActivity.class);
                intent.putExtra("action", "UPDATE_USER");
                intent.putExtra("update_user_id", list.get(holder.getAdapterPosition()).getUserID());
                intent.putExtra("update_user_name", list.get(holder.getAdapterPosition()).getUsername());
                intent.putExtra("update_user_mobile", list.get(holder.getAdapterPosition()).getMobile());
                intent.putExtra("update_user_email", list.get(holder.getAdapterPosition()).getEmailId());
                intent.putExtra("update_user_password", list.get(holder.getAdapterPosition()).getPassword());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtUsername, txtMobile;
        private Button updateBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsername = itemView.findViewById(R.id.ma_txtUsername);
            txtMobile = itemView.findViewById(R.id.ma_txtMobile);
            updateBtn = itemView.findViewById(R.id.ma_updateUserBtn);
            deleteBtn = itemView.findViewById(R.id.ma_deleteUserBtn);
        }
    }
}
