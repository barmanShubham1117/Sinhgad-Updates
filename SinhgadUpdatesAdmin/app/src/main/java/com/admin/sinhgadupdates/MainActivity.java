package com.admin.sinhgadupdates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CardView logout, notification,usercard,postblog, manageblog;
//    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView= findViewById(R.id.currentAccount);
        logout= findViewById(R.id.logout);
        notification= findViewById(R.id.notificationcard);
        usercard= findViewById(R.id.usercard);
        postblog= findViewById(R.id.postblog);
        manageblog=findViewById(R.id.manegeblog);

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
//                Intent intent=new Intent(getApplicationContext(), com.example.sinhgadupdatesadmin.notification.class);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "You are now on Notification Page", Toast.LENGTH_SHORT).show();
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
//                FirebaseAuth.getInstance().signOut();
//                Intent intent= new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}