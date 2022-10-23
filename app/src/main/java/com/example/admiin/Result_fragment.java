package com.example.admiin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admiin.databinding.FragmentResultFragmentBinding;
import com.example.admiin.databinding.FragmentTopupFragmentBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Adapters.People_Adapter;
import Adapters.User_Adapter;
import models.Topup_model;
import models.User_model;

public class Result_fragment extends Fragment {

 FragmentResultFragmentBinding binding;
FirebaseFirestore firestore;
    Context context;
    ArrayList<User_model> list;
    People_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentResultFragmentBinding.inflate(inflater,container,false);

        firestore=FirebaseFirestore.getInstance();



        /*
        *         list=new ArrayList<>();
        adapter=new User_Adapter(list,getContext());
        binding.topupRecyclerview.setHasFixedSize(true);
        binding.topupRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.topupRecyclerview.setAdapter(adapter);

         receved();
        * */

        list=new ArrayList<>();
        adapter=new People_Adapter(list,getContext());
        binding.peopleRecyclerview.setHasFixedSize(true);
        binding.peopleRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.peopleRecyclerview.setAdapter(adapter);
        receved();

        return binding.getRoot();
    }

    private void receved() {

        firestore.collection("User").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for(DocumentChange documentChange : value.getDocumentChanges()){

                    if(documentChange.getType()== DocumentChange.Type.ADDED ){

                        User_model mm = documentChange.getDocument().toObject(User_model.class);
                        list.add(mm);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });


    }
}