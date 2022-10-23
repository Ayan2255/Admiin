package Adapters;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admiin.R;

import java.util.ArrayList;

import io.grpc.internal.ThreadOptimizedDeframer;
import models.Topup_model;
import models.Topup_notification_model;
import models.User_model;

public class Topup_notification_Adapter extends RecyclerView.Adapter<Topup_notification_Adapter.Holder> {

    ArrayList<Topup_notification_model>list;
    Context context;

    public Topup_notification_Adapter(ArrayList<Topup_notification_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.toup_notification_demo,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Topup_notification_model model=list.get(position);

        holder.date.setText(model.getDate());
        holder.diamond.setText(model.getDiamond()+" Diamond");
        holder.taka.setText(model.getTaka()+" Taka");
        holder.uid.setText("FF_UID= "+model.getUid());
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", model.getUid().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText( context, "Copied", Toast.LENGTH_SHORT).show();
                return  false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView date,diamond,taka,uid;
        View layout;
        public Holder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date);
            taka=itemView.findViewById(R.id.taka);
            diamond=itemView.findViewById(R.id.diamond_d);
            uid=itemView.findViewById(R.id.uid);
            layout=itemView.findViewById(R.id.topup_notificatin_layout);
        }
    }
}
