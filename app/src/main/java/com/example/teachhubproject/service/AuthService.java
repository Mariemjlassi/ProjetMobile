package com.example.teachhubproject.service;



import com.example.teachhubproject.dto.LoginRequest;
import com.example.teachhubproject.dto.LoginResponse;
import com.example.teachhubproject.dto.SignUpRequest;
import com.example.teachhubproject.dto.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignUpResponse> registerUser(@Body SignUpRequest signUpRequest);
}