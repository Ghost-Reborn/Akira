package com.ghostreborn.akira.allAnime;

import android.util.Log;

import com.ghostreborn.akira.model.Anime;
import com.ghostreborn.akira.model.AnimeDetails;
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

    public static ArrayList<Anime> queryPopular() {
        ArrayList<Anime> animes = new ArrayList<>();
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

    public static ArrayList<Anime> searchAnime(String animeName) {
        ArrayList<Anime> animes = new ArrayList<>();
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
            JSONArray availableEpisodes = show.getJSONObject("availableEpisodesDetail").getJSONArray("sub");
            ArrayList<String> episodes = new ArrayList<>();
            for (int i = availableEpisodes.length() - 1; i >= 0; i--) {
                episodes.add(availableEpisodes.getString(i));
            }
            animeDetails = new AnimeDetails(id, name, thumbnail, description, banner, prequel, sequel, episodes);
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return animeDetails;
    }

    public static Episode episodeDetails(String id, String episodeNumber) {
        Episode episode = null;
        try {
            JSONObject episodeJSON = new JSONObject(AllAnimeNetwork.episodeDetails(id, episodeNumber))
                    .getJSONObject("data")
                    .getJSONObject("episode");
            String episodeTitle = "Episode " + episodeNumber;
            if (!episodeJSON.getString("episodeInfo").equals("null")) {
                if (!episodeJSON.getJSONObject("episodeInfo").getString("notes").equals("null")) {
                    episodeTitle = episodeJSON.getJSONObject("episodeInfo").getString("notes");
                }
            }
            String episodeThumbnail = "https://wp.youtube-anime.com/s4.anilist.co/file/anilistcdn/media/anime/cover/large/nx21-tXMN3Y20PIL9.jpg?w=250";
            if (!episodeJSON.getString("episodeInfo").equals("null")) {
                if (!episodeJSON.getJSONObject("episodeInfo").getString("thumbnails").equals("null")) {
                    episodeThumbnail = "https://wp.youtube-anime.com/aln.youtube-anime.com" + episodeJSON.getJSONObject("episodeInfo").getJSONArray("thumbnails").getString(0);
                }
            }
            episode = new Episode(episodeNumber,episodeTitle, episodeThumbnail);
        } catch (JSONException e) {
            Log.e("AllAnimeParser", "Error parsing JSON: ", e);
        }
        return episode;
    }

    public static ArrayList<Episode> getEpisodeDetails(String id, ArrayList<Episode> episodes){
        ArrayList<Episode> out = new ArrayList<>();
        for (int i=0;i<episodes.size();i++){
            out.add(episodeDetails(id, episodes.get(i).getEpisodeNumber()));
        }
        return out;
    }

    private static ArrayList<String> episodeUrls(String id, String episode) {
        ArrayList<String> sources = new ArrayList<>();
        String rawJSON = AllAnimeNetwork.episodeUrls(id, episode);
        try {
            JSONArray sourceUrls = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONArray("sourceUrls");
            for (int i = 0; i < sourceUrls.length(); i++) {
                String sourceUrl = sourceUrls.getJSONObject(i).getString("sourceUrl");
                if (sourceUrl.contains("--") && sourceUrl.length() > 138) {
                    String decrypted = decryptAllAnimeServer(sourceUrl.substring(2)).replace("clock", "clock.json");
                    if (decrypted.contains("fast4speed")) {
                        continue;
                    }
                    String apiUrl = "https://allanime.day" + decrypted;
                    sources.add(apiUrl);
                }
            }
        } catch (JSONException e) {
            Log.e("TAG", "Error parsing JSON: ", e);
        }
        return sources;
    }

    public static ArrayList<String> getSourceUrls(String id, String episode){
        ArrayList<String> sources = episodeUrls(id,episode);
        ArrayList<String> out = new ArrayList<>();
        for (int i=0;i<sources.size();i++){
            Log.e("TAG", sources.get(i));
            String rawJSON = getJSON(sources.get(i));
            try{
                JSONArray linksArray = new JSONObject(rawJSON)
                        .getJSONArray("links");
                for (int j=0;j<linksArray.length();j++){
                    String link = linksArray.getJSONObject(j).getString("link");
                    out.add(link);
                }
            } catch (JSONException e) {
                Log.e("TAG", "Error parsing JSON: ", e);
            }
        }
        return out;
    }

    public static String getJSON(String url) {
        OkHttpClient client = new OkHttpClient();
        Log.e("TAG", url);
        Request request = new Request.Builder().url(url).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rawJson;
    }

    public static String decryptAllAnimeServer(String decrypt) {
        StringBuilder decryptedString = new StringBuilder();

        for (int i = 0; i < decrypt.length(); i += 2) {
            String hex = decrypt.substring(i, i + 2);
            int dec = Integer.parseInt(hex, 16);
            int xor = dec ^ 56;
            String oct = String.format("%03o", xor);
            char decryptedChar = (char) Integer.parseInt(oct, 8);
            decryptedString.append(decryptedChar);
        }

        return decryptedString.toString();
    }

}
