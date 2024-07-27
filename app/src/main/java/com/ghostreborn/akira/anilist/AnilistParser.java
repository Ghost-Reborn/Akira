package com.ghostreborn.akira.anilist;

import android.util.Log;

import androidx.core.text.HtmlCompat;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.model.Anime;
import com.ghostreborn.akira.model.AnimeDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnilistParser {

    public static void animeList(String type, String status) {
        Constants.animes = new ArrayList<>();
        String rawJSON = AnilistNetwork.animeList(type, status);
        try {
            JSONArray entries = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("MediaListCollection")
                    .getJSONArray("lists")
                    .getJSONObject(0)
                    .getJSONArray("entries");
            for (int i=0;i<entries.length();i++){
                JSONObject entry = entries.getJSONObject(i);
                JSONObject media = entry.getJSONObject("media");
                String id = media.getString("idMal");
                String title = media.getJSONObject("title").getString("english");
                String thumbnail = media.getJSONObject("coverImage").getString("medium");
                String progress = entry.getString("progress");
                Constants.animes.add(new Anime(id, title, thumbnail, progress));
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
    }

    public static void searchAnime(String anime) {
        Constants.animes = new ArrayList<>();
        String rawJSON = AnilistNetwork.searchAnime(anime);
        try {
            JSONArray media = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("Page")
                    .getJSONArray("media");
            for (int i=0;i<media.length();i++){
                JSONObject entry = media.getJSONObject(i);
                String id = entry.getString("idMal");
                String title = entry.getJSONObject("title").getString("native");
                String thumbnail = entry.getJSONObject("coverImage").getString("medium");
                Constants.animes.add(new Anime(id, title, thumbnail, ""));
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
    }

    public static void animeDetails(String id) {
        String rawJSON = AnilistNetwork.animeDetails(id);
        try {
            JSONObject mediaObject = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("Media");
            String animeName = mediaObject.getJSONObject("title").getString("english");
            String animeImage = mediaObject.getJSONObject("coverImage").getString("medium");
            String animeDescription = mediaObject.getString("description");
            animeDescription = HtmlCompat.fromHtml(animeDescription, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    .toString();
            String animeBanner = mediaObject.getString("bannerImage");
            JSONArray relations = mediaObject.getJSONObject("relations")
                    .getJSONArray("edges");
            String prequel = "", sequel = "";
            for (int i=0;i<relations.length();i++){
                JSONObject relation = relations.getJSONObject(i);
                String relationType = relation.getString("relationType");
                if ("PREQUEL".equals(relationType)) prequel = relation.getJSONObject("node").getString("idMal");
                if ("SEQUEL".equals(relationType)) sequel = relation.getJSONObject("node").getString("idMal");
            }

            Constants.animeDetails = new AnimeDetails(animeName, animeImage, animeDescription, animeBanner, prequel, sequel);

        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
    }

}
