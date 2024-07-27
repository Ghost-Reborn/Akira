package com.ghostreborn.akira.jikan;

import android.util.Log;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    public static void getTopAnime() {
        Constants.animes = new ArrayList<>();
        String path = "top/anime?limit=24";
        try {
            JSONArray dataArray = new JSONObject(getJSON(path))
                    .getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String malID = dataObject.getString("mal_id");
                String title = dataObject.getString("title_english");
                String thumbnail = dataObject.getJSONObject("images")
                        .getJSONObject("jpg")
                        .getString("image_url");
                Constants.animes.add(new Anime(malID, title, thumbnail));
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
    }

    public static void searchAnime(String anime) {
        Constants.animes = new ArrayList<>();
        String path = "anime?q=" + anime + "&sfw?limit=24";
        try {
            JSONArray dataArray = new JSONObject(getJSON(path))
                    .getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String malID = dataObject.getString("mal_id");
                String title = dataObject.getString("title_english");
                String thumbnail = dataObject.getJSONObject("images")
                        .getJSONObject("jpg")
                        .getString("image_url");
                Constants.animes.add(new Anime(malID, title, thumbnail));
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
    }

}
