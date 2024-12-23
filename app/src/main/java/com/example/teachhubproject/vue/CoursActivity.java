package com.example.teachhubproject.vue;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachhubproject.R;
import com.example.teachhubproject.model.Cour;
import com.example.teachhubproject.service.CourService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourAdapter adapter;
    private List<Cour> coursList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours);

        // Toolbar configuration
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Champ et bouton
        EditText codeInput = findViewById(R.id.codeInput);
        Button joinButton = findViewById(R.id.joinButton);

        joinButton.setOnClickListener(v -> {
            String code = codeInput.getText().toString().trim();

            if (!code.isEmpty()) {
                // Appel au backend pour rejoindre le cours
                joinCourseByCode(code);
            } else {
                Toast.makeText(CoursActivity.this, "Veuillez entrer un code valide", Toast.LENGTH_SHORT).show();
            }
        });

        // Récupérer les cours existants
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        coursList = new ArrayList<>();
        adapter = new CourAdapter(coursList, coursId -> {
            // Action lors du clic sur un cours
            Toast.makeText(this, "Cours sélectionné : " + coursId, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        Long etudiantId = getIntent().getLongExtra("etudiantId", 0L);
        if (etudiantId != 0L) {
            fetchCours(etudiantId);
        }
    }

    private void joinCourseByCode(String code) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);

        // Récupérer l'ID de l'étudiant depuis l'intent
        Long etudiantId = getIntent().getLongExtra("etudiantId", 0L);

        // Appel à l'API pour rejoindre le cours avec le code
        Call<Void> call = apiService.inviteStudentByCode(code, etudiantId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CoursActivity.this, "Rejoint avec succès", Toast.LENGTH_SHORT).show();
                    // Actualiser la liste des cours
                    fetchCours(etudiantId);
                } else {
                    Toast.makeText(CoursActivity.this, "Erreur: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CoursActivity.this, "Échec: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }








    private void fetchCours(Long etudiantId) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);
        Call<List<Cour>> call = apiService.getCoursByEtudiant(etudiantId);

        call.enqueue(new Callback<List<Cour>>() {
            @Override
            public void onResponse(Call<List<Cour>> call, Response<List<Cour>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    coursList.clear();
                    coursList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("CoursActivity", "Cours list updated with " + coursList.size() + " items.");
                } else {
                    Toast.makeText(CoursActivity.this, "Erreur lors de la récupération des cours", Toast.LENGTH_SHORT).show();
                    Log.e("CoursActivity", "Erreur lors de la récupération des cours: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Cour>> call, Throwable t) {
                Toast.makeText(CoursActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("CoursActivity", "Erreur : " + t.getMessage());
            }
        });
    }

}
