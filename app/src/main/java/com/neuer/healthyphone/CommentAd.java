package com.neuer.healthyphone;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CommentAd extends RecyclerView.Adapter<CommentAd.MyViewHolder> {


    Context mContext;
    List<Comment> Data;
    public CommentAd() {

    }

    public CommentAd(Context mContext, List<Comment> data) {
        this.mContext = mContext;
        Data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.commentview,parent,false);

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Name.setText(Data.get(position).getUName());
        holder.Comment.setText(Data.get(position).getComment());
        holder.Time.setText(Data.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Comment,Time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.textView11);
            Comment = itemView.findViewById(R.id.textView12);
            Time = itemView.findViewById(R.id.textView13);

        }
    }


}
