package com.example.gerenciadorhabitos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadorhabitos.adapter.HabitAdapter;
import com.example.gerenciadorhabitos.api.Quote;
import com.example.gerenciadorhabitos.api.QuoteApiService;
import com.example.gerenciadorhabitos.api.RetrofitClient;
import com.example.gerenciadorhabitos.data.AppDatabase;
import com.example.gerenciadorhabitos.model.Habit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements HabitAdapter.OnHabitListener {
    private AppDatabase db;
    private final List<Habit> habits = new ArrayList<>();
    private HabitAdapter adapter;
    private TextView tvProgress;
    private ProgressBar progressBar;
    private TextView tvQuote;
    private int selectedProfileId = -1;
    private ImageView ivProfile;
    private ImageButton btnBack;

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
        tvQuote = findViewById(R.id.tvQuote);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        ivProfile = findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(v -> finish());

        fetchQuote();

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

    private void fetchQuote() {
        QuoteApiService apiService = RetrofitClient.getClient().create(QuoteApiService.class);
        Call<List<Quote>> call = apiService.getRandomQuote();

        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Quote quote = response.body().get(0);
                    String quoteText = "\"" + quote.getText() + "\" - " + quote.getAuthor();
                    tvQuote.setText(quoteText);
                } else {
                    tvQuote.setText("Não foi possível carregar a citação do dia.");
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                tvQuote.setText("Falha na conexão. Verifique sua internet.");
            }
        });
    }

    private void refreshList() {
        if (selectedProfileId != -1) {
            new Thread(() -> {
                habits.clear();
                habits.addAll(db.habitDao().getHabitsForProfile(selectedProfileId));
                runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                    updateProgress();
                });
            }).start();
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
        new Thread(() -> db.habitDao().update(habit)).start();
        updateProgress();
        adapter.notifyItemChanged(position);
    }
}