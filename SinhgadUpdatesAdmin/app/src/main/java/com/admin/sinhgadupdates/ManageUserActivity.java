package com.admin.sinhgadupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.admin.sinhgadupdates.adapter.ManageBlogAdapter;
import com.admin.sinhgadupdates.adapter.ManageUserAdapter;
import com.admin.sinhgadupdates.model.BlogModel;
import com.admin.sinhgadupdates.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageUserActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseStorage mStorage;
    private RecyclerView recyclerView;
    private ManageUserAdapter adapter;
    private List<UserModel> userList;
    private String TAG = "ManageUserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference().child("users");
        mStorage=FirebaseStorage.getInstance();

        recyclerView=findViewById(R.id.amu_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList=new ArrayList<UserModel>();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    userList.add(user);
                    Log.e(TAG, "User Id: " + user.getUserID());
                    Log.e(TAG, "Username: " + user.getUsername());
                    Log.e(TAG, "Mobile: " + user.getMobile());
                    Log.e(TAG, "Email Id: " + user.getEmailId());
                    Log.e(TAG, "Password: " + user.getPassword());
                }
                Collections.reverse(userList);
                adapter = new ManageUserAdapter(getApplicationContext(), userList, ManageUserActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageUserActivity.this, "Failed to load blog, try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}