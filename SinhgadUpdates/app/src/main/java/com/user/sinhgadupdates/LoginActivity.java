package com.user.sinhgadupdates;

import static com.user.sinhgadupdates.data.UserData.userModel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.user.sinhgadupdates.model.UserModel;

public class LoginActivity extends AppCompatActivity {
    private EditText edtMobile, edtPassword;
    private Button loginBtn;
    private TextView redirectRegister;
    private UserModel user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private SharedPreferences.Editor editor;
    private SharedPreferences mPrefs;
    private String mobile, password;
    private boolean isValidUser = false;
    private ProgressDialog dialog;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..");
        dialog.setCancelable(false);

        edtMobile = findViewById(R.id.la_mobile);
        edtPassword = findViewById(R.id.la_password);
        loginBtn = findViewById(R.id.la_loginBtn);
        redirectRegister = findViewById(R.id.la_redirectRegister);

        user = new UserModel();

        mPrefs = getSharedPreferences("credentials", MODE_PRIVATE);
        editor = mPrefs.edit();
        logUserPreferences();

        mobile = mPrefs.getString("mobile", null);
        password = mPrefs.getString("password", null);

        if (mobile != null && password != null) {
            dialog.show();
            loginUser();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = edtMobile.getText().toString();
                password = edtPassword.getText().toString();

                if(mobile.isEmpty()||password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill fields", Toast.LENGTH_SHORT).show();
                }else {
                    loginUser();
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

    private void loginUser() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(dataSnapshot.getValue());
                    UserModel user = gson.fromJson(jsonElement, UserModel.class);
                    assert user != null;
                    if (user.getMobile().equals(mobile) && user.getPassword().equals(password)) {
                        dialog.dismiss();
                        editor.putString("mobile", mobile);
                        editor.putString("password", password);
                        editor.apply();

                        isValidUser = true;

                        userModel = user;
                        Log.e(TAG, "STATIC USER DATA: ");
                        Log.e(TAG, "USERNAME: " + userModel.getUsername());
                        Log.e(TAG, "MOBILE: " + userModel.getMobile());
                        Log.e(TAG, "EMAIL: " + userModel.getEmailId());
                        Log.e(TAG, "PASSWORD: " + userModel.getPassword());

                        Toast.makeText(LoginActivity.this, "You're logged in.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }
                if (!isValidUser) {
                    Toast.makeText(LoginActivity.this, "Invaild crendentials.", Toast.LENGTH_SHORT).show();
                    resetView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onCancelled: " + error.getMessage());
                resetView();
            }
        });
    }

    private void logUserPreferences() {
        Log.e(TAG, "Mobile: " + mPrefs.getString("mobile", null));
        Log.e(TAG, "Password: " + mPrefs.getString("password", null));
    }

    private void resetView() {
        edtMobile.setText("");
        edtPassword.setText("");
        edtMobile.setFocusable(true);
    }
}