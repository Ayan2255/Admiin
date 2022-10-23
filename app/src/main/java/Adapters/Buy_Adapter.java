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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admiin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Buy_model;
import models.Topup_notification_model;
import models.User_model;

public class Buy_Adapter extends RecyclerView.Adapter<Buy_Adapter.Holder> {
    ArrayList<Buy_model> list;
    Context context;
    FirebaseFirestore firestore;
    ArrayList<User_model> list2;
    People_Adapter adapter;
    String uid;
     AlertDialog alertDialog;
     ProgressDialog progressDialog;
    int coin,setCoin;

    public Buy_Adapter(ArrayList<Buy_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.buy_demoo,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("wait a moment");
         Buy_model model=list.get(position);

        holder.date.setText(model.getDate());
        holder.number.setText(model.getNumber()+" Number");
        holder.taka.setText(model.getAmount()+" Taka");
        holder.oparator.setText(model.getOperator());
        uid=model.getAuth();
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(context);
                View view=LayoutInflater.from(context).inflate(R.layout.set_coin_alartdaiologe,null);
                final  EditText editText=view.findViewById(R.id.coin_gift);
                final  Button buttonOK=view.findViewById(R.id.btn_ok);
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
                        setCoin= Integer.valueOf(editText.getText().toString());
                        recevid();

                    }
                });

                return false;
            }
        });
    }

    public void swnt() {

        Map<String,String>userMap=new HashMap<>();
       // Toast.makeText(context, ""+coin+"--------------"+setCoin, Toast.LENGTH_SHORT).show();
        userMap.put("coin", String.valueOf(coin+setCoin));
        firestore.collection("User").document(uid).set(userMap,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public  class  Holder extends RecyclerView.ViewHolder{
        TextView date,number,taka,oparator;
        View layout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            taka=itemView.findViewById(R.id.taka);
            number=itemView.findViewById(R.id.diamond_d);
            oparator=itemView.findViewById(R.id.uid);
            layout=itemView.findViewById(R.id.buy_coin_layout);
        }
    }



}
