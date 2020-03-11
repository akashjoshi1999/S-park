package com.example.spark;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class bookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textViewChangeSpot,textViewBookSpot;
    public ItemClickListner listner;

    public bookViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewChangeSpot = (TextView) itemView.findViewById(R.id.pickSpot);
        textViewBookSpot = (TextView) itemView.findViewById(R.id.bookSpot);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
