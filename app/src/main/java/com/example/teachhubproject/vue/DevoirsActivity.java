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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.recyclerview.widget.GridLayoutManager;

public class DevoirsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDevoirs;
    private RecyclerView recyclerViewDocuments;
    private DevoirAdapter devoirAdapter;
    private DocumentAdapter documentAdapter;
    private List<Devoir> devoirList;
    private List<Document> documentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_devoirs);

        recyclerViewDevoirs = findViewById(R.id.recyclerViewDevoirs);
        recyclerViewDocuments = findViewById(R.id.recyclerViewDocuments);

        // Utilisation d'un GridLayoutManager pour afficher les devoirs sous forme de grille
        int numberOfColumns = 2; // Vous pouvez ajuster ce nombre selon vos besoins
        recyclerViewDevoirs.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerViewDocuments.setLayoutManager(new LinearLayoutManager(this));

        devoirList = new ArrayList<>();
        documentList = new ArrayList<>();
        devoirAdapter = new DevoirAdapter(devoirList);
        documentAdapter = new DocumentAdapter(documentList);
        recyclerViewDevoirs.setAdapter(devoirAdapter);
        recyclerViewDocuments.setAdapter(documentAdapter);

        Integer coursId = getIntent().getIntExtra("coursId", -1);
        if (coursId != -1) {
            fetchDevoirs(coursId);
            fetchDocuments(coursId); // Ajouté pour récupérer les documents
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchDevoirs(Integer coursId) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);

        Call<List<Devoir>> call = apiService.getDevoirsByCours(coursId);

        call.enqueue(new Callback<List<Devoir>>() {
            @Override
            public void onResponse(Call<List<Devoir>> call, Response<List<Devoir>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DevoirsActivity", "Devoirs reçus: " + response.body().toString());
                    devoirList.clear();
                    devoirList.addAll(response.body());
                    devoirAdapter.notifyDataSetChanged();
                } else {
                    Log.e("DevoirsActivity", "Erreur : " + response.message());
                    Toast.makeText(DevoirsActivity.this, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Devoir>> call, Throwable t) {
                Toast.makeText(DevoirsActivity.this, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDocuments(Integer coursId) {
        CourService apiService = RetrofitClient.getClient().create(CourService.class);

        Call<List<Document>> call = apiService.getDocumentsByCourId(coursId);

        call.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DevoirsActivity", "Documents reçus: " + response.body().toString());
                    documentList.clear();
                    documentList.addAll(response.body());
                    documentAdapter.notifyDataSetChanged();
                } else {
                    Log.e("DevoirsActivity", "Erreur : " + response.message());
                    Toast.makeText(DevoirsActivity.this, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                Toast.makeText(DevoirsActivity.this, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
