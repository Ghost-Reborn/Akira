package com.ghostreborn.akira.anilist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ghostreborn.akira.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnilistParser {

    public static void getToken(String code, Activity activity) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        String json = "{}";
        try {
            json = new JSONObject()
                    .put("grant_type", "authorization_code")
                    .put("client_id", "20149")
                    .put("client_secret", "taduUeXEUEZzKfQ5Av0VapX2aLhYgqSWEJgHt9uQ")
                    .put("redirect_uri", "wanpisu://ghostreborn.in")
                    .put("code", code)
                    .toString();
        } catch (JSONException e) {
            Log.e("TAG", "Error: ", e);
        }

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url("https://anilist.co/api/v2/oauth/token")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String accessToken = new JSONObject(response.body().string())
                    .getString("access_token");
            SharedPreferences preferences = activity.getSharedPreferences(Constants.sharedPreference, Context.MODE_PRIVATE);
            preferences.edit()
                    .putBoolean(Constants.akiraLoggedIn, true)
                    .putString(Constants.akiraToken, accessToken)
                    .apply();
            getUserNameAndId(accessToken,activity);
        } catch (IOException | JSONException e) {
            Log.e("TAG", "Error: ", e);
        }
    }

    private static void getUserNameAndId(String token,Activity activity) {
        OkHttpClient client = new OkHttpClient();
        String query = "{\n" +
                "  Viewer {\n" +
                "    id\n" +
                "    name\n" +
                "  }\n" +
                "}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("query", query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://graphql.anilist.co")
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseData = response.body().string();
            try {
                JSONObject jsonResponse = new JSONObject(responseData);
                JSONObject data = jsonResponse.getJSONObject("data");
                JSONObject viewer = data.getJSONObject("Viewer");
                String id = viewer.getString("id");
                SharedPreferences preferences = activity.getSharedPreferences(Constants.sharedPreference, Context.MODE_PRIVATE);
                preferences.edit()
                        .putString(Constants.akiraUserId, id)
                        .apply();
                Log.e("TAG", "User ID: " + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            Log.e("TAG", "Error: ", ex);
        }
    }

}
