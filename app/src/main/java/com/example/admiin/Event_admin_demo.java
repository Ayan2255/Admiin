package com.example.admiin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admiin.databinding.FragmentEventAdminDemoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

import models.Event_model;
import models.Topup_model;


public class Event_admin_demo extends Fragment {
    FragmentEventAdminDemoBinding binding;
    ProgressDialog progressDialog;
    FirebaseFirestore firestore;
    Context context;
    StorageReference storageReference;
    Uri u, ui;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData()!=null){
            u=data.getData();
            binding.gamePic.setImageURI(u);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventAdminDemoBinding.inflate(inflater, container, false);
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Wait a moment");
        Random r=new Random();
        storageReference= FirebaseStorage.getInstance().getReference().child("Event_pic").child(String.valueOf(r));

        binding.gamePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });


        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.gameDate.getText().toString()) ||
                TextUtils.isEmpty(binding.gameName.getText().toString()) ||
                TextUtils.isEmpty(binding.joinCoin.getText().toString()) ||
                TextUtils.isEmpty(binding.prizeCoin.getText().toString()) ||
                TextUtils.isEmpty(binding.gameTime.getText().toString()) ||
                u==null){
                    Toast.makeText(getContext(), "please fullfill information", Toast.LENGTH_SHORT).show();
                }
                else {
                progressDialog.show();
                storageReference.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                
                                Event_model model = new Event_model(binding.gameName.getText().toString(),
                                        binding.joinCoin.getText().toString(), binding.prizeCoin.getText().toString(), binding.gameDate.getText().toString(), uri.toString(),binding.gameTime.getText().toString());
                                firestore.collection("Event").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();

                                        Fragment fragment=new Home_fragment();
                                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                              progressDialog.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       progressDialog.dismiss();
                    }
                });}
            }
        });


        return binding.getRoot();
    }

}