package com.neuer.healthyphone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AskAD extends RecyclerView.Adapter<AskAD.MyViewHolder>  {

    Context mContext;
    List<Ask> Data;

    public AskAD(Context mContext, List<Ask> data) {
        this.mContext = mContext;
        Data = data;
    }
    public AskAD(){

    }
    @NonNull
    @Override
    public AskAD.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.viewask,parent,false);

        return new AskAD.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull AskAD.MyViewHolder holder, int position) {

        holder.textView.setText(Data.get(position).getClientName());
        Glide.with(mContext).load(Data.get(position).getImage()).into(holder.IMG);
        holder.textView2.setText(Data.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView,textView2;
        ImageView IMG;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.Name);
            textView2 = itemView.findViewById(R.id.Details);

            IMG = itemView.findViewById(R.id.Img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent postDetails = new Intent(mContext, AskDetails.class);
                    postDetails.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    int pos = getAdapterPosition();



                    postDetails.putExtra("id",pos);

                    postDetails.putExtra("Image",Data.get(pos).getImage());
                    postDetails.putExtra("Image2",Data.get(pos).getImage2());
                    postDetails.putExtra("Image3",Data.get(pos).getImage3());
                    postDetails.putExtra("Image4",Data.get(pos).getImage4());
                    postDetails.putExtra("Image5",Data.get(pos).getImage5());
                    postDetails.putExtra("Image6",Data.get(pos).getImage6());
                    postDetails.putExtra("Image7",Data.get(pos).getImage7());

                    postDetails.putExtra("Name",Data.get(pos).getClientName());

                    postDetails.putExtra("Details",Data.get(pos).getQuestion());

                    postDetails.putExtra("Key",Data.get(pos).getKey());

                    mContext.startActivity(postDetails);

                }
            });


        }
    }

}
