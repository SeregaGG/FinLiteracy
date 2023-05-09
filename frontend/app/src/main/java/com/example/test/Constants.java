package com.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;

public class Constants {
    public static OkHttpClient okHttpClient = new OkHttpClient();
    public static ObjectMapper objectMapper = new ObjectMapper();
}
