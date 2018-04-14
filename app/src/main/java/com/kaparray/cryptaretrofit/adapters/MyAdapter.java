package com.kaparray.cryptaretrofit.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaparray.cryptaretrofit.MainActivity;
import com.kaparray.cryptaretrofit.data.Data;
import com.kaparray.cryptaretrofit.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Data> userFromServer;
    private Context context;


    public MyAdapter(List<Data> userFromServer, Context context) {
        this.userFromServer = userFromServer;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypta, parent, false);

        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        holder.setTitleName(userFromServer.get(position).getName());
        holder.setPrice(userFromServer.get(position).getPriceUsd());
        holder.setShortName(userFromServer.get(position).getSymbol());
        holder.setPrice1h(userFromServer.get(position).getPercentChange1h());
        holder.setPrice24h(userFromServer.get(position).getPercentChange24h());
        holder.setPrice7d(userFromServer.get(position).getPercentChange7d());

    }

    @Override
    public int getItemCount() {
        return userFromServer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitleName(String Name) {
            TextView name = mView.findViewById(R.id.name);
            name.setText(Name);
        }

        public void setPrice(String Price){
            TextView price = mView.findViewById(R.id.price);
            price.setText( "$" + Price);
        }

        public void setShortName(String ShortName){
            TextView price = mView.findViewById(R.id.short_name);
            price.setText(ShortName);
        }

        @SuppressLint("ResourceAsColor")
        public void setPrice1h(String Price){
            TextView price = mView.findViewById(R.id.price1h);
            TextView h = mView.findViewById(R.id.h1);
            h.setText("1H");
            price.setText(Price + "%");
            if (Price.charAt(0) == '-') price.setTextColor(context.getResources().getColor(R.color.Red));
            else price.setTextColor(context.getResources().getColor(R.color.Green));
        }

        @SuppressLint("ResourceAsColor")
        public void setPrice24h(String Price){
            TextView price = mView.findViewById(R.id.price24h);
            TextView h = mView.findViewById(R.id.h24);
            h.setText("24H");
            price.setText(Price + "%");
            if (Price.charAt(0) == '-') price.setTextColor(context.getResources().getColor(R.color.Red));
            else price.setTextColor(context.getResources().getColor(R.color.Green));
        }

        public void setPrice7d(String Price){
            TextView price = mView.findViewById(R.id.price1d);
            price.setText(Price + "%");
            TextView h = mView.findViewById(R.id.d7);
            h.setText("7D");
            if (Price.charAt(0) == '-') price.setTextColor(context.getResources().getColor(R.color.Red));
            else price.setTextColor(context.getResources().getColor(R.color.Green));
        }

    }
}