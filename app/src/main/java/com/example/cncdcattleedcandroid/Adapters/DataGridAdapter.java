package com.example.cncdcattleedcandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cncdcattleedcandroid.Models.DataGridModel;
import com.example.cncdcattleedcandroid.UI.ActivityWebViewSurveyForm;
import com.example.cncdcattleedcandroid.databinding.DatagridlayoutBinding;

import java.util.ArrayList;

public class DataGridAdapter extends RecyclerView.Adapter<DataGridAdapter.Viewholer> implements Filterable {

    ArrayList<DataGridModel> dataGridModels = new ArrayList<>();
    private Context ctx;

    private ArrayList<DataGridModel> filteredData;

    public DataGridAdapter(ArrayList<DataGridModel> gridModels, Context context){

        this.dataGridModels = gridModels;
        this.ctx = context;
        filteredData = new ArrayList<>(gridModels);
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


        holder.datagridlayoutBinding.btneditcattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(ctx, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","1");
                ctx.startActivity(i);

            }
        });

        holder.datagridlayoutBinding.btneditmilkcycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","5");
                ctx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public void filter(String query) {
        filteredData.clear();
        if (query.isEmpty()) {
            filteredData.addAll(dataGridModels);
        } else {
            query = query.toLowerCase();
            for (DataGridModel item : dataGridModels) {
                if (item.getId().toLowerCase().contains(query)) {
                    filteredData.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    class Viewholer extends RecyclerView.ViewHolder{


        DatagridlayoutBinding datagridlayoutBinding;

        public Viewholer(@NonNull DatagridlayoutBinding itemView) {
            super(itemView.getRoot());
            this.datagridlayoutBinding = itemView;
        }
    }
}
