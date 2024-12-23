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

    private static final Gson gson = new Gson();
    public static void saveDocuments(Context context, List<Document> documents, String filename) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            String json = gson.toJson(documents);
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Document> loadDocuments(Context context, String filename) {
        try (FileInputStream fis = context.openFileInput(filename);
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            Type listType = new TypeToken<List<Document>>() {}.getType();
            return gson.fromJson(bufferedReader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

