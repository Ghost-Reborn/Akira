package com.ghostreborn.akira.anilist;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnilistParser {

    public static String connectAnilist(){
        OkHttpClient client = new OkHttpClient();
        String queryUrl = "https://anilist.co/api/v2/oauth/authorize?client_id=11820&response_type=token";
        Request request = new Request.Builder().url(queryUrl).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0").build();
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

}
