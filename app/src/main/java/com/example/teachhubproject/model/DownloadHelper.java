package com.example.teachhubproject.model;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class DownloadHelper {

    public static void downloadFile(Context context, String url, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Téléchargement du document");
        request.setTitle(fileName);

        // Enregistrer le fichier dans le dossier Download
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        // Obtenez le service DownloadManager
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request);
            Toast.makeText(context, "Téléchargement en cours...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Erreur : Impossible de lancer le téléchargement", Toast.LENGTH_SHORT).show();
        }
    }
}
