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

public class CourAdapter extends RecyclerView.Adapter<CourAdapter.CourViewHolder> {
    private List<Cour> cours;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Integer coursId);
    }

    public CourAdapter(List<Cour> cours, OnItemClickListener listener) {
        this.cours = cours;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cours, parent, false);
        return new CourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourViewHolder holder, int position) {
        Cour cour = cours.get(position);
        holder.nomTextView.setText(cour.getNom());
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, DevoirsActivity.class);
            intent.putExtra("coursId", cour.getIdCours());
            context.startActivity(intent);
        });}

    @Override
    public int getItemCount() {
        return cours.size();
    }

    public static class CourViewHolder extends RecyclerView.ViewHolder {
        TextView nomTextView;

        public CourViewHolder(View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nomTextView);
        }
    }
}
