// Package contenant la classe RetrofitClient pour gérer les appels API.
package com.example.teachhubproject.service;

import android.util.Log; // Utilisé pour la journalisation des informations et erreurs.

import retrofit2.Retrofit; // Classe principale pour effectuer les appels HTTP.
import retrofit2.converter.gson.GsonConverterFactory; // Permet de convertir les réponses JSON en objets Java avec Gson.

public class RetrofitClient {

    // L'URL de base de l'API, utilisée pour les appels RESTful. Elle pointe vers le serveur local sur l'émulateur Android.
    private static final String BASE_URL = "http://10.0.2.2:9099/"; // Utilisez l'adresse IP de votre machine au lieu de localhost si nécessaire.

    // Une instance de Retrofit pour éviter de créer une nouvelle instance à chaque appel.
    private static Retrofit retrofit = null;

    // Méthode statique pour obtenir l'instance unique de Retrofit.
    public static Retrofit getClient() {
        // Vérifie si Retrofit n'a pas encore été initialisé.
        if (retrofit == null) {
            // Journalise un message pour indiquer que l'on essaie de se connecter à l'URL de base.
            Log.d("RetrofitClient", "Attempting to connect to BASE_URL: " + BASE_URL);

            try {
                // Initialise l'instance de Retrofit si elle n'existe pas encore.
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL) // Définit l'URL de base pour toutes les requêtes.
                        .addConverterFactory(GsonConverterFactory.create()) // Utilise Gson pour la conversion des réponses JSON en objets Java.
                        .build(); // Crée l'instance Retrofit.

                // Journalise un message pour indiquer que l'initialisation a réussi.
                Log.d("RetrofitClient", "Retrofit initialized successfully.");
            } catch (Exception e) {
                // En cas d'erreur lors de l'initialisation, un message d'erreur est affiché.
                Log.e("RetrofitClient", "Error initializing Retrofit", e);
            }
        }

        // Retourne l'instance de Retrofit.
        return retrofit;
    }
}
