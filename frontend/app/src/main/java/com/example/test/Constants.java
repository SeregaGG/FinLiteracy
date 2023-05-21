package com.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;

public class Constants {
    public static OkHttpClient okHttp_client = new OkHttpClient();
    public static ObjectMapper object_mapper = new ObjectMapper();

    public final static String server_url = "http://81.200.149.240:8000";
    public final static String loc_entertainment_center = "entertainment_center";
    public final static String loc_school = "school";
    public final static String loc_bank = "bank";
    public final static String loc_shop = "shop";
    public final static String loc_fin_org = "fin_org";
    public final static int coins = 500;
    public final static int time = 2700; // Время игры в секундах

    public static String token = "NONE";
    public static String first_name = "NONE";
    public static String second_name = "NONE";
    public static String city = "NONE";
    public static String school = "NONE";
    public static String class_name = "NONE";
}
