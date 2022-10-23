package com.example.admiin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admiin.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    FirebaseFirestore firestore;
    Button button;
    ActivityHomeBinding binding;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.hone_layout,new Home_fragment()).commit();
        bottomMenu();



    }

    private void bottomMenu() {

        binding.homeNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch (i){
                    case R.id.home:
                        fragment=new Home_fragment();
                        binding.homeText.setTextColor(Color.parseColor("#533483"));
                        break;
                    case R.id.result:
                        fragment=new Result_fragment();
                        binding.homeText.setTextColor(Color.parseColor("#0F3460"));
                        break;
                    case R.id.topup:
                        fragment=new Topup_fragment();
                        binding.homeText.setTextColor(Color.parseColor("#0F3460"));
                        break;
                    case R.id.Notificatioon:
                        fragment=new Nf();
                        binding.homeText.setTextColor(Color.parseColor("#E94560"));
                        break;



                }
                getSupportFragmentManager().beginTransaction().replace(R.id.hone_layout,fragment).commit();
            }
        });

    }


}