package com.example.cncdcattleedcandroid.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cncdcattleedcandroid.Models.FarmerListitem;
import com.example.cncdcattleedcandroid.R;

import java.util.List;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.ViewHolder> {
    private List<FarmerListitem>FarmerListitemslist;
    private Context context;

    public FarmerAdapter(List<FarmerListitem> list) {
        this.FarmerListitemslist = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farmers_list_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmerListitem FarmerListitems = FarmerListitemslist.get(position);

        holder.textViewHead.setText(FarmerListitems.getHead());
        holder.textViewDesc.setText(FarmerListitems.getDesc());

    }

    @Override
    public int getItemCount() {

        return FarmerListitemslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
