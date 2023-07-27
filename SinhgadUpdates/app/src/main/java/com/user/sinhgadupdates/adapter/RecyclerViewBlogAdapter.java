package com.user.sinhgadupdates.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.user.sinhgadupdates.R;
import com.user.sinhgadupdates.model.BlogModel;
import com.user.sinhgadupdates.model.UserModel;

import java.util.List;

public class RecyclerViewBlogAdapter extends RecyclerView.Adapter<RecyclerViewBlogAdapter.ViewHolder> {
        private Context context;
        private List<BlogModel> list;
        private List<UserModel.LikedBlog> likedBlogs;
        private Activity activity;

        public RecyclerViewBlogAdapter(Context context, List<BlogModel> list, Activity activity, List<UserModel.LikedBlog> likedBlogs) {
            this.context = context;
            this.list = list;
            this.activity = activity;
            this.likedBlogs = likedBlogs;
        }

        public RecyclerViewBlogAdapter(Context context, List<BlogModel> list, Activity activity) {
            this.context = context;
            this.list = list;
            this.activity = activity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_blog, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            BlogModel blog= list.get(position);
            holder.title.setText(blog.getTitle());
            holder.descriprtion.setText(blog.getDescription());

            Picasso.get().load(blog.getImgURL()).into(holder.image);

//            if ()

            holder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.isLiked) {
                        holder.icLike.setImageResource(R.drawable.ic_like_disable);
                        holder.isLiked = false;
                    }
                    else {
                        holder.icLike.setImageResource(R.drawable.ic_like_enable);
                        holder.isLiked = true;
                    }
                }
            });
            holder.commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ShapeableImageView image;
            private MaterialCardView likeBtn,commentBtn;
            private ShapeableImageView icLike;
            private TextView title, descriprtion;
            private boolean isLiked = false;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.row_blog_image);
                title = itemView.findViewById(R.id.row_blog_title);
                descriprtion = itemView.findViewById(R.id.row_blog_description);

                likeBtn = itemView.findViewById(R.id.row_blog_likeBtn);
                commentBtn = itemView.findViewById(R.id.row_blog_commentBtn);
                icLike = itemView.findViewById(R.id.row_like);
            }
        }
}
