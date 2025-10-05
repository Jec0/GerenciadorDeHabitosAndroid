package com.example.gerenciadorhabitos;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadorhabitos.adapter.ProfileAdapter;
import com.example.gerenciadorhabitos.data.AppDatabase;
import com.example.gerenciadorhabitos.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements ProfileAdapter.OnProfileListener {

    private AppDatabase db;
    private final List<Profile> profiles = new ArrayList<>();
    private ProfileAdapter adapter;
    private Profile selectedProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        db = AppDatabase.getInstance(this);

        RecyclerView rv = findViewById(R.id.recyclerProfiles);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProfileAdapter(profiles, this);
        rv.setAdapter(adapter);

        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            if (selectedProfile != null) {
                openHabitsScreen(selectedProfile.id);
            } else {
                Toast.makeText(this, "Selecione um perfil para continuar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfiles();
    }

    private void loadProfiles() {
        new Thread(() -> {
            profiles.clear();
            profiles.addAll(db.profileDao().getAll());
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }

    @Override
    public void onProfileClick(Profile profile) {
        Toast.makeText(this, "Carregando perfil: " + profile.name, Toast.LENGTH_SHORT).show();
        openHabitsScreen(profile.id);
    }

    @Override
    public void onAddProfileClick() {
        showCreateProfileDialog();
    }

    @Override
    public void onProfileLongClick(Profile profile, int adapterPosition) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Perfil")
                .setMessage("Tem certeza que deseja excluir o perfil '" + profile.name + "'? Todos os hábitos associados serão perdidos permanentemente.")
                .setPositiveButton("Excluir", (dialog, which) -> {
                    new Thread(() -> {
                        db.profileDao().delete(profile);
                        runOnUiThread(this::loadProfiles);
                    }).start();
                    Toast.makeText(ProfileActivity.this, "Perfil excluído", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showCreateProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Novo Perfil");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        input.setHint("Nome do perfil");
        builder.setView(input);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) {
                new Thread(() -> {
                    db.profileDao().insert(new Profile(name));
                    runOnUiThread(this::loadProfiles);
                }).start();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void openHabitsScreen(int profileId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("PROFILE_ID", profileId);
        startActivity(intent);
    }
}