package com.example.teachhubproject.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teachhubproject.R;
import com.example.teachhubproject.dto.SignUpRequest; // Objet de demande d'inscription
import com.example.teachhubproject.dto.SignUpResponse; // Objet de réponse de l'inscription
import com.example.teachhubproject.service.AuthService; // Service d'authentification
import com.example.teachhubproject.service.RetrofitClient;

import java.io.IOException; // Pour gérer les erreurs d'entrées/sorties (IO)

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    // Déclaration des champs de saisie pour le formulaire d'inscription
    private EditText editNom, editPrenom, editEmail, editMotDePasse;
    private Button btnSignup; // Bouton d'inscription
    private String role; // Le rôle de l'utilisateur, récupéré depuis l'Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // Associe cette activité à son layout

        // Initialisation des vues (les champs de saisie et le bouton)
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        editEmail = findViewById(R.id.editEmail);
        editMotDePasse = findViewById(R.id.editMotDePasse);
        btnSignup = findViewById(R.id.btnSignup);

        // Récupère le rôle de l'utilisateur depuis l'Intent (transmis depuis LoginActivity)
        role = getIntent().getStringExtra("role");

        // Ajoute un écouteur d'événements pour le bouton d'inscription
        btnSignup.setOnClickListener(view -> performSignup(role)); // Appel de la méthode d'inscription
    }

    // Méthode pour vérifier la validité de l'email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); // Utilise le pattern Android pour valider l'email
    }

    // Méthode qui effectue l'inscription
    private void performSignup(String role) {
        // Récupère les valeurs saisies par l'utilisateur
        String nom = editNom.getText().toString();
        String prenom = editPrenom.getText().toString();
        String email = editEmail.getText().toString();
        String motDePasse = editMotDePasse.getText().toString();

        // Vérifie si tous les champs sont remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show(); // Message d'erreur si un champ est vide
            return;
        }

        // Vérifie si l'email est valide
        if (!isValidEmail(email)) {
            editEmail.setError("Adresse e-mail invalide"); // Affiche un message d'erreur sur le champ email
            editEmail.requestFocus(); // Demande le focus sur le champ email
            return;
        }

        // Crée un objet SignUpRequest contenant les informations de l'utilisateur à envoyer au backend
        SignUpRequest signUpRequest = new SignUpRequest(nom, prenom, email, motDePasse, role);

        // Appel au service d'authentification pour effectuer l'inscription via Retrofit
        AuthService authService = RetrofitClient.getClient().create(AuthService.class); // Récupère l'instance du service
        Call<SignUpResponse> call = authService.registerUser(signUpRequest); // Prépare l'appel API pour l'inscription

        // Exécute l'appel API de manière asynchrone
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                // Vérifie si la réponse est réussie
                if (response.isSuccessful()) {
                    // Si l'inscription est réussie, affiche un message et redirige vers l'écran de login
                    Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class); // Crée une nouvelle intention pour ouvrir l'écran de login
                    startActivity(intent); // Démarre l'activité LoginActivity
                } else {
                    // Si l'inscription échoue, affiche un message d'erreur avec le corps de l'erreur retourné par l'API
                    try {
                        String errorBody = response.errorBody().string(); // Lit le corps de l'erreur
                        Toast.makeText(SignupActivity.this, "Signup failed: " + errorBody, Toast.LENGTH_SHORT).show();
                        Log.e("SignupActivity", "Signup failed: " + errorBody); // Log l'erreur pour le débogage
                    } catch (IOException e) {
                        e.printStackTrace(); // Gère les exceptions IO
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                // En cas d'échec de l'appel API (erreur réseau ou autre), affiche un message d'erreur
                Toast.makeText(SignupActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignupActivity", "Error: ", t); // Log l'erreur pour le débogage
            }
        });
    }
}
