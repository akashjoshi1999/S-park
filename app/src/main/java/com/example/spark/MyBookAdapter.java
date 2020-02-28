package com.example.spark;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        BookTheVehicle bookTheVehicle = bookTheVehicles.get(position);
        holder.textViewBookSpot.setText(bookTheVehicle.getSpotBook());
    }

    @Override
    public int getItemCount() {
        return bookTheVehicles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewBookSpot;
        public TextView textViewButtonBookSpot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBookSpot = (TextView) itemView.findViewById(R.id.pickSpot);
            textViewBookSpot = (TextView) itemView.findViewById(R.id.bookSpot);

        }
    }
}
