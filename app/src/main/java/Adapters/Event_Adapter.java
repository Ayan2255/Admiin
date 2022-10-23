package Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admiin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.Event_model;

public class Event_Adapter extends RecyclerView.Adapter<Event_Adapter.Holder> {
    ArrayList<Event_model>list;
    Context context;
    FirebaseFirestore firestore;
      ProgressDialog progressDialog;

    public Event_Adapter(ArrayList<Event_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.event_demo,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        firestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(context);
     Event_model model=list.get(position);
     holder.name.setText(model.getE_name());
     holder.date.setText("Date\n"+model.getE_date());
     holder.join_coin.setText(model.getE_join_coin()+" Coin\nJoin");
     holder.prize_coin.setText(model.getE_prize_coin()+" Coin\nPrizze");
     holder.time.setText(model.getE_time().toString());
     Picasso.get().load((String) model.getE_pic()).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.imageView);

     holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
            progressDialog.show();
             firestore.collection("Event")
                     .whereEqualTo("e_pic",model.getE_pic())
                     .get()
                     .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {

                             if(task.isSuccessful() && !task.getResult().isEmpty()){
                                 DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                                 String documentID =documentSnapshot.getId();
                                 firestore.collection("Event")
                                         .document(documentID)
                                         .delete()
                                         .addOnSuccessListener(new OnSuccessListener<Void>() {
                                             @Override
                                             public void onSuccess(Void unused) {
                                                 progressDialog.dismiss();
                                                 Toast.makeText(context, "Delete done...", Toast.LENGTH_SHORT).show();
                                             }
                                         }).addOnFailureListener(new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {
                                                 progressDialog.dismiss();
                                                 Toast.makeText(context, "Try again...", Toast.LENGTH_SHORT).show();
                                             }
                                         });
                             }

                         }
                     });
             return false;
         }
     });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends  RecyclerView.ViewHolder{

TextView name,join_coin,prize_coin,date,time;
ImageView imageView;
View layout;

public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.event_name);
            join_coin=itemView.findViewById(R.id.event_join_coin);
            prize_coin=itemView.findViewById(R.id.event_prize_coin);
            date=itemView.findViewById(R.id.event_date);
            imageView=itemView.findViewById(R.id.event_pic);
            layout=itemView.findViewById(R.id.event_demo_layout);
            time=itemView.findViewById(R.id.event_time);
        }
    }
}
