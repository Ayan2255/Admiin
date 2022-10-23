package com.example.admiin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admiin.databinding.ActivityAdminLoginBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin_login extends AppCompatActivity {
   ActivityAdminLoginBinding binding;
   FirebaseFirestore firestore;
   ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(Admin_login.this);
        binding.sininBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                firestore.collection("Admin").document("user1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(binding.lgnUserName.getText().toString().equals(documentSnapshot.
                                getString("name")) && binding.lgnUserPassword.getText().toString().equals(documentSnapshot.getString("password"))){
                            progressDialog.dismiss();
                            Toast.makeText(Admin_login.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Admin_login.this,Home.class);
                            startActivity(intent);
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(Admin_login.this, "Wrong information", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}