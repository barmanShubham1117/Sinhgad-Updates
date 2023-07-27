package com.admin.sinhgadupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.admin.sinhgadupdates.adapter.ManageBlogAdapter;
import com.admin.sinhgadupdates.adapter.ViewBlogForCommentAdapter;
import com.admin.sinhgadupdates.model.BlogModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlogAndCommentActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseStorage mStorage;
    private RecyclerView recyclerView;
    private ViewBlogForCommentAdapter adapter;
    private List<BlogModel> blogList;
    private String TAG = "BlogAndCommentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_and_comment);

        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference().child("blogs");
        mStorage=FirebaseStorage.getInstance();

        recyclerView=findViewById(R.id.bac_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        blogList=new ArrayList<BlogModel>();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blogList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BlogModel blog = dataSnapshot.getValue(BlogModel.class);
                    blogList.add(blog);
                    Log.e(TAG, "Blog Id: " + blog.getBlogId());
                    Log.e(TAG, "Title: " + blog.getTitle());
                    Log.e(TAG, "Description: " + blog.getDescription());
                    Log.e(TAG, "Image URL: " + blog.getImgURL());
                    Log.e(TAG, "Likes: " + blog.getLikes());
                }
                Collections.reverse(blogList);
                adapter = new ViewBlogForCommentAdapter(getApplicationContext(), blogList, BlogAndCommentActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load blog, try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}