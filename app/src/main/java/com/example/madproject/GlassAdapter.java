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

public class GlassAdapter extends FirestoreRecyclerAdapter<Glass, GlassAdapter.GlassViewHolder> {

    Context context;

    public GlassAdapter(@NonNull FirestoreRecyclerOptions<Glass> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull GlassAdapter.GlassViewHolder holder, int position, @NonNull Glass glass) {
        holder.glassA.setText(glass.amount);

    }

    @NonNull
    @Override
    public GlassAdapter.GlassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_glass,parent,false);
        return  new GlassAdapter.GlassViewHolder(view);
    }

    class GlassViewHolder extends RecyclerView.ViewHolder{

        TextView glassA;

        public GlassViewHolder(@NonNull View itemView) {
            super(itemView);

            glassA = itemView.findViewById(R.id.glass_view);


        }
    }
}
