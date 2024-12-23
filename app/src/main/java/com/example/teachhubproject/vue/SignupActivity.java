package com.example.teachhubproject.vue;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teachhubproject.R;
import com.example.teachhubproject.dto.SignUpRequest;
import com.example.teachhubproject.dto.SignUpResponse;
import com.example.teachhubproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editEmail, editMotDePasse;
    private Button btnSignup;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        editEmail = findViewById(R.id.editEmail);
        editMotDePasse = findViewById(R.id.editMotDePasse);
        btnSignup = findViewById(R.id.btnSignup);

        // Get role from Intent extras
        role = getIntent().getStringExtra("role");

        btnSignup.setOnClickListener(view -> performSignup(role));
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void performSignup(String role) {
        String nom = editNom.getText().toString();
        String prenom = editPrenom.getText().toString();
        String email = editEmail.getText().toString();
        String motDePasse = editMotDePasse.getText().toString();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            editEmail.setError("Adresse e-mail invalide");
            editEmail.requestFocus();
            return;
        }


        // Create the request object
        SignUpRequest signUpRequest = new SignUpRequest(nom, prenom, email, motDePasse, role);

        // Make the API call
        AuthService authService = RetrofitClient.getClient().create(AuthService.class);
        Call<SignUpResponse> call = authService.registerUser(signUpRequest);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(SignupActivity.this, "Signup failed: " + errorBody, Toast.LENGTH_SHORT).show();
                        Log.e("SignupActivity", "Signup failed: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignupActivity", "Error: ", t);
            }
        });
    }
}
