package com.example.teachhubproject.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.teachhubproject.model.Devoir;
import com.example.teachhubproject.model.Document;
import com.example.teachhubproject.service.CourService;
import com.example.teachhubproject.service.RetrofitClient;
import com.example.teachhubproject.vue.DevoirsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevoirController {

    private Context context;

    // Constructeur avec le contexte pour utiliser dans l'activité
    public DevoirController(Context context) {
        this.context = context;
    }

    // Méthode pour obtenir les devoirs pour un cours
    public void fetchDevoirs(int coursId, final DevoirsActivity devoirsActivity) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);
        Call<List<Devoir>> call = apiService.getDevoirsByCours(coursId);

        call.enqueue(new Callback<List<Devoir>>() {
            @Override
            public void onResponse(Call<List<Devoir>> call, Response<List<Devoir>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mise à jour de la liste des devoirs dans l'activité

                } else {
                    // Gérer une réponse incorrecte
                    Toast.makeText(context, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Devoir>> call, Throwable t) {
                // Gestion des erreurs de connexion
                Toast.makeText(context, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour obtenir les documents pour un cours
    public void fetchDocuments(int coursId, final DevoirsActivity devoirsActivity) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);
        Call<List<Document>> call = apiService.getDocumentsByCourId(coursId);

        call.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mise à jour de la liste des documents dans l'activité

                } else {
                    // Gérer une réponse incorrecte
                    Toast.makeText(context, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                // Gestion des erreurs de connexion
                Toast.makeText(context, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
