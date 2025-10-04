package com.example.gerenciadorhabitos;

import android.content.DialogInterface;
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
    private List<Profile> profiles = new ArrayList<>();
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
        profiles.clear();
        profiles.addAll(db.profileDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProfileClick(int position) {
        if (position == 0) {
            showCreateProfileDialog();
        } else {
            selectedProfile = profiles.get(position - 1);
            Toast.makeText(this, "Perfil selecionado: " + selectedProfile.name, Toast.LENGTH_SHORT).show();
            openHabitsScreen(selectedProfile.id);
        }
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
                db.profileDao().insert(new Profile(name));
                loadProfiles();
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