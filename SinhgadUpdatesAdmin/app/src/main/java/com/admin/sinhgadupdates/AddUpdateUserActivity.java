package com.admin.sinhgadupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.admin.sinhgadupdates.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddUpdateUserActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPhone, edtPassword, edtConPassword;
    private Button btn;

    private UserModel user;
    private ProgressDialog progress;

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private String TAG = "AddUpdateUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_user);

        edtName = findViewById(R.id.user_name);
        edtPhone = findViewById(R.id.user_number);
        edtEmail = findViewById(R.id.user_email);
        edtPassword = findViewById(R.id.user_password);
        edtConPassword = findViewById(R.id.user_conpassword);
        btn = findViewById(R.id.user_addBtn);

        user = new UserModel();
        progress = new ProgressDialog(this);

        Intent intent = getIntent();
        if (intent.hasExtra("action")) {
            Log.e(TAG, "Data set: " + intent.getStringExtra("update_user_id"));
            user.setUserID(intent.getStringExtra("update_user_id"));
            user.setUsername(intent.getStringExtra("update_user_name"));
            user.setMobile(intent.getStringExtra("update_user_mobile"));
            user.setEmailId(intent.getStringExtra("update_user_email"));
            user.setPassword(intent.getStringExtra("update_user_password"));

            edtName.setText(user.getUsername());
            edtPhone.setText(user.getMobile());
            edtEmail.setText(user.getEmailId());
            edtPassword.setText(user.getPassword());
            edtConPassword.setText(user.getPassword());
            btn.setText("UPDATE");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtPassword.getText().toString();
                String conPassword = edtConPassword.getText().toString();

                user.setUsername(edtName.getText().toString());
                user.setMobile(edtPhone.getText().toString());
                user.setEmailId(edtEmail.getText().toString());
                user.setPassword(edtPassword.getText().toString());

                if (
                        user.getUsername() == null ||
                        user.getMobile() == null ||
                        user.getEmailId() == null ||
                        user.getPassword() == null ||
                        edtConPassword.getText().toString().isEmpty()
                ) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(conPassword)) {
                    Toast.makeText(AddUpdateUserActivity.this, "Password didn't matched.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Password: " + user.getPassword());
                    Log.e(TAG, "Con Password: " + edtConPassword.getText().toString());
                }
                else {
                    if (intent.hasExtra("action"))
                        updateUser();
                    else
                        addUser();
                }
            }
        });

    }

    private void updateUser() {
        progress.setTitle("Updating user info");
        progress.setMessage("Record is being updated, please wait.");
        progress.show();

        Map<String,Object> map=new HashMap<>();
        map.put("userId",user.getUserID());
        map.put("username",user.getUsername());
        map.put("mobile",user.getMobile());
        map.put("emailId",user.getEmailId());
        map.put("password",user.getPassword());

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(user.getUserID());

        reference.updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddUpdateUserActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                        resetView();
                        progress.dismiss();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: "+ e.getMessage());
                        Toast.makeText(AddUpdateUserActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
    }

    private void addUser() {
        progress.setTitle("Adding user info");
        progress.setMessage("Data is being adding, please wait.");
        progress.show();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").push();

        user.setUserID(reference.getKey());

        reference.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "User added successfully.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        resetView();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: "+ e.getMessage());
                        Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        resetView();
                    }
                });
    }

    private void resetView() {
        edtName.setText("");
        edtPhone.setText("");
        edtEmail.setText("");
        edtPassword.setText("");
        edtConPassword.setText("");
    }
}