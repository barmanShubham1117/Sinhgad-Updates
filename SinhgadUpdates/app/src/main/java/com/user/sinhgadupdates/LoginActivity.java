package com.user.sinhgadupdates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.sinhgadupdates.model.UserModel;

public class LoginActivity extends AppCompatActivity {
    private EditText edtMobile, edtPassword;
    private Button loginBtn;
    private TextView redirectRegister;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtMobile = findViewById(R.id.la_mobile);
        edtPassword = findViewById(R.id.la_password);
        loginBtn = findViewById(R.id.la_loginBtn);
        redirectRegister = findViewById(R.id.la_redirectRegister);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = edtMobile.getText().toString();
                String password = edtPassword.getText().toString();

                if(mobile.isEmpty()||password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill fields", Toast.LENGTH_SHORT).show();
                }else {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                UserModel user = dataSnapshot.getValue(UserModel.class);
                                assert user != null;
                                if (user.getMobile().equals(mobile) && user.getPassword().equals(password)) {
                                    Toast.makeText(LoginActivity.this, "You're logged in.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Invaild crendentials.", Toast.LENGTH_SHORT).show();
                                    resetView();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onCancelled: " + error.getMessage());
                            resetView();
                        }
                    });
                }
            }
        });

        redirectRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register activity
                Intent intent= new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void resetView() {
        edtMobile.setText("");
        edtPassword.setText("");
        edtMobile.setFocusable(true);
    }
}