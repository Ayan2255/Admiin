package com.example.admiin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admiin.databinding.FragmentAdminSettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Admin_Settings extends Fragment {
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    FragmentAdminSettingsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminSettingsBinding.inflate(inflater,container,false);

        firestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait a moment");

       binding.submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressDialog.show();;
               Map<String,String> userMap = new HashMap<>();
               userMap.put("password",binding.newPaassword.getText().toString());
        /*firestore.collection("User").document(auth.getUid())
                .set(userMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
        */
               if (TextUtils.isEmpty(binding.newPaassword.getText().toString())){
                   Toast.makeText(getContext(),"Please Enter new password", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
               }
               else {
                   firestore.collection("Admin").document("user1").set(userMap,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           progressDialog.dismiss();
                           Toast.makeText(getContext(),"Ok", Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(getContext(),Admin_login.class);
                           startActivity(intent);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           progressDialog.dismiss();
                           Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });}

           }
       });
        return binding.getRoot();
    }
}