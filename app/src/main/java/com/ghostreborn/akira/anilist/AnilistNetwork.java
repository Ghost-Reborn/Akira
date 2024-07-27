package com.ghostreborn.akira.anilist;

import android.util.Log;

import com.ghostreborn.akira.Constants;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnilistNetwork {

    private static String connectAnilist(String qraph) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("query", qraph);
        } catch (Exception e) {
            Log.e("TAG", "Error putting json: ", e);
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://graphql.anilist.co")
                .post(body)
                .addHeader("Authorization", "Bearer " + Constants.userToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException ex) {
            Log.e("TAG", "Error: ", ex);
        }
        return "{}";
    }

    public static String animeList(String type, String status) {
        String graph = "query{\n" +
                "  MediaListCollection(userId:" + Constants.userID + ",type:" + type + ",status:" + status + "){\n" +
                "    lists{\n" +
                "      entries {\n" +
                "        id\n" +
                "        media{ \n" +
                "          idMal\n" +
                "          title {\n" +
                "            english\n" +
                "          }\n" +
                "          coverImage {\n" +
                "            large\n" +
                "          }\n" +
                "        }\n" +
                "        progress\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return connectAnilist(graph);
    }

    public static String animeDetails(String malId) {
        String graph = "query{\n" +
                "  Media(idMal:" + malId + "){\n " +
                "    title{english}\n" +
                "    coverImage{large}\n" +
                "    description\n" +
                "    bannerImage\n" +
                "    relations {\n" +
                "    \n" +
                "      edges{\n" +
                "        node{\n" +
                "          idMal\n" +
                "          title {\n" +
                "            english\n" +
                "          }\n" +
                "        }\n" +
                "        relationType\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return connectAnilist(graph);
    }

    public static String searchAnime(String animeName) {
        String graph = "query{\n" +
                "  Page(perPage: 21) {\n" +
                "    media(search: \"" + animeName + "\", type: ANIME) {\n" +
                "      idMal\n" +
                "      title {\n" +
                "        native\n" +
                "      }\n" +
                "      coverImage{\n" +
                "        large\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return connectAnilist(graph);
    }

    public static String saveAnimeProgress(String malId, String progress) {
        String graph = "mutation {\n" +
                "  SaveMediaListEntry(\n" +
                "    mediaId: " + malId + "\n" +
                "    status: " + Constants.animeStatus + "\n" +
                "    progress: " + progress + " \n" +
                "  ) {\n" +
                "    id\n" +
                "    status\n" +
                "    progress\n" +
                "  }\n" +
                "}";
        return connectAnilist(graph);
    }

    public static String deleteAnime(String mediaListEntryId) {
        String graph = "mutation {\n" +
                "  DeleteMediaListEntry(\n" +
                "    id: " + mediaListEntryId + "\n" +
                "  ) {\n" +
                "    deleted\n" +
                "  }\n" +
                "}";
        return connectAnilist(graph);
    }

}
