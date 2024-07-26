package com.ghostreborn.akira.jikan;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JikanParser {

    public static String getJSON(String path) {
        String url = "https://api.jikan.moe/v4/" + path;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0").build();
        String rawJson = "{}";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rawJson;
    }

    public static String getTopAnime(){
        String path = "top/anime";
        return getJSON(path);
    }

}
