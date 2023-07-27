package com.admin.sinhgadupdates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView user;
    private FirebaseUser mUser;
    private CardView logout, notification,usercard,postblog, manageblog, commentCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user= findViewById(R.id.ma_user);
        logout= findViewById(R.id.logout);
        notification= findViewById(R.id.notificationcard);
        usercard= findViewById(R.id.usercard);
        postblog= findViewById(R.id.postblog);
        manageblog=findViewById(R.id.manegeblog);
        commentCard=findViewById(R.id.comment_management_card);

        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        if (mUser==null){
            Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            user.setText(mUser.getEmail());
        }

        manageblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ManageBlogActivity.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(), BlogAndCommentActivity.class);
//                startActivity(intent);
            }
        });

        commentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), BlogAndCommentActivity.class);
                startActivity(intent);
            }
        });

        postblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), AddUpdateBlogActivity.class);
                startActivity(intent);
            }
        });

        usercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), UserManagementActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}