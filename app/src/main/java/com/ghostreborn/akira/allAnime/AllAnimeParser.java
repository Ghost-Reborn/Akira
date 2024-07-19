package com.ghostreborn.akira.allAnime;

import android.util.Log;

import com.ghostreborn.akira.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllAnimeParser {

    public static List<Anime> queryPopular() {
        List<Anime> animes = new ArrayList<>();
        try {
            JSONArray recommendations = new JSONObject(AllAnimeNetwork.queryPopular())
                    .getJSONObject("data")
                    .getJSONObject("queryPopular")
                    .getJSONArray("recommendations");

            for (int i = 0; i < recommendations.length(); i++) {
                JSONObject anime = recommendations.getJSONObject(i).getJSONObject("anyCard");
                String name = anime.optString("englishName", anime.getString("name"));
                animes.add(new Anime(name, anime.getString("thumbnail")));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animes;
    }

    public static List<Anime> searchAnime(String animeName) {
        List<Anime> animes = new ArrayList<>();
        try {
            JSONArray recommendations = new JSONObject(AllAnimeNetwork.searchAnime(animeName))
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");

            for (int i = 0; i < recommendations.length(); i++) {
                JSONObject anime = recommendations.getJSONObject(i);
                String name = anime.optString("englishName", anime.getString("name"));
                animes.add(new Anime(name, anime.getString("thumbnail")));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animes;
    }

}
