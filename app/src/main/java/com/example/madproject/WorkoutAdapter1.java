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

public class WorkoutAdapter1 extends FirestoreRecyclerAdapter<Workout, WorkoutAdapter1.WorkoutViewHolder> {

    Context context;

    public WorkoutAdapter1(@NonNull FirestoreRecyclerOptions<Workout> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position, @NonNull Workout Workout) {
        holder.work1.setText(Workout.workout1);
        holder.work2.setText(Workout.workout2);
        holder.work3.setText(Workout.workout3);
        holder.work4.setText(Workout.workoutdes);

        holder.itemView.setOnClickListener((v) ->{
            Intent intent = new Intent(context, EditWorkoutActivity.class);
            intent.putExtra("workout1", Workout.workout1);
            intent.putExtra("workout2",Workout.workout2);
            intent.putExtra("workout3",Workout.workout3);
            intent.putExtra("workoutdes",Workout.workoutdes);
            String docId = this.getSnapshots().getSnapshot(position).getId();

            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });



    }

    @NonNull
    @Override
    public WorkoutAdapter1.WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_workout,parent,false);
        return new WorkoutViewHolder(view);
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder{

        TextView work1, work2 ,work3, work4;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);

            work1 = itemView.findViewById(R.id.ww1);
            work2 = itemView.findViewById(R.id.w2);
            work3 = itemView.findViewById(R.id.ww3);
            work4= itemView.findViewById(R.id.w4);

        }
    }

}

