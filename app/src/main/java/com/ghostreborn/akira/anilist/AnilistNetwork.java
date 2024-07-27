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
                "        media{ \n" +
                "          idMal\n" +
                "          title {\n" +
                "            english\n" +
                "          }\n" +
                "          coverImage {\n" +
                "            medium\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return connectAnilist(graph);
    }

}
