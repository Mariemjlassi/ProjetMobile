package com.example.teachhubproject.service;

import com.example.teachhubproject.model.Cour;
import com.example.teachhubproject.model.Devoir;
import com.example.teachhubproject.model.Document;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CourService {
    @GET("/cours/etudiant/{id}")
    Call<List<Cour>> getCoursByEtudiant(@Path("id") Long id);

    @POST("/{courseCode}/inviteById/{studentId}")
    Call<Void> inviteStudentByCode(@Path("courseCode") String courseCode, @Path("studentId") Long studentId);

    @GET("/Devoirs/{idCours}")
    Call<List<Devoir>> getDevoirsByCours(@Path("idCours") Integer coursId);

    @GET("/cours/{courId}/documents")
    Call<List<Document>> getDocumentsByCourId(@Path("courId") Integer courId);




}
