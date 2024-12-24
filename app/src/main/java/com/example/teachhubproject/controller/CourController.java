package com.example.teachhubproject.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.teachhubproject.model.Cour;
import com.example.teachhubproject.service.CourService;
import com.example.teachhubproject.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourController {

    // Contexte de l'application, utilisé pour afficher des Toasts
    private Context context;

    // Constructeur pour initialiser le contexte
    public CourController(Context context) {
        this.context = context;
    }

    // Méthode pour récupérer la liste des cours d'un étudiant
    public void fetchCours(Long etudiantId, CourServiceCallback callback) {
        // Création du service API via Retrofit
        CourService apiService = RetrofitClient.getClient().create(CourService.class);

        // Appel API pour obtenir les cours de l'étudiant
        Call<List<Cour>> call = apiService.getCoursByEtudiant(etudiantId);

        // Envoi de la requête de manière asynchrone
        call.enqueue(new Callback<List<Cour>>() {
            @Override
            public void onResponse(Call<List<Cour>> call, Response<List<Cour>> response) {
                // Si la réponse est réussie et contient des données
                if (response.isSuccessful() && response.body() != null) {
                    // Passer les données récupérées au callback de succès
                    callback.onSuccess(response.body());
                } else {
                    // Si la récupération échoue, afficher un message d'erreur et appeler le callback de défaillance
                    callback.onFailure("Erreur lors de la récupération des cours");
                    Toast.makeText(context, "Erreur lors de la récupération des cours", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cour>> call, Throwable t) {
                // En cas d'échec de la requête, appeler le callback de défaillance avec le message d'erreur
                callback.onFailure(t.getMessage());
                Toast.makeText(context, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour rejoindre un cours via son code
    public void joinCourseByCode(String code, Long etudiantId, CourServiceCallback callback) {
        // Création du service API via Retrofit
        CourService apiService = RetrofitClient.getClient().create(CourService.class);

        // Appel API pour rejoindre le cours par son code
        Call<Void> call = apiService.inviteStudentByCode(code, etudiantId);

        // Envoi de la requête de manière asynchrone
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Si la réponse est réussie
                if (response.isSuccessful()) {
                    // Pas de données à renvoyer, donc on passe null au callback
                    callback.onSuccess(null);
                    Toast.makeText(context, "Rejoint avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    // Si l'ajout échoue, afficher un message d'erreur et appeler le callback de défaillance
                    callback.onFailure("Erreur: " + response.message());
                    Toast.makeText(context, "Erreur: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // En cas d'échec de la requête, appeler le callback de défaillance avec le message d'erreur
                callback.onFailure(t.getMessage());
                Toast.makeText(context, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Interface pour communiquer les résultats entre le Controller et la View (Activity)
    public interface CourServiceCallback {
        // Méthode appelée en cas de succès, retournant la liste des cours
        void onSuccess(List<Cour> cours);

        // Méthode appelée en cas d'échec, retournant un message d'erreur
        void onFailure(String errorMessage);
    }
}
