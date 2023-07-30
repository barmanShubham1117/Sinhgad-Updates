package com.user.sinhgadupdates;

import static com.user.sinhgadupdates.data.UserData.userModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.user.sinhgadupdates.adapter.RecyclerViewBlogAdapter;
import com.user.sinhgadupdates.model.BlogModel;
import com.user.sinhgadupdates.model.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private RecyclerView recyclerView;
    private ImageView logoutBtn;
    private RecyclerViewBlogAdapter adapter;
    private List<BlogModel> blogList;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logUserData();

        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference().child("blogs");

        logoutBtn=findViewById(R.id.ma_logout_btn);
        recyclerView=findViewById(R.id.main_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        blogList=new ArrayList<BlogModel>();

        mPrefs = getSharedPreferences("credentials", MODE_PRIVATE);
        editor = mPrefs.edit();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(MainActivity.this)
                        .setCancelable(false)
                        .setTitle("Logout")
                        .setMessage("Are you sure, you want to logout?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putString("mobile", null);
                                editor.putString("password", null);
                                editor.apply();

                                Toast.makeText(getApplicationContext(), "You're logged out.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        });
                dialog.show();
            }
        });

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blogList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(dataSnapshot.getValue());
                    BlogModel blog = gson.fromJson(jsonElement, BlogModel.class);
                    blogList.add(blog);
                    Log.e(TAG, "Blog Id: " + blog.getBlogId());
                    Log.e(TAG, "Title: " + blog.getTitle());
                    Log.e(TAG, "Description: " + blog.getDescription());
                    Log.e(TAG, "Image URL: " + blog.getImgURL());
                    Log.e(TAG, "Likes: " + blog.getLikes());
                }
                Collections.reverse(blogList);
                adapter = new RecyclerViewBlogAdapter(getApplicationContext(), blogList, MainActivity.this, userModel.getLikedBlogs());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load blog, try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logUserData() {
        Log.e(TAG, "STATIC USER DATA: ");
        Log.e(TAG, "USERNAME: " + userModel.getUsername());
        Log.e(TAG, "MOBILE: " + userModel.getMobile());
        Log.e(TAG, "EMAIL: " + userModel.getEmailId());
        Log.e(TAG, "PASSWORD: " + userModel.getPassword());
    }
}