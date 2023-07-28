package com.user.sinhgadupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.sinhgadupdates.model.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtMobile, edtEmail, edtPassword, edtConPassword;
    private Button registerBtn;
    private TextView redirectLogin;
    private UserModel user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;
    private String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.ra_username);
        edtMobile = findViewById(R.id.ra_mobile);
        edtEmail = findViewById(R.id.ra_email);
        edtPassword = findViewById(R.id.ra_password);
        edtConPassword = findViewById(R.id.ra_conPassword);

        registerBtn = findViewById(R.id.ra_registerBtn);
        redirectLogin = findViewById(R.id.redirect_login);

        user = new UserModel();

        mPrefs = getSharedPreferences("credentials", MODE_PRIVATE);
        editor = mPrefs.edit();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setUsername(edtUsername.getText().toString());
                user.setMobile(edtMobile.getText().toString());
                user.setEmailId(edtEmail.getText().toString());
                user.setPassword(edtPassword.getText().toString());

                if(
                        user.getUsername().isEmpty() ||
                        user.getMobile().isEmpty() ||
                        user.getEmailId().isEmpty() ||
                        user.getPassword().isEmpty() ||
                        edtConPassword.getText().toString().isEmpty()
                ){
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (!user.getPassword().equals(edtConPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Password didn't matched.", Toast.LENGTH_SHORT).show();
                }
                else {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users").push();

                    user.setUserID(reference.getKey());

                    reference.setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("mobile", user.getMobile());
                                    editor.putString("password", user.getPassword());
                                    editor.apply();

                                    Toast.makeText(RegisterActivity.this, "You're registered successfully.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onFailure: " + e.getMessage());
                                }
                            });
                }
            }
        });

        redirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}