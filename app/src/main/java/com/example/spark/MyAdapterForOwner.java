package com.example.spark;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterForOwner extends RecyclerView.Adapter<MyAdapterForOwner.MyViewHolder> {

    Context context;
    ArrayList<PaymentOwner> paymentOwners;

    public MyAdapterForOwner(Context c,ArrayList<PaymentOwner> p){
        context = c;
        paymentOwners = p;
    }

    @NonNull
    @Override
    public MyAdapterForOwner.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapterForOwner.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_history_owner,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterForOwner.MyViewHolder holder, int position) {
        int x = paymentOwners.get(position).getAmount();
        String amt = Integer.toString(x);
        holder.name.setText(paymentOwners.get(position).getUname());
        holder.UpiID.setText(paymentOwners.get(position).getGoogleid());
        holder.amount.setText(amt);
    }

    @Override
    public int getItemCount() {
        return paymentOwners.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,UpiID,amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.v("abc", "CONSTRUCTOR CALL");
            name = (TextView) itemView.findViewById(R.id.userNameHistory);
            UpiID =(TextView) itemView.findViewById(R.id.upiIdOfUser);
            amount =(TextView) itemView.findViewById(R.id.paidMoneyByUser);
        }
    }
}
