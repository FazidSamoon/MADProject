package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;

public class WaterAdapter extends FirestoreRecyclerAdapter<WaterGoal, WaterAdapter.WaterViewHolder> {

    Context context;

    public WaterAdapter(@NonNull FirestoreRecyclerOptions<WaterGoal> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull WaterViewHolder holder, int position, @NonNull WaterGoal waterGoal) {
        holder.dailyTar.setText(waterGoal.waterGoal);
        holder.dailyMod.setText(waterGoal.dailyMode);
        holder.lastCom.setText(waterGoal.completed);

        String docId = this.getSnapshots().getSnapshot(position).getId();
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditWaterConsumptionActivity.class);
            intent.putExtra("waterGoal", waterGoal.waterGoal);
            intent.putExtra("dailyMode",waterGoal.dailyMode);
            intent.putExtra("completed",waterGoal.completed);

            intent.putExtra("docId", docId);
            context.startActivity(intent);
            }
        });



//        holder.itemView.setOnClickListener((v) -> {
//            Intent intent = new Intent(context, EditWaterConsumptionActivity.class);
//            intent.putExtra("waterGoal", waterGoal.waterGoal);
//            intent.putExtra("dailyMode",waterGoal.dailyMode);
//            intent.putExtra("completed",waterGoal.completed);
//
//            String docId = this.getSnapshots().getSnapshot(position).getId();
//            intent.putExtra("docId", docId);
//            context.startActivity(intent);
//        });
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_water_item,parent,false);
        return  new WaterViewHolder(view);
    }

    class WaterViewHolder extends RecyclerView.ViewHolder{

        TextView dailyTar, dailyMod ,lastCom;
        MaterialButton update;

        public WaterViewHolder(@NonNull View itemView) {
            super(itemView);

            dailyTar = itemView.findViewById(R.id.water_view);
            dailyMod = itemView.findViewById(R.id.daily_view);
            lastCom = itemView.findViewById(R.id.complete_view);
            update = itemView.findViewById(R.id.button_update_water1);

        }
    }
}

