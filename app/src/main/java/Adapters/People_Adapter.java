package Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admiin.Home;
import com.example.admiin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Topup_model;
import models.User_model;

public class People_Adapter extends RecyclerView.Adapter<People_Adapter.Holder> {

    ArrayList<User_model> list;
    Context context;
    FirebaseFirestore firestore;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    int coin,setCoin;
    String uid;

    public People_Adapter(ArrayList<User_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.people_demo,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("wait a moment");
        firestore=FirebaseFirestore.getInstance();
        User_model model=list.get(position);

        holder.name.setText(model.getName());
        holder.email.setText(model.getEmali());
        holder.coin.setText(model.getCoin());
        holder.phone.setText(model.getPhone());


        Picasso.get().load((String) model.getPic()).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.profile_pic);
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(context);
                View view=LayoutInflater.from(context).inflate(R.layout.set_coin_alartdaiologe,null);
                final EditText editText=view.findViewById(R.id.coin_gift);
                final Button buttonOK=view.findViewById(R.id.btn_ok);
                final  Button buttonCnl=view.findViewById(R.id.btn_cn);
                alert.setView(view);
                alertDialog =alert.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
                buttonCnl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, user.getDiamond()+"--cancel-"+user.getTaka(), Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();

                    }
                });

                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        uid=model.getUid();
                        setCoin= Integer.valueOf(editText.getText().toString());
                        recevid();

                    }
                });
                return false;
            }
        });


    }
    public void swnt() {

        Map<String,String> userMap=new HashMap<>();
        // Toast.makeText(context, ""+coin+"--------------"+setCoin, Toast.LENGTH_SHORT).show();
        userMap.put("coin", String.valueOf(coin+setCoin));

        firestore.collection("User").document(uid).set(userMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Done...", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                progressDialog.dismiss();
            }
        });

    }

    public void recevid() {
        firestore=FirebaseFirestore.getInstance();

        firestore.collection("User").document(uid.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String ss=documentSnapshot.getString("coin");
                coin= Integer.parseInt(ss);
                swnt();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends  RecyclerView.ViewHolder{
        TextView name,email,coin,phone;
        ImageView profile_pic;
        View layout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.people_demo_name);
            email=itemView.findViewById(R.id.people_demo_email);
            coin=itemView.findViewById(R.id.people_demo_coin);
            phone=itemView.findViewById(R.id.people_demo_phone);
            profile_pic=itemView.findViewById(R.id.people_demo_profile_image);
            layout=itemView.findViewById(R.id.people_demo_layout);
        }
    }
}



