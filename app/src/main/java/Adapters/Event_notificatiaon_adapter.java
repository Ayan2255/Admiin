package Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admiin.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import models.Event_notification;
import models.Topup_model;
import models.User_model;

public class Event_notificatiaon_adapter extends RecyclerView.Adapter<Event_notificatiaon_adapter.Holder> {
    FirebaseFirestore firestore;
    ArrayList<Event_notification> list;
    Context context;
    int coin,setCoin;
    String uid;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    public Event_notificatiaon_adapter(ArrayList<Event_notification> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_list_demo,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Event_notification model= list.get(position);
        firestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("wait a moment");
        holder.name.setText(model.getName().toString());
        holder.uid.setText(model.getUid().toString());
        holder.date.setText(model.getDate().toString());
        holder.time.setText(model.getTime().toString());
        holder.curent_date.setText(model.getCurrent_date().toString());
        firestore.collection("User").document(model.getAuth_uid().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Picasso.get().load(documentSnapshot.getString("pic")).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.imageView);

            }
        });

        holder.coin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        uid=model.getAuth_uid().toString();
                        setCoin= Integer.valueOf(editText.getText().toString());
                        recevid();

                    }

                    public void swnt() {

                        Map<String,String> userMap=new HashMap<>();
                        // Toast.makeText(context, ""+coin+"--------------"+setCoin, Toast.LENGTH_SHORT).show();

                       // Toast.makeText(context, ""+coin, Toast.LENGTH_SHORT).show();
                        userMap.put("coin", String.valueOf(coin+setCoin));
                      //  Toast.makeText(context, ""+coin+setCoin, Toast.LENGTH_SHORT).show();
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

                        firestore.collection("User").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String ss=documentSnapshot.getString("coin");
                                coin= Integer.valueOf(ss);
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
                });

            }
        });

        holder.sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(context);
                View view=LayoutInflater.from(context).inflate(R.layout.smsalartdailoge,null);
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
                        alertDialog.dismiss();
                    }
                });
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        recevid();
                    }

                    private void recevid() {

                        Date d = new Date();
                        String da  = String.valueOf(DateFormat.format("MMMM d, yyyy ", d.getTime()));
                        Map<String,String> sm=new HashMap<>();
                        // Toast.makeText(context, ""+coin+"--------------"+setCoin, Toast.LENGTH_SHORT).show();

                        // Toast.makeText(context, ""+coin, Toast.LENGTH_SHORT).show();
                        sm.put("sms", String.valueOf(editText.getText().toString()));
                        sm.put("date", String.valueOf(da.toString()));
                       sm.put("name", model.getName().toString());
                       sm.put("game_date", model.getDate().toString());
                       sm.put("game_time", model.getTime().toString());
                        Random r=new Random();
                        firestore.collection("SMS").document(model.getAuth_uid().toString()).collection("s").document().set(sm).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                alertDialog.dismiss();
                                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
       // String name="",date="",time="",auth_uid="",uid="",current_date="";
        TextView name,date,time,auth_uid,curent_date,uid,coin_btn,sms_btn;
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.event_name);
            date=itemView.findViewById(R.id.event_date);
            curent_date=itemView.findViewById(R.id.event_nf_cuurent_date);
            uid=itemView.findViewById(R.id.event_user_uid);
            time=itemView.findViewById(R.id.event_time);
            imageView=itemView.findViewById(R.id.event_imag);
            coin_btn=itemView.findViewById(R.id.Coin_sent_btn);
            sms_btn=itemView.findViewById(R.id.sms_sent_btn);

        }
    }
}
