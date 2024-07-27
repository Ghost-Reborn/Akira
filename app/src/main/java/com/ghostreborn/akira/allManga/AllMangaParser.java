package com.ghostreborn.akira.allManga;

import android.util.Log;

import com.ghostreborn.akira.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllMangaParser {

    public static ArrayList<Anime> queryPopular() {
        ArrayList<Anime> animes = new ArrayList<>();
        try {
            JSONArray recommendations = new JSONObject(AllMangaNetwork.queryPopular())
                    .getJSONObject("data")
                    .getJSONObject("queryPopular")
                    .getJSONArray("recommendations");

            for (int i = 0; i < recommendations.length(); i++) {
                JSONObject anime = recommendations.getJSONObject(i).getJSONObject("anyCard");
                String id = anime.getString("_id");
                String name = anime.optString("englishName", anime.optString("name"));
                name = "null".equals(name) ? anime.getString("name") : name;
                String thumbnail = "https://wp.youtube-anime.com/aln.youtube-anime.com/" + anime.getString("thumbnail");
                animes.add(new Anime(id, "",name, thumbnail, "0"));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animes;
    }

    public static ArrayList<Anime> searchManga(String mangaName) {
        ArrayList<Anime> animes = new ArrayList<>();
        try {
            JSONArray edges = new JSONObject(AllMangaNetwork.searchManga(mangaName))
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");

            for (int i = 0; i < edges.length(); i++) {
                JSONObject anime = edges.getJSONObject(i);
                String id = anime.getString("_id");
                String name = anime.optString("englishName", anime.getString("name"));
                if ("null".equals(name)) name = anime.getString("name");
                animes.add(new Anime(id, "",name, anime.getString("thumbnail"), ""));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animes;
    }

}
