package com.user.sinhgadupdates.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class RecyclerViewCommentAdapter {
//    private Context context;
//    private List<BlogModel> list;
//    private Activity activity;
//
//    public ManageBlogAdapter(Context context, List<BlogModel> list, Activity activity) {
//        this.context = context;
//        this.list = list;
//        this.activity = activity;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.row_manage_blog, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        BlogModel blog= list.get(position);
//        holder.title.setText(blog.getTitle());
//        holder.descriprtion.setText(blog.getDescription());
//
//        Picasso.get().load(blog.getImgURL()).into(holder.image);
//
//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity)
//                        .setCancelable(false)
//                        .setTitle("Delete Blog")
//                        .setMessage("Are you sure you want to delete?")
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {}
//                        })
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                FirebaseDatabase.getInstance()
//                                        .getReference("blogs")
//                                        .child(blog.getBlogId())
//                                        .removeValue();
//                                Toast.makeText(holder.deleteBtn.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                dialog.show();
//            }
//        });
//        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, AddUpdateBlogActivity.class);
//                intent.putExtra("action", "UPDATE_BLOG");
//                intent.putExtra("update_blogId", list.get(holder.getAdapterPosition()).getBlogId());
//                intent.putExtra("update_blog_title", list.get(holder.getAdapterPosition()).getTitle());
//                intent.putExtra("update_blog_description", list.get(holder.getAdapterPosition()).getDescription());
//                intent.putExtra("update_blog_imgURL", list.get(holder.getAdapterPosition()).getImgURL());
//                intent.putExtra("update_blog_likes", list.get(holder.getAdapterPosition()).getLikes());
//                activity.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView image;
//        private Button updateBtn,deleteBtn;
//        private TextView title, descriprtion;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            image=itemView.findViewById(R.id.blog_recyclerView_img);
//            title=itemView.findViewById(R.id.blog_recyclerView_title);
//            descriprtion=itemView.findViewById(R.id.blog_recyclerView_desc);
//
//            updateBtn=(Button)itemView.findViewById(R.id.blog_recyclerView_update_btn);
//            deleteBtn=(Button)itemView.findViewById(R.id.blog_recyclerView_delete_btn);
//        }
//    }
}
