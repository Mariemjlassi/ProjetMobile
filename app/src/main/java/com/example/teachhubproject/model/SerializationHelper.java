package com.example.teachhubproject.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SerializationHelper {

    // Instance de Gson pour la conversion entre objets Java et JSON
    private static final Gson gson = new Gson();

    /**
     * Sauvegarde une liste de documents dans un fichier JSON.
     * @param context Le contexte de l'application (pour accéder aux fichiers internes).
     * @param documents La liste de documents à sauvegarder.
     * @param filename Le nom du fichier dans lequel les documents seront sauvegardés.
     */
    public static void saveDocuments(Context context, List<Document> documents, String filename) {
        try (
                // Ouverture du fichier en mode écriture privée
                FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                OutputStreamWriter writer = new OutputStreamWriter(fos)
        ) {
            // Conversion de la liste de documents en une chaîne JSON
            String json = gson.toJson(documents);
            // Écriture de la chaîne JSON dans le fichier
            writer.write(json);
        } catch (Exception e) {
            // Gestion des erreurs, ici on imprime l'exception
            e.printStackTrace();
        }
    }

    /**
     * Charge une liste de documents depuis un fichier JSON.
     * @param context Le contexte de l'application (pour accéder aux fichiers internes).
     * @param filename Le nom du fichier à charger.
     * @return La liste des documents chargée depuis le fichier, ou null en cas d'erreur.
     */
    @SuppressWarnings("unchecked")
    public static List<Document> loadDocuments(Context context, String filename) {
        try (
                // Ouverture du fichier en mode lecture
                FileInputStream fis = context.openFileInput(filename);
                InputStreamReader reader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            // Type spécifique de la liste de documents
            Type listType = new TypeToken<List<Document>>() {}.getType();
            // Conversion du contenu du fichier JSON en une liste de documents
            return gson.fromJson(bufferedReader, listType);
        } catch (Exception e) {
            // Gestion des erreurs, ici on imprime l'exception
            e.printStackTrace();
            // Si une erreur se produit, la méthode retourne null
            return null;
        }
    }
}
