package com.example.gerenciadorhabitos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gerenciadorhabitos.R;
import com.example.gerenciadorhabitos.model.Habit;
import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private final List<Habit> habits;
    private final OnHabitListener listener;

    public HabitAdapter(List<Habit> habits, OnHabitListener listener) {
        this.habits = habits;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_habit, parent, false);
        return new HabitViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habits.get(position);
        holder.bind(habit);
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvHabitName, tvHabitGoal, tvHabitFrequency;
        private final ImageView ivCompletedCheck;
        private final OnHabitListener listener;

        public HabitViewHolder(@NonNull View itemView, OnHabitListener listener) {
            super(itemView);
            tvHabitName = itemView.findViewById(R.id.tvHabitName);
            tvHabitGoal = itemView.findViewById(R.id.tvHabitGoal);
            tvHabitFrequency = itemView.findViewById(R.id.tvHabitFrequency);
            ivCompletedCheck = itemView.findViewById(R.id.ivCompletedCheck);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public void bind(Habit habit) {
            tvHabitName.setText(habit.name);
            tvHabitFrequency.setText(habit.frequency);

            if (habit.goal != null && !habit.goal.isEmpty()) {
                tvHabitGoal.setText("Meta: " + habit.goal);
                tvHabitGoal.setVisibility(View.VISIBLE);
            } else {
                tvHabitGoal.setVisibility(View.GONE);
            }

            if (habit.completedToday) {
                ivCompletedCheck.setVisibility(View.VISIBLE);
            } else {
                ivCompletedCheck.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            listener.onHabitClick(getAdapterPosition());
        }
    }
    public interface OnHabitListener {
        void onHabitClick(int position);
    }
}