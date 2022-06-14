package com.neuer.healthyphone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class BoutiqueAD extends RecyclerView.Adapter<BoutiqueAD.MyViewHolder>  {

    Context mContext;
    List<Boutique> Data;
    FirebaseDatabase firebaseDatabase;

    public BoutiqueAD(Context mContext, List<Boutique> data) {
        this.mContext = mContext;
        Data = data;
    }
    public BoutiqueAD(){

    }
    @NonNull
    @Override
    public BoutiqueAD.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.viewboutique,parent,false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        return new BoutiqueAD.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull BoutiqueAD.MyViewHolder holder, int position) {
        holder.textView.setText(Data.get(position).getName());
        holder.level.setText(Data.get(position).getWilaya()+" / "+Data.get(position).getCity());

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView,level;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.Name);
            level = itemView.findViewById(R.id.Level);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent postDetails = new Intent(mContext, BoutiqueDetails.class);
                    postDetails.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    int pos = getAdapterPosition();


                    postDetails.putExtra("id",pos);

                    postDetails.putExtra("Phone",Data.get(pos).getPhone());
                    postDetails.putExtra("Name",Data.get(pos).getName());
                    postDetails.putExtra("City",Data.get(pos).getCity());
                    postDetails.putExtra("Wilaya",Data.get(pos).getWilaya());
                    postDetails.putExtra("Key",Data.get(pos).getUID());

                    mContext.startActivity(postDetails);

                }
            });


        }
    }

}
