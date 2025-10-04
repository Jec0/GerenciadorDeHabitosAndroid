package com.example.gerenciadorhabitos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gerenciadorhabitos.data.AppDatabase;
import com.example.gerenciadorhabitos.model.Habit;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class AddHabitActivity extends AppCompatActivity {

    private EditText etHabitName, etGoal;
    private Spinner spinnerFrequency;
    private SwitchMaterial switchNotifications;
    private Button btnSaveHabit;
    private ImageView ivBack;
    private AppDatabase db;
    private int currentProfileId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        currentProfileId = getIntent().getIntExtra("PROFILE_ID", -1);

        db = AppDatabase.getInstance(this);

        ivBack = findViewById(R.id.ivBack);
        etHabitName = findViewById(R.id.etHabitName);
        spinnerFrequency = findViewById(R.id.spinnerFrequency);
        etGoal = findViewById(R.id.etGoal);
        switchNotifications = findViewById(R.id.switchNotifications);
        btnSaveHabit = findViewById(R.id.btnSaveHabit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapter);

        ivBack.setOnClickListener(v -> finish());
        btnSaveHabit.setOnClickListener(v -> saveHabit());
    }

    private void saveHabit() {
        String habitName = etHabitName.getText().toString().trim();
        String frequency = spinnerFrequency.getSelectedItem().toString();
        String goal = etGoal.getText().toString().trim();
        boolean notificationsOn = switchNotifications.isChecked();

        if (habitName.isEmpty()) {
            Toast.makeText(this, "Por favor, digite o nome do hábito", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentProfileId == -1) {
            Toast.makeText(this, "Erro ao salvar: ID do perfil não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Habit newHabit = new Habit(
                currentProfileId,
                habitName,
                frequency,
                goal,
                notificationsOn
        );

        db.habitDao().insert(newHabit);

        Toast.makeText(this, "Hábito salvo!", Toast.LENGTH_SHORT).show();
        finish();
    }
}