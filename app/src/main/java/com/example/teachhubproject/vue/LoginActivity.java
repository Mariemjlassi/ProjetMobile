// Package de la vue contenant les activités de l'interface utilisateur.
package com.example.teachhubproject.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teachhubproject.R; // Fichier de ressources.
import com.example.teachhubproject.dto.LoginRequest; // DTO pour la requête de connexion.
import com.example.teachhubproject.dto.LoginResponse; // DTO pour la réponse de connexion.
import com.example.teachhubproject.service.AuthService; // Service d'authentification.
import com.example.teachhubproject.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Classe représentant l'écran de connexion (LoginActivity).
public class LoginActivity extends AppCompatActivity {
    // Déclaration des vues utilisées dans l'interface.
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signupEtudiantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Associe le layout XML.

        // Initialisation des éléments de l'interface.
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupEtudiantButton = findViewById(R.id.signupEtudiantButton);

        // Gestion des clics sur les boutons.
        loginButton.setOnClickListener(v -> login()); // Tentative de connexion.
        signupEtudiantButton.setOnClickListener(v -> {
            // Redirige vers l'écran d'inscription avec le rôle d'étudiant.
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            intent.putExtra("role", "ROLE_ETUDIANT");
            startActivity(intent);
        });
    }

    // Vérifie si une adresse e-mail est valide.
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Fonction de gestion de la connexion.
    private void login() {
        // Récupération des valeurs des champs de texte.
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validation des champs.
        if (email.isEmpty()) {
            emailEditText.setError("Veuillez entrer une adresse e-mail");
            emailEditText.requestFocus(); // Place le curseur dans le champ correspondant.
            return;
        }
        if (!isValidEmail(email)) {
            emailEditText.setError("Adresse e-mail invalide");
            emailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Veuillez entrer un mot de passe");
            passwordEditText.requestFocus();
            return;
        }

        // Création d'un objet de requête pour l'API.
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setMotDePasse(password);

        // Appel au service d'authentification via Retrofit.
        AuthService authService = RetrofitClient.getClient().create(AuthService.class);
        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // Vérifie si la réponse est réussie.
                if (response.isSuccessful() && response.body() != null) {
                    // Récupération des données de la réponse.
                    String jwt = response.body().getJwt();
                    String role = response.body().getRole();
                    Long etudiantId = response.body().getId();

                    // Log et message de succès.
                    Log.d("LoginActivity", "Connexion réussie : " + role);
                    Log.d("LoginActivity", "Etudiant ID: " + etudiantId);
                    Toast.makeText(LoginActivity.this, "Connexion réussie : " + role, Toast.LENGTH_SHORT).show();

                    // Redirection vers l'activité des cours.
                    Intent intent = new Intent(LoginActivity.this, CoursActivity.class);
                    intent.putExtra("etudiantId", etudiantId); // Passe l'ID de l'étudiant.
                    startActivity(intent);
                } else {
                    // Gestion des erreurs (ex: mauvais identifiants).
                    Toast.makeText(LoginActivity.this, "Erreur d'authentification", Toast.LENGTH_SHORT).show();
                    Log.e("LoginActivity", "Authentication error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Gestion des erreurs réseau ou autres.
                Log.e("LoginActivity", "Erreur : " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
