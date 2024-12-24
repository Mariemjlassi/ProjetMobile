package com.example.teachhubproject.vue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;

import com.example.teachhubproject.R;
import com.example.teachhubproject.model.Devoir;

import java.util.List;

// Adapter pour afficher une liste de devoirs dans un RecyclerView
public class DevoirAdapter extends RecyclerView.Adapter<DevoirAdapter.DevoirViewHolder> {
    // Liste des devoirs à afficher
    private List<Devoir> devoirs;

    // Constructeur pour initialiser l'adapter avec une liste de devoirs
    public DevoirAdapter(List<Devoir> devoirs) {
        this.devoirs = devoirs;
    }

    // Méthode pour créer une vue (item) pour chaque élément de la liste
    @NonNull
    @Override
    public DevoirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Charger le layout XML pour un élément de devoir
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.devoir_item, parent, false);
        return new DevoirViewHolder(view);
    }

    // Méthode pour lier les données d'un devoir à une vue spécifique
    @Override
    public void onBindViewHolder(@NonNull DevoirViewHolder holder, int position) {
        // Récupérer le devoir correspondant à la position actuelle
        Devoir devoir = devoirs.get(position);

        // Remplir les TextViews avec les informations du devoir
        holder.typedevoirTextView.setText(Html.fromHtml("<b>Type de devoir:</b> " + devoir.getTypedevoir()));
        holder.descriptionTextView.setText(Html.fromHtml("<b>Description:</b> " + devoir.getDescription()));
        holder.ponderationTextView.setText(Html.fromHtml("<b>Pondération:</b> " + String.valueOf(devoir.getPonderation())));
        holder.baremeTextView.setText(Html.fromHtml("<b>Barème:</b> " + devoir.getBareme()));
        holder.dateLimiteTextView.setText(Html.fromHtml("<b>Date limite:</b> " + devoir.getFormattedDateLimite()));
        holder.statutTextView.setText(Html.fromHtml("<b>Statut:</b> " + devoir.getStatut()));
    }

    // Retourner le nombre total de devoirs dans la liste
    @Override
    public int getItemCount() {
        return devoirs.size();
    }

    // Classe interne pour gérer les vues de chaque élément de devoir
    public static class DevoirViewHolder extends RecyclerView.ViewHolder {
        // Définition des TextViews pour afficher les informations
        TextView typedevoirTextView;
        TextView descriptionTextView;
        TextView ponderationTextView;
        TextView baremeTextView;
        TextView dateLimiteTextView;
        TextView statutTextView;

        // Constructeur pour initialiser les vues depuis le layout
        public DevoirViewHolder(View itemView) {
            super(itemView);
            typedevoirTextView = itemView.findViewById(R.id.typedevoirTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            ponderationTextView = itemView.findViewById(R.id.ponderationTextView);
            baremeTextView = itemView.findViewById(R.id.baremeTextView);
            dateLimiteTextView = itemView.findViewById(R.id.dateLimiteTextView);
            statutTextView = itemView.findViewById(R.id.statutTextView);
        }
    }
}
