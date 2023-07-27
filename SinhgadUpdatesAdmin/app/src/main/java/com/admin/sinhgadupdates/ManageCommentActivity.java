package com.admin.sinhgadupdates;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.sinhgadupdates.adapter.ManageCommentAdapter;
import com.admin.sinhgadupdates.model.BlogModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageCommentActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private RecyclerView recyclerView;
    private ManageCommentAdapter adapter;
    private List<BlogModel.Comment> commentList;
    private String TAG = "ManageCommentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_comment);

        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference().child("comments");

        recyclerView=findViewById(R.id.mca_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentList = new ArrayList<BlogModel.Comment>();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BlogModel.Comment comment = dataSnapshot.getValue(BlogModel.Comment.class);
                    commentList.add(comment);
                    Log.e(TAG, "Comment Id: " + comment.getCommentId());
                    Log.e(TAG, "Blog Id: " + comment.getBlogId());
                    Log.e(TAG, "Author Id: " + comment.getAuthorId());
                    Log.e(TAG, "Author: " + comment.getAuthor());
                    Log.e(TAG, "Comment: " + comment.getComment());
                    Log.e(TAG, "Timestamp: " + comment.getTime());
                }
                Collections.reverse(commentList);
                adapter = new ManageCommentAdapter(getApplicationContext(), commentList, ManageCommentActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageCommentActivity.this, "Failed to load blog, try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}