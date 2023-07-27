package com.admin.sinhgadupdates.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.sinhgadupdates.ManageCommentActivity;
import com.admin.sinhgadupdates.R;
import com.admin.sinhgadupdates.model.BlogModel;

import java.util.List;

public class ViewBlogForCommentAdapter extends RecyclerView.Adapter<ViewBlogForCommentAdapter.ViewHolder> {

    private Context context;
    private List<BlogModel> list;
    private Activity activity;

    public ViewBlogForCommentAdapter(Context context, List<BlogModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_blog_and_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.blogTitle.setText(list.get(position).getTitle());

        holder.viewCommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ManageCommentActivity.class);
                intent.putExtra("blogId", list.get(holder.getAdapterPosition()).getBlogId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView blogTitle;
        private ImageButton viewCommentsBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            blogTitle = itemView.findViewById(R.id.bac_blog_title);
            viewCommentsBtn = itemView.findViewById(R.id.bac_view_comment_btn);
        }
    }
}
