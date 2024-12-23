package com.example.teachhubproject.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teachhubproject.R;
import com.example.teachhubproject.dto.LoginRequest;
import com.example.teachhubproject.dto.LoginResponse;
import com.example.teachhubproject.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signupEtudiantButton, signupEnseignantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupEtudiantButton = findViewById(R.id.signupEtudiantButton);


        loginButton.setOnClickListener(v -> login());

        signupEtudiantButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            intent.putExtra("role", "ROLE_ETUDIANT");
            startActivity(intent);
        });


    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (email.isEmpty()) {
            emailEditText.setError("Veuillez entrer une adresse e-mail");
            emailEditText.requestFocus();
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

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setMotDePasse(password);

        AuthService authService = RetrofitClient.getClient().create(AuthService.class);
        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jwt = response.body().getJwt();
                    String role = response.body().getRole();
                    Long etudiantId = response.body().getId();

                    Log.d("LoginActivity", "Connexion réussie : " + role);
                    Log.d("LoginActivity", "Etudiant ID: " + etudiantId);
                    Toast.makeText(LoginActivity.this, "Connexion réussie : " + role, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, CoursActivity.class);
                    intent.putExtra("etudiantId", etudiantId);
                    startActivity(intent);


                } else {
                    Toast.makeText(LoginActivity.this, "Erreur d'authentification", Toast.LENGTH_SHORT).show();
                    Log.e("LoginActivity", "Authentication error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginActivity", "Erreur : " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
