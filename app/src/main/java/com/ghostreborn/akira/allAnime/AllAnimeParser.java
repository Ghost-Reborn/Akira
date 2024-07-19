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
        String rawJSON = AllAnimeNetwork.queryPopular();
        try {
            JSONArray recommendation = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("queryPopular")
                    .getJSONArray("recommendations");
            for (int i = 0; i < recommendation.length(); i++) {
                JSONObject anime = recommendation.getJSONObject(i)
                        .getJSONObject("anyCard");
                String name = anime.getString("name");
                String englishName = anime.getString("englishName");
                if (!englishName.equals("null")){
                    name = englishName;
                }
                String thumbnail = anime.getString("thumbnail");
                animes.add(new Anime(name, thumbnail));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: " + e);
        }
        return animes;
    }

    public static List<Anime> searchAnime(String animeName) {
        List<Anime> animes = new ArrayList<>();
        String rawJSON = AllAnimeNetwork.searchAnime(animeName);
        try {
            JSONArray recommendation = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");
            for (int i = 0; i < recommendation.length(); i++) {
                JSONObject anime = recommendation.getJSONObject(i);
                String name = anime.getString("name");
                String englishName = anime.getString("englishName");
                if (!englishName.equals("null")){
                    name = englishName;
                }
                String thumbnail = anime.getString("thumbnail");
                animes.add(new Anime(name, thumbnail));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: " + e);
        }
        return animes;
    }

}
