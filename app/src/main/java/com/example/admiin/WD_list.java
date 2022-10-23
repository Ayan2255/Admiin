package com.example.admiin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admiin.databinding.FragmentWDListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Adapters.People_Adapter;
import Adapters.WD_Adapter;
import models.User_model;
import models.Withdraw_model;


public class WD_list extends Fragment {

    FragmentWDListBinding binding;
    FirebaseFirestore firestore;
    Context context;
    ArrayList<Withdraw_model> list;
    WD_Adapter adapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentWDListBinding.inflate(inflater,container,false);
        firestore=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        adapter=new WD_Adapter(list,getContext());
        binding.recyclerviewWd.setHasFixedSize(true);
        binding.recyclerviewWd.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewWd.setAdapter(adapter);
        progressDialog=new ProgressDialog(getContext());
        receved();

        binding.deleteWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
           firestore.collection("withwdraw").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
               @Override
               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                       firestore.collection("withwdraw").document(queryDocumentSnapshot.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               progressDialog.dismiss();
                               Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               progressDialog.dismiss();
                               Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   progressDialog.dismiss();
                   Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
               }
           });
            }
        });
        return binding.getRoot();


    }

    private void receved() {

        firestore.collection("withwdraw").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for(DocumentChange documentChange : value.getDocumentChanges()){

                    if(documentChange.getType()== DocumentChange.Type.ADDED ){

                        Withdraw_model mm = documentChange.getDocument().toObject(Withdraw_model.class);
                        list.add(mm);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
}