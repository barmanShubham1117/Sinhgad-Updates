package com.admin.sinhgadupdates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserManagementActivity extends AppCompatActivity {

//    private FirebaseAuth auth;
//    private FirebaseUser user;
    private CardView adduser, viewUser,updateUser, deleteuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

//        auth= FirebaseAuth.getInstance();
//        user= auth.getCurrentUser();

        adduser= findViewById(R.id.add_user);
        viewUser= findViewById(R.id.search_user);
        updateUser=findViewById(R.id.update_user);
//        deleteuser=findViewById(R.id.delete_user);

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddUpdateUserActivity.class);
                startActivity(intent);
            }
        });

//        deleteuser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), deleteUser.class);
//                startActivity(intent);
//            }
//        });
//
        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ViewUserActivity.class);
                startActivity(intent);
            }
        });

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ManageUserActivity.class);
                startActivity(intent);
            }
        });

    }
}