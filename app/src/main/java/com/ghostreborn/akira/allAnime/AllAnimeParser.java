package com.ghostreborn.akira.allAnime;

import android.util.Log;

import com.ghostreborn.akira.model.Anime;
import com.ghostreborn.akira.model.AnimeDetails;
import com.ghostreborn.akira.model.EpisodeDetails;

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
                String id = anime.getString("_id");
                String name = anime.optString("englishName", anime.getString("name"));
                if ("null".equals(name)) name = anime.getString("name");
                animes.add(new Anime(id, name, anime.getString("thumbnail")));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animes;
    }

    public static List<Anime> searchAnime(String animeName) {
        List<Anime> animes = new ArrayList<>();
        try {
            JSONArray edges = new JSONObject(AllAnimeNetwork.searchAnime(animeName))
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");

            for (int i = 0; i < edges.length(); i++) {
                JSONObject anime = edges.getJSONObject(i);
                String id = anime.getString("_id");
                String name = anime.optString("englishName", anime.getString("name"));
                if ("null".equals(name)) name = anime.getString("name");
                animes.add(new Anime(id, name, anime.getString("thumbnail")));
            }
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animes;
    }

    public static AnimeDetails animeDetails(String id) {
        AnimeDetails animeDetails = null;
        try {
            JSONObject show = new JSONObject(AllAnimeNetwork.animeDetails(id))
                    .getJSONObject("data")
                    .getJSONObject("show");
            String name = show.getString("englishName");
            if ("null".equals(name)) name = show.getString("name");
            // Thumbnail, description, banner, relatedShows
            String thumbnail = show.getString("thumbnail");
            String description = show.getString("description");
            String banner = show.getString("banner");
            JSONArray relatedShows = show.getJSONArray("relatedShows");
            String sequel = "";
            String prequel = "";
            for (int i = 0; i < relatedShows.length(); i++) {
                JSONObject relatedShow = relatedShows.getJSONObject(i);
                if (relatedShow.getString("relation").equals("prequel")) {
                    prequel = relatedShow.getString("showId");
                } else if (relatedShow.getString("relation").equals("sequel")) {
                    sequel = relatedShow.getString("showId");
                }
            }
            animeDetails = new AnimeDetails(name, thumbnail, description, banner, prequel, sequel);
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animeDetails;
    }

    public static EpisodeDetails episodeDetails(String id, String episode) {
        EpisodeDetails episodeDetails = null;
        try {
            JSONObject episodeJSON = new JSONObject(AllAnimeNetwork.episodeDetails(id,episode))
                    .getJSONObject("data")
                    .getJSONObject("episode");
            String episodeTitle = episodeJSON.getJSONObject("pageStatus").getString("notes");
            String episodeThumbnail ="https://wp.youtube-anime.com/aln.youtube-anime.com"+ episodeJSON.getJSONObject("episodeInfo").getJSONArray("thumbnails").getString(0);
            episodeDetails = new EpisodeDetails(episodeTitle, episodeThumbnail);
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return episodeDetails;
    }

}
