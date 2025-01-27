package com.ghostreborn.akira.allAnime;

import android.util.Log;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.model.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnimeParser {

    public static void animeDetails(String id) {
        try {
            JSONObject show = new JSONObject(AllAnimeNetwork.animeDetails(id))
                    .getJSONObject("data")
                    .getJSONObject("show");
            JSONArray availableEpisodes = show.getJSONObject("availableEpisodesDetail").getJSONArray("sub");
            ArrayList<String> episodes = new ArrayList<>();
            for (int i = availableEpisodes.length() - 1; i >= 0; i--) {
                episodes.add(availableEpisodes.getString(i));
            }
            Constants.groupedEpisodes = groupEpisodes(episodes);
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
    }

    public static ArrayList<ArrayList<String>> groupEpisodes(ArrayList<String> episodes) {
        ArrayList<ArrayList<String>> groupedEpisodes = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < episodes.size()) {
            int endIndex = Math.min(startIndex + 15, episodes.size());
            groupedEpisodes.add(new ArrayList<>(episodes.subList(startIndex, endIndex)));
            startIndex = endIndex;
        }
        return groupedEpisodes;
    }

    public static void getEpisodeDetails(ArrayList<String> episodes) {
        ArrayList<Episode> out = new ArrayList<>();

        for (int i=0;i< episodes.size(); i++) {
            try {
                // Fetch the JSON response
                String episodeDetailsJson = AllAnimeNetwork.episodeDetails(Constants.allAnimeID, episodes.get(i));
                JSONObject jsonResponse = new JSONObject(episodeDetailsJson);

                JSONObject dataObject = jsonResponse.getJSONObject("data");
                JSONObject episodeObject = dataObject.getJSONObject("episode");

                String episodeThumbnail = "https://wp.youtube-anime.com/s4.anilist.co/file/anilistcdn/media/anime/cover/large/nx21-tXMN3Y20PIL9.jpg?w=250";
                Log.e("TAG", episodeDetailsJson);
                if (episodeObject.isNull("episodeInfo")){
                    out.add(new Episode(episodes.get(i), "", episodeThumbnail));
                    continue;
                }

                JSONObject episodeInfo = episodeObject.getJSONObject("episodeInfo");
                String episodeTitle = episodeInfo.optString("notes", "Episode " + episodes.get(i));

                JSONArray thumbnails = episodeInfo.optJSONArray("thumbnails");
                if (thumbnails != null && thumbnails.length() > 0) {
                    String thumbnailUrl = thumbnails.optString(0, "");
                    if (!thumbnailUrl.isEmpty()) {
                        episodeThumbnail = "https://wp.youtube-anime.com/aln.youtube-anime.com" + thumbnailUrl;
                    }
                }

                // Add the new Episode object to the list
                out.add(new Episode(episodes.get(i), episodeTitle, episodeThumbnail));
            } catch (JSONException e) {
                Log.e("AllAnimeParser", "Error parsing JSON: ", e);
                out.add(null);
            }
        }
        Constants.parsedEpisodes = out;
    }

    private static ArrayList<String> episodeUrls(String id, String episode) {
        ArrayList<String> sources = new ArrayList<>();
        try {
            JSONArray sourceUrls = new JSONObject(AllAnimeNetwork.episodeUrls(id, episode))
                    .getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONArray("sourceUrls");

            for (int i = 0; i < sourceUrls.length(); i++) {
                String sourceUrl = sourceUrls.getJSONObject(i).getString("sourceUrl");
                if (sourceUrl.contains("--") && sourceUrl.length() > 138) {
                    String decrypted = decryptAllAnimeServer(sourceUrl.substring(2)).replace("clock", "clock.json");
                    if (!decrypted.contains("fast4speed")) {
                        sources.add("https://allanime.day" + decrypted);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
        return sources;
    }

    public static ArrayList<String> getSourceUrls(String id, String episode) {
        ArrayList<String> sources = episodeUrls(id, episode);
        ArrayList<String> out = new ArrayList<>();

        for (String source : sources) {
            try {
                String rawJSON = getJSON(source);
                JSONArray linksArray = new JSONObject(rawJSON).getJSONArray("links");
                for (int j = 0; j < linksArray.length(); j++) {
                    out.add(linksArray.getJSONObject(j).getString("link"));
                }
            } catch (JSONException e) {
                Log.e("TAG", "Error parsing JSON: ", e);
            }
        }
        return out;
    }

    public static String getJSON(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Referer", "https://allanime.to")
                .header("Cipher", "AES256-SHA256")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "NULL";
        } catch (IOException e) {
            Log.e("TAG", "Error fetching JSON: ", e);
        }
        return "{}";
    }

    public static String decryptAllAnimeServer(String decrypt) {
        StringBuilder decryptedString = new StringBuilder();
        for (int i = 0; i < decrypt.length(); i += 2) {
            int dec = Integer.parseInt(decrypt.substring(i, i + 2), 16);
            decryptedString.append((char) (dec ^ 56));
        }
        return decryptedString.toString();
    }

    public static String allAnimeIdWithMalId(String animeName, String malId){
        String rawJSON = AllAnimeNetwork.allAnimeIdWithMalId(animeName);
        try{
            JSONArray edges = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");
            for (int i=0;i<edges.length();i++){
                JSONObject anime = edges.getJSONObject(i);
                if (anime.getString("malId").equals(malId)){
                    return anime.getString("_id");
                }
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
        return "";
    }

}
