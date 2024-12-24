package com.example.teachhubproject.vue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachhubproject.R;
import com.example.teachhubproject.controller.CourController;
import com.example.teachhubproject.model.Cour;

import java.util.ArrayList;
import java.util.List;

public class CoursActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // RecyclerView pour afficher la liste des cours
    private CourAdapter adapter; // Adaptateur pour lier les données à la RecyclerView
    private List<Cour> coursList; // Liste des cours récupérés
    private CourController courController; // Instance du Controller pour la gestion des cours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours);

        // Configuration de la barre d'outils
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialisation du champ de saisie et du bouton pour rejoindre un cours
        EditText codeInput = findViewById(R.id.codeInput);
        Button joinButton = findViewById(R.id.joinButton);

        // Initialisation du Controller
        courController = new CourController(this);

        // Action lorsque l'utilisateur clique sur le bouton "Rejoindre"
        joinButton.setOnClickListener(v -> {
            String code = codeInput.getText().toString().trim(); // Récupérer le code saisi

            if (!code.isEmpty()) {
                // Si un code est saisi, appeler la méthode pour rejoindre le cours
                Long etudiantId = getIntent().getLongExtra("etudiantId", 0L);
                courController.joinCourseByCode(code, etudiantId, new CourController.CourServiceCallback() {
                    @Override
                    public void onSuccess(List<Cour> cours) {
                        fetchCours(etudiantId);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(CoursActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Sinon, afficher un message demandant un code valide
                Toast.makeText(CoursActivity.this, "Veuillez entrer un code valide", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuration de la RecyclerView pour afficher la liste des cours
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Disposition en liste verticale

        // Initialisation de la liste des cours et de l'adaptateur
        coursList = new ArrayList<>();
        adapter = new CourAdapter(coursList, coursId -> {
            // Action à exécuter lorsqu'un cours est sélectionné (clic sur un élément)
            Toast.makeText(this, "Cours sélectionné : " + coursId, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter); // Lier l'adaptateur à la RecyclerView

        // Récupérer l'ID de l'étudiant passé dans l'intent
        Long etudiantId = getIntent().getLongExtra("etudiantId", 0L);
        if (etudiantId != 0L) {
            // Si un ID est présent, récupérer les cours associés à cet étudiant
            fetchCours(etudiantId);
        }
    }

    // Méthode pour récupérer la liste des cours associés à un étudiant
    private void fetchCours(Long etudiantId) {
        courController.fetchCours(etudiantId, new CourController.CourServiceCallback() {
            @Override
            public void onSuccess(List<Cour> cours) {
                // Mise à jour de la liste des cours
                coursList.clear();
                coursList.addAll(cours);
                adapter.notifyDataSetChanged(); // Notifier l'adaptateur des changements
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(CoursActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
