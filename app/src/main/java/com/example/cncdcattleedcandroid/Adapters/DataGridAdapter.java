package com.example.cncdcattleedcandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cncdcattleedcandroid.Models.DataGridModel;
import com.example.cncdcattleedcandroid.databinding.DatagridlayoutBinding;

import java.util.ArrayList;

public class DataGridAdapter extends RecyclerView.Adapter<DataGridAdapter.Viewholer> {

    ArrayList<DataGridModel> dataGridModels = new ArrayList<>();

    public DataGridAdapter(ArrayList<DataGridModel> gridModels){

        this.dataGridModels = gridModels;
    }

    @NonNull
    @Override
    public Viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DatagridlayoutBinding binding = DatagridlayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new Viewholer(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholer holder, int position) {

        DataGridModel model = dataGridModels.get(position);
        holder.datagridlayoutBinding.cattlename.setText(model.farmname);
        holder.datagridlayoutBinding.cattleId.setText(model.cattleid);
        holder.datagridlayoutBinding.farmid.setText(model.farmid);
        holder.datagridlayoutBinding.farmerId.setText(model.farmerid);

        Glide.with(holder.datagridlayoutBinding.profileImagedatagrid.getContext()).
                load(model.cattleimagepath).into(holder.datagridlayoutBinding.profileImagedatagrid);

    }

    @Override
    public int getItemCount() {
        return dataGridModels.size();
    }

    class Viewholer extends RecyclerView.ViewHolder{


        DatagridlayoutBinding datagridlayoutBinding;

        public Viewholer(@NonNull DatagridlayoutBinding itemView) {
            super(itemView.getRoot());
            this.datagridlayoutBinding = itemView;
        }
    }
}
