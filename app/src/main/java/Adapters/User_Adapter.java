package Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.ArrayList;

import models.Topup_model;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.ViewHolder> {

       ArrayList<Topup_model>list;
       Context context;
       FirebaseFirestore firestore;

    public User_Adapter(ArrayList<Topup_model> list,Context context) {
        this.list = list;
       this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topup_demo,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topup_model user= list.get(position);
        holder.diamond.setText(user.getDiamond()+"Diamond");
        holder.taka.setText(user.getTaka()+"Taka");
        holder.name.setText(user.getName().toString());
        firestore=FirebaseFirestore.getInstance();
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                firestore.collection("Diamond")
                        .whereEqualTo("diamond",user.getDiamond())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if(task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                                    String documentID =documentSnapshot.getId();
                                    firestore.collection("Diamond")
                                            .document(documentID)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "Delete done...", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
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

    public class  ViewHolder extends  RecyclerView.ViewHolder{


        TextView diamond,taka,name;
        View layout;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);


            diamond=itemView.findViewById(R.id.demo_diamond);
           taka =itemView.findViewById(R.id.demo_taka);
           layout=itemView.findViewById(R.id.topup_demo_layout);
           name=itemView.findViewById(R.id.topup_name_set);

        }
    }
}
