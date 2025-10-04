package com.example.gerenciadorhabitos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadorhabitos.adapter.HabitAdapter;
import com.example.gerenciadorhabitos.data.AppDatabase;
import com.example.gerenciadorhabitos.model.Habit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HabitAdapter.OnHabitListener {
    private AppDatabase db;
    private List<Habit> habits = new ArrayList<>();
    private HabitAdapter adapter;
    private TextView tvProgress;
    private ProgressBar progressBar;
    private int selectedProfileId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);

        selectedProfileId = getIntent().getIntExtra("PROFILE_ID", -1);
        if (selectedProfileId == -1) {
            Toast.makeText(this, "Erro: Perfil não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = AppDatabase.getInstance(this);
        tvProgress = findViewById(R.id.tvProgress);
        progressBar = findViewById(R.id.progressBar);

        RecyclerView rv = findViewById(R.id.recyclerHabits);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new HabitAdapter(habits, this);
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAdd);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddHabitActivity.class);
            intent.putExtra("PROFILE_ID", selectedProfileId);
            startActivity(intent);
        });
    }

    private void refreshList() {
        if (selectedProfileId != -1) {
            habits.clear();
            habits.addAll(db.habitDao().getHabitsForProfile(selectedProfileId));
            adapter.notifyDataSetChanged();
            updateProgress();
        }
    }

    private void updateProgress() {
        if (habits.isEmpty()) {
            tvProgress.setText("Adicione um novo hábito!");
            progressBar.setMax(1);
            progressBar.setProgress(0);
            return;
        }

        long completedCount = habits.stream().filter(h -> h.completedToday).count();
        int totalCount = habits.size();

        tvProgress.setText("Hoje você completou " + completedCount + " de " + totalCount + " hábitos");
        progressBar.setMax(totalCount);
        progressBar.setProgress((int) completedCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onHabitClick(int position) {
        Habit habit = habits.get(position);
        habit.completedToday = !habit.completedToday;
        db.habitDao().update(habit);
        refreshList();
    }
}