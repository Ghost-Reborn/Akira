package com.ghostreborn.akira.anilist;

import android.util.Log;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnilistParser {

    public static void animeList(String token, String userId, String type, String status) {
        Constants.animes = new ArrayList<>();
        String rawJSON = AnilistNetwork.animeList(token, userId, type, status);
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
                Constants.animes.add(new Anime(id, title, thumbnail));
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
    }

}
