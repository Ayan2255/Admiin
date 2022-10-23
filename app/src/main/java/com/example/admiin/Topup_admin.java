package com.example.admiin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admiin.databinding.FragmentTopupAdminBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import models.Topup_model;

public class Topup_admin extends Fragment {
    FragmentTopupAdminBinding binding;
    ProgressDialog progressDialog;
    FirebaseFirestore firestore;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentTopupAdminBinding.inflate(inflater,container,false);
        firestore=FirebaseFirestore.getInstance();
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setTitle("Sent data");
        progressDialog.setMessage("please wait sent data");


        binding.topupSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();;
                Topup_model model=new Topup_model(binding.diamondEditText.getText().toString(),binding.takaEditText.getText().toString(),binding.topupName.getText().toString());

                firestore.collection("Diamond").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                     progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG );
                        Fragment fragment=new Topup_fragment();
                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG );

                    }
                });

            }
        });


        return binding.getRoot();


    }
}