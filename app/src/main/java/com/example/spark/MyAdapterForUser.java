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

            int x = paymentUsers.get(position).getAmount();
            Log.v("abc","int: "+x);

            String amt = Integer.toString(x);
            Log.v("abc","str: "+amt);
            holder.name.setText(paymentUsers.get(position).getOwnername());
            holder.UpiID.setText(paymentUsers.get(position).getGoogleid());
            holder.amount.setText(amt);


    }

    @Override
    public int getItemCount() {
        return paymentUsers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,UpiID,amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.v("abc", "CONSTRUCTOR CALL");

            name = (TextView) itemView.findViewById(R.id.ownerNameHistory);
            UpiID =(TextView) itemView.findViewById(R.id.upiIdOfOwner);
            amount =(TextView) itemView.findViewById(R.id.payMoneyToOwner);
        }
    }
}
