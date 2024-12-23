package com.example.teachhubproject.dto;

public class SignUpResponse {
    private String message;
    private Object data;

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
