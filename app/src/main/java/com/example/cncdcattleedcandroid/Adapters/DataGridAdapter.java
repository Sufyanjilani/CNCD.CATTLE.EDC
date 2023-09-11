package com.example.cncdcattleedcandroid.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.cncdcattleedcandroid.Session.SessionManager;
import com.example.cncdcattleedcandroid.UI.ActivityCattleProfile;
import com.example.cncdcattleedcandroid.UI.ActivityFarmerProfile;
import com.example.cncdcattleedcandroid.UI.ActivityWebViewSurveyForm;
import com.example.cncdcattleedcandroid.databinding.DatagridlayoutBinding;
import com.example.cncdcattleedcandroid.databinding.RowBinding;

import java.util.ArrayList;

public class DataGridAdapter extends RecyclerView.Adapter<DataGridAdapter.Viewholer> implements Filterable {

    ArrayList<DataGridModel> dataGridModels = new ArrayList<>();
    private Context ctx;

    private ArrayList<DataGridModel> filteredData;
    String cattleID;
    SessionManager sessionManager;

    public DataGridAdapter(ArrayList<DataGridModel> gridModels, Context context){

        this.dataGridModels = gridModels;
        this.ctx = context;
        filteredData = new ArrayList<>(gridModels);
    }

    @NonNull
    @Override
    public Viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       DatagridlayoutBinding binding = DatagridlayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        sessionManager = new SessionManager(ctx);
        return new Viewholer(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholer holder, int position) {

        DataGridModel model = dataGridModels.get(position);
        cattleID = model.getCattleID();
        holder.datagridlayoutBinding.cattleSample.setText(model.getSampleID());
        holder.datagridlayoutBinding.cattleBreed.setText(model.getcBreedName());
        holder.datagridlayoutBinding.cattleType.setText(model.getcTypeName());
        holder.datagridlayoutBinding.cattleGender.setText(model.getCattleGender());
        holder.datagridlayoutBinding.createdAt.setText(model.getCreated_at());

        Glide.with(holder.datagridlayoutBinding.profileImagedatagrid.getContext()).
                load(model.cattleimagepath).into(holder.datagridlayoutBinding.profileImagedatagrid);


        holder.datagridlayoutBinding.btneditcattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.datagridlayoutBinding.btneditmilkcycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, ActivityWebViewSurveyForm.class);
                i.putExtra("formID","personal_mik_weight");
                i.putExtra("cattleID", cattleID);
                ctx.startActivity(i);
            }
        });

        holder.datagridlayoutBinding.viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDetails(model);
            }
        });
        holder.datagridlayoutBinding.deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog.setTitle("Delete Form");
                dialog.setMessage("Are You Sure You Want To Delete Record?");
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                });
                dialog.show();
            }
        });

    }
    public void ViewDetails(DataGridModel model){
        Intent intent = new Intent(ctx, ActivityCattleProfile.class);
        intent.putExtra("cattleID",model.getCattleID());
        sessionManager.SaveCattleID(model.getCattleID());
        sessionManager.saveCattleDetails(
                model.getcTypeName(),
                model.getCattleGender(),
                model.getcBreedName(),
                model.getSampleID()
        );
        ctx.startActivity(intent);
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
