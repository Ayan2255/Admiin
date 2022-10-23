package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admiin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.User_model;
import models.Withdraw_model;

public class WD_Adapter extends RecyclerView.Adapter<WD_Adapter.Holder> {

    ArrayList<Withdraw_model> list;;
    Context context;
    public WD_Adapter(ArrayList<Withdraw_model> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wd_list_demo,parent,false);
       return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Withdraw_model mdel=list.get(position);
        holder.date.setText(mdel.getDate());

        holder.number.setText(mdel.getNumber()+"\n"+ mdel.getOperator().toString());
        holder.taka.setText(mdel.getTaka()+" Taka");
        holder.name.setText(mdel.getNamw());
        Picasso.get().load((String) mdel.getPic()).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.pic);
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", mdel.getNumber().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText( context, "Copied", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        TextView date,name,number,taka;
        ImageView pic;
        View layout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.wd_date);
            pic=itemView.findViewById(R.id.wd_profile_image);
            name=itemView.findViewById(R.id.wd_name);
            number=itemView.findViewById(R.id.wd_number);
            taka=itemView.findViewById(R.id.wd_taka);
            layout=itemView.findViewById(R.id.wd_layout);


        }
    }
}
