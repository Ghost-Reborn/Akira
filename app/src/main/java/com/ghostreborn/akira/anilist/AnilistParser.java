package com.ghostreborn.akira.anilist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ghostreborn.akira.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnilistParser {

    public static String connectAnilist() {
        OkHttpClient client = new OkHttpClient();
        String queryUrl = "https://anilist.co/api/v2/oauth/authorize?client_id=11820&response_type=token";
        Request request = new Request.Builder().url(queryUrl).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0").build();
        String rawJson = "{}";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rawJson;
    }

    public static String getToken(String code, Activity activity) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        String json = "{"
                + "\"grant_type\": \"authorization_code\","
                + "\"client_id\": \"20149\","
                + "\"client_secret\": \"taduUeXEUEZzKfQ5Av0VapX2aLhYgqSWEJgHt9uQ\","
                + "\"redirect_uri\": \"wanpisu://ghostreborn.in\","
                + "\"code\": \"" + code + "\""
                + "}";

        RequestBody body = RequestBody.create(json, JSON);

        String response = "";

        Request request = new Request.Builder()
                .url("https://anilist.co/api/v2/oauth/token")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject object = new JSONObject(responseBody);
                        String accessToken = object.getString("access_token");
                        SharedPreferences preferences = activity.getSharedPreferences(Constants.sharedPreference, Context.MODE_PRIVATE);
                        preferences.edit()
                                .putBoolean(Constants.akiraLoggedIn, true)
                                .putString(Constants.akiraToken, accessToken)
                                .apply();
                    } catch (JSONException e) {
                        Log.e("TAG", "Error parsing JSON: ", e);
                    }
                } else {
                    System.out.println("Error: " + response.code());
                }
            }
        });

        return "";
    }
}
