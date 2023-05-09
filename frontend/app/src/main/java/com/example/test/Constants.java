package com.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;

public class Constants {
    public static OkHttpClient okHttp_client = new OkHttpClient();
    public static ObjectMapper object_mapper = new ObjectMapper();

    public static String server_url = "http://81.200.149.240:8000";
    public static String loc_entertainment_center = "entertainment_center";
    public static String loc_school = "school";
    public static String loc_bank = "bank";
    public static String loc_shop = "shop";
    public static String loc_fin_org = "fin_org";

    public static int coins = 500;
}
