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

import com.example.admiin.databinding.FragmentEventListBinding;
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

import Adapters.Event_notificatiaon_adapter;
import models.Event_notification;


public class Event_list extends Fragment {
     FragmentEventListBinding binding;
     FirebaseFirestore firestore;
     ArrayList<Event_notification>list;
     Context context;
     ProgressDialog  progressDialog;
     Event_notificatiaon_adapter adapter;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentEventListBinding.inflate(inflater,container,false);

        firestore= FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getContext());
        binding.eventTimeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                firestore.collection("Event_notification").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            firestore.collection("Event_notification").document(queryDocumentSnapshot.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                 progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Try gamin", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

        list=new ArrayList<>();
        adapter=new Event_notificatiaon_adapter(list,getContext());
        binding.eventRecycelerview.setHasFixedSize(true);
        //  binding.eventRecycelerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.eventRecycelerview.setLayoutManager(new LinearLayoutManager(context));
        binding.eventRecycelerview.setAdapter(adapter);
        receved();
        return binding.getRoot();
    }

    private void receved() {


        firestore.collection("Event_notification").orderBy("current_date", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for(DocumentChange documentChange : value.getDocumentChanges()){

                    if(documentChange.getType()== DocumentChange.Type.ADDED ){

                        Event_notification mm = documentChange.getDocument().toObject(Event_notification.class);
                        list.add(mm);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}