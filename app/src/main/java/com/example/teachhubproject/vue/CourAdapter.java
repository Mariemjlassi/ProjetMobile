package com.example.teachhubproject.vue;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachhubproject.R;
import com.example.teachhubproject.model.Cour;

import java.util.List;

// Adaptateur pour afficher une liste de cours dans un RecyclerView
public class CourAdapter extends RecyclerView.Adapter<CourAdapter.CourViewHolder> {

    private List<Cour> cours; // Liste des cours à afficher
    private OnItemClickListener listener; // Interface pour gérer les clics sur les éléments

    // Interface pour définir une action à exécuter lors d'un clic sur un élément
    public interface OnItemClickListener {
        void onItemClick(Integer coursId); // Méthode appelée lors d'un clic avec l'ID du cours
    }

    // Constructeur pour initialiser l'adaptateur avec une liste de cours et un listener
    public CourAdapter(List<Cour> cours, OnItemClickListener listener) {
        this.cours = cours; // Initialisation de la liste des cours
        this.listener = listener; // Initialisation du gestionnaire de clics
    }

    @NonNull
    @Override
    public CourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Création d'une vue pour chaque élément du RecyclerView à partir du fichier XML `cours`
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cours, parent, false);
        return new CourViewHolder(view); // Retourne un ViewHolder pour cette vue
    }

    @Override
    public void onBindViewHolder(@NonNull CourViewHolder holder, int position) {
        // Récupération du cours correspondant à la position actuelle
        Cour cour = cours.get(position);

        // Affichage du nom du cours dans le TextView
        holder.nomTextView.setText(cour.getNom());

        // Ajout d'un listener de clic à l'élément de la liste
        holder.itemView.setOnClickListener(v -> {
            // Récupération du contexte actuel
            Context context = holder.itemView.getContext();

            // Création d'une intention pour passer à l'activité DevoirsActivity
            Intent intent = new Intent(context, DevoirsActivity.class);

            // Ajout de l'ID du cours comme donnée à transmettre à l'activité suivante
            intent.putExtra("coursId", cour.getIdCours());

            // Démarrage de l'activité DevoirsActivity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Retourne le nombre total de cours dans la liste
        return cours.size();
    }

    // ViewHolder : classe interne pour représenter chaque élément de la liste
    public static class CourViewHolder extends RecyclerView.ViewHolder {
        TextView nomTextView; // TextView pour afficher le nom du cours

        public CourViewHolder(View itemView) {
            super(itemView);
            // Liaison du TextView défini dans le fichier XML `cours`
            nomTextView = itemView.findViewById(R.id.nomTextView);
        }
    }
}
