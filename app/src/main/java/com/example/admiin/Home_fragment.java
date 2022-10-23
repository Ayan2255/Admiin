package com.example.admiin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admiin.databinding.FragmentHomeFragmentBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Adapters.Event_Adapter;
import models.Event_model;


public class Home_fragment extends Fragment {
    FragmentHomeFragmentBinding binding;
    FirebaseFirestore firestore;
    Context context;
    ArrayList<Event_model>list;
    Event_Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeFragmentBinding.inflate(inflater,container,false);

        binding.eventSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new Event_admin_demo();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
            }
        });
        firestore=FirebaseFirestore.getInstance();


        list=new ArrayList<>();
        adapter=new Event_Adapter(list,getContext());
        binding.eventRecycelerview.setHasFixedSize(true);
      //  binding.eventRecycelerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.eventRecycelerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.eventRecycelerview.setAdapter(adapter);
        receved();

        return binding.getRoot();

    }

    public void receved() {

        firestore.collection("Event").orderBy("e_date", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for(DocumentChange documentChange : value.getDocumentChanges()){

                    if(documentChange.getType()== DocumentChange.Type.ADDED ){

                        Event_model  mm = documentChange.getDocument().toObject(Event_model.class);
                        list.add(mm);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}