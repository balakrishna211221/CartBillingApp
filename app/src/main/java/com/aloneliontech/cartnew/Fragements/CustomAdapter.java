package com.aloneliontech.cartnew.Fragements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aloneliontech.cartnew.Itempojopojo;
import com.aloneliontech.cartnew.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    FragmentActivity activity;
    List<Itempojopojo> list;
    public CustomAdapter(FragmentActivity activity, List<Itempojopojo> list) {
        this.activity=activity;
        this.list=list;
    }


    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,qty;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemname);
            price=itemView.findViewById(R.id.itemprice);
            qty=itemView.findViewById(R.id.itemqty);
           // image=itemView.findViewById(R.id.itemimage);



        }
    }
}
