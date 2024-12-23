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

public class DevoirAdapter extends RecyclerView.Adapter<DevoirAdapter.DevoirViewHolder> {
    private List<Devoir> devoirs;

    public DevoirAdapter(List<Devoir> devoirs) {
        this.devoirs = devoirs;
    }

    @NonNull
    @Override
    public DevoirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.devoir_item, parent, false);
        return new DevoirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DevoirViewHolder holder, int position) {
        Devoir devoir = devoirs.get(position);

        // Bind data to the TextViews with bold labels
        holder.typedevoirTextView.setText(Html.fromHtml("<b>Type de devoir:</b> " + devoir.getTypedevoir()));
        holder.descriptionTextView.setText(Html.fromHtml("<b>Description:</b> " + devoir.getDescription()));
        holder.ponderationTextView.setText(Html.fromHtml("<b>Pondération:</b> " + String.valueOf(devoir.getPonderation())));
        holder.baremeTextView.setText(Html.fromHtml("<b>Barème:</b> " + devoir.getBareme()));
        holder.dateLimiteTextView.setText(Html.fromHtml("<b>Date limite:</b> " + devoir.getFormattedDateLimite()));
        holder.statutTextView.setText(Html.fromHtml("<b>Statut:</b> " + devoir.getStatut()));
    }


    @Override
    public int getItemCount() {
        return devoirs.size();
    }

    public static class DevoirViewHolder extends RecyclerView.ViewHolder {
        TextView typedevoirTextView;
        TextView descriptionTextView;
        TextView ponderationTextView;
        TextView baremeTextView;
        TextView dateLimiteTextView;
        TextView statutTextView;

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
