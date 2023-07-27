package com.admin.sinhgadupdates.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.sinhgadupdates.R;
import com.admin.sinhgadupdates.model.BlogModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ManageCommentAdapter extends RecyclerView.Adapter<ManageCommentAdapter.ViewHolder>{
    private Context context;
    private List<BlogModel.Comment> list;
    private Activity activity;

    public ManageCommentAdapter(Context context, List<BlogModel.Comment> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_manage_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BlogModel.Comment comment = list.get(position);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity)
                        .setCancelable(false)
                        .setTitle("Delete Blog")
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance()
                                        .getReference("comments")
                                        .child(comment.getCommentId())
                                        .removeValue();
                                Toast.makeText(holder.deleteBtn.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView author, comment, timestamp;
        private Button deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.comment_recyclerView_author);
            comment = itemView.findViewById(R.id.comment_recyclerView_comment);
            timestamp = itemView.findViewById(R.id.comment_recyclerView_timestamp);
            deleteBtn = itemView.findViewById(R.id.comment_recyclerView_delete_btn);
        }
    }
}
