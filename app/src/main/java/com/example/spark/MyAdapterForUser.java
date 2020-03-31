package com.example.spark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterForUser extends RecyclerView.Adapter<MyAdapterForUser.MyViewHolder> {

    Context context;
    ArrayList<PaymentUser> paymentUsers;

    public MyAdapterForUser(Context c,ArrayList<PaymentUser> p){
        context = c;
        paymentUsers = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_history_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(paymentUsers.get(position).getName());
        holder.UpiID.setText(paymentUsers.get(position).getUpiID());
        holder.amount.setText(paymentUsers.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return paymentUsers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,UpiID,amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.ownerNameHistory);
            UpiID =(TextView) itemView.findViewById(R.id.upiIdOfOwner);
            amount =(TextView) itemView.findViewById(R.id.payMoneyToOwner);
        }
    }
}
