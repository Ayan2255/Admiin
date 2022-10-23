package com.example.admiin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admiin.databinding.FragmentNfBinding;

public class Nf extends Fragment {

    FragmentNfBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNfBinding.inflate(inflater,container,false);

        binding.coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Buy_list();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
            }
        });
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Admin_Settings();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
            }
        });

        binding.topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Profile_fragment();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
            }
        });


      binding.withdro.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Fragment fragment = new WD_list();
              FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
              transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
          }
      });
      binding.people.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Fragment fragment = new Result_fragment();
              FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
              transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
          }
      });

binding.event.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Fragment fragment = new Event_list();
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.hone_layout,fragment).addToBackStack(null).commit();
    }
});

        return binding.getRoot();
    }
}