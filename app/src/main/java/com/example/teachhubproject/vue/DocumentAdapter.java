// Package de la vue qui contient les classes liées à l'interface utilisateur.
package com.example.teachhubproject.vue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachhubproject.R; // Référence aux ressources de l'application.
import com.example.teachhubproject.model.Document; // Classe représentant les documents.
import com.example.teachhubproject.model.SerializationHelper; // Aide pour la sérialisation JSON.

import java.util.ArrayList;
import java.util.List;

// Classe d'adaptateur pour gérer l'affichage des documents dans un RecyclerView.
public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

    // Liste des documents à afficher.
    private List<Document> documentList;

    // Constructeur de l'adaptateur. Initialise la liste des documents.
    public DocumentAdapter(List<Document> documentList) {
        this.documentList = documentList;
    }

    // Création de la vue pour chaque élément du RecyclerView.
    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate le layout d'un item de document et retourne un ViewHolder.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);
        return new DocumentViewHolder(view);
    }

    // Liaison des données d'un document spécifique avec la vue correspondante.
    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        // Récupère le document à la position donnée.
        Document document = documentList.get(position);
        // Définit le nom du document dans le TextView.
        holder.nameTextView.setText(document.getName());

        // Gestion du clic sur le bouton "saveButton".
        holder.saveButton.setOnClickListener(v -> {
            // Charge les documents existants depuis le fichier JSON.
            List<Document> savedDocuments = SerializationHelper.loadDocuments(holder.itemView.getContext(), "documents.json");

            // Si des documents sont déjà sauvegardés, ajoute le document actuel.
            if (savedDocuments != null) {
                savedDocuments.add(document);
            } else {
                // Sinon, crée une nouvelle liste et ajoute le document actuel.
                savedDocuments = new ArrayList<>();
                savedDocuments.add(document);
            }

            // Sauvegarde la liste mise à jour dans le fichier JSON.
            SerializationHelper.saveDocuments(holder.itemView.getContext(), savedDocuments, "documents.json");

            // Affiche un message de confirmation.
            Toast.makeText(holder.itemView.getContext(), "Document sauvegardé!", Toast.LENGTH_SHORT).show();
        });
    }

    // Retourne le nombre total de documents dans la liste.
    @Override
    public int getItemCount() {
        return documentList.size();
    }

    // Classe interne pour gérer les vues de chaque élément (ViewHolder).
    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        // Déclaration des vues de l'élément (nom et boutons).
        TextView nameTextView;
        Button saveButton;

        // Constructeur pour initialiser les vues de l'élément.
        public DocumentViewHolder(View itemView) {
            super(itemView);
            // Associe les vues aux ID définis dans le layout XML.
            nameTextView = itemView.findViewById(R.id.nameTextView);
            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }
}
