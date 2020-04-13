package com.example.spark;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.ViewHolder> {

    private List<BookTheVehicle> bookTheVehicles;
    private Context context;

    public MyBookAdapter(List<BookTheVehicle> bookTheVehicles, Context context) {
        this.bookTheVehicles = bookTheVehicles;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_recycler_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //BookTheVehicle bookTheVehicle = bookTheVehicles.get(position);
        //holder.textViewBookSpot.setText(bookTheVehicle.getSpotBook());
        if(bookTheVehicles.get(position).getCar_standing().equals("Yes")){
            holder.textViewChangeSpot.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.textViewBookSpot.setEnabled(false);
        }
        else {
            holder.textViewChangeSpot.setBackgroundColor(Color.parseColor("#00ff00"));
            holder.textViewBookSpot.setEnabled(true);
            holder.textViewChangeSpot.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(getApplicationContext(),carBookingBytime.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookTheVehicles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewChangeSpot,textViewBookSpot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChangeSpot = (TextView) itemView.findViewById(R.id.pickSpot);
            textViewBookSpot = (TextView) itemView.findViewById(R.id.bookSpot);

        }
    }
}
