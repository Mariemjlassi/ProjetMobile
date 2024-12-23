package com.example.teachhubproject.vue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachhubproject.R;
import com.example.teachhubproject.model.Document;
import com.example.teachhubproject.model.DownloadHelper;
import com.example.teachhubproject.model.SerializationHelper;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {
    private List<Document> documentList;

    public DocumentAdapter(List<Document> documentList) {
        this.documentList = documentList;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        Document document = documentList.get(position);
        holder.nameTextView.setText(document.getName());



        holder.saveButton.setOnClickListener(v -> {
            // Sauvegarder les documents en JSON
            List<Document> savedDocuments = SerializationHelper.loadDocuments(holder.itemView.getContext(), "documents.json");
            if (savedDocuments != null) {
                savedDocuments.add(document);
            } else {
                savedDocuments = new ArrayList<>();
                savedDocuments.add(document);
            }
            SerializationHelper.saveDocuments(holder.itemView.getContext(), savedDocuments, "documents.json");
            Toast.makeText(holder.itemView.getContext(), "Document sauvegardé!", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button downloadButton;
        Button saveButton;

        public DocumentViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);

            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }
}
