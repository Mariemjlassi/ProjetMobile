package com.example.teachhubproject.vue;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachhubproject.R;
import com.example.teachhubproject.model.Devoir;
import com.example.teachhubproject.model.Document;
import com.example.teachhubproject.service.CourService;
import com.example.teachhubproject.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.recyclerview.widget.GridLayoutManager;

public class DevoirsActivity extends AppCompatActivity {

    // Déclaration des éléments de l'interface utilisateur et des données
    private RecyclerView recyclerViewDevoirs; // RecyclerView pour afficher les devoirs
    private RecyclerView recyclerViewDocuments; // RecyclerView pour afficher les documents
    private DevoirAdapter devoirAdapter; // Adaptateur pour la liste des devoirs
    private DocumentAdapter documentAdapter; // Adaptateur pour la liste des documents
    private List<Devoir> devoirList; // Liste des devoirs
    private List<Document> documentList; // Liste des documents

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activer l'affichage bord-à-bord
        setContentView(R.layout.activity_devoirs);

        // Initialisation des RecyclerViews pour afficher les données
        recyclerViewDevoirs = findViewById(R.id.recyclerViewDevoirs);
        recyclerViewDocuments = findViewById(R.id.recyclerViewDocuments);

        // Utilisation d'un GridLayoutManager pour afficher les devoirs sous forme de grille
        int numberOfColumns = 2; // Nombre de colonnes pour la grille
        recyclerViewDevoirs.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerViewDocuments.setLayoutManager(new LinearLayoutManager(this)); // Disposition linéaire pour les documents

        // Initialisation des listes et des adaptateurs
        devoirList = new ArrayList<>();
        documentList = new ArrayList<>();
        devoirAdapter = new DevoirAdapter(devoirList);
        documentAdapter = new DocumentAdapter(documentList);
        recyclerViewDevoirs.setAdapter(devoirAdapter); // Associer l'adaptateur des devoirs
        recyclerViewDocuments.setAdapter(documentAdapter); // Associer l'adaptateur des documents

        // Récupérer l'identifiant du cours depuis les Intent extras
        Integer coursId = getIntent().getIntExtra("coursId", -1);
        if (coursId != -1) { // Vérifier si l'ID est valide
            fetchDevoirs(coursId); // Charger les devoirs associés au cours
            fetchDocuments(coursId); // Charger les documents associés au cours
        }

        // Appliquer des marges pour éviter que l'interface chevauche les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Méthode pour récupérer les devoirs d'un cours via l'API
    private void fetchDevoirs(Integer coursId) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);

        Call<List<Devoir>> call = apiService.getDevoirsByCours(coursId); // Appel à l'API pour obtenir les devoirs

        call.enqueue(new Callback<List<Devoir>>() {
            @Override
            public void onResponse(Call<List<Devoir>> call, Response<List<Devoir>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mise à jour de la liste des devoirs si la réponse est correcte
                    Log.d("DevoirsActivity", "Devoirs reçus: " + response.body().toString());
                    devoirList.clear();
                    devoirList.addAll(response.body());
                    devoirAdapter.notifyDataSetChanged(); // Notifier l'adaptateur pour rafraîchir l'affichage
                } else {
                    // Gérer une réponse incorrecte de l'API
                    Log.e("DevoirsActivity", "Erreur : " + response.message());
                    Toast.makeText(DevoirsActivity.this, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Devoir>> call, Throwable t) {
                // Gérer une erreur de connexion ou de requête
                Toast.makeText(DevoirsActivity.this, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour récupérer les documents d'un cours via l'API
    private void fetchDocuments(Integer coursId) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);

        Call<List<Document>> call = apiService.getDocumentsByCourId(coursId); // Appel à l'API pour obtenir les documents

        call.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mise à jour de la liste des documents si la réponse est correcte
                    Log.d("DevoirsActivity", "Documents reçus: " + response.body().toString());
                    documentList.clear();
                    documentList.addAll(response.body());
                    documentAdapter.notifyDataSetChanged(); // Notifier l'adaptateur pour rafraîchir l'affichage
                } else {
                    // Gérer une réponse incorrecte de l'API
                    Log.e("DevoirsActivity", "Erreur : " + response.message());
                    Toast.makeText(DevoirsActivity.this, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                // Gérer une erreur de connexion ou de requête
                Toast.makeText(DevoirsActivity.this, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
