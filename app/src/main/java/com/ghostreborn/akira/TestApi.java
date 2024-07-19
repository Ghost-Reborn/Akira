package com.ghostreborn.akira;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestApi {

    public static String connectAllAnime(String url) {
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

    public static String testApi() {
        String url = "https://api.allanime.day/api?variables={%22search%22:{%22sortBy%22:%22Recent%22},%22limit%22:26,%22page%22:1,%22translationType%22:%22sub%22,%22countryOrigin%22:%22ALL%22}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%2206327bc10dd682e1ee7e07b6db9c16e9ad2fd56c1b769e47513128cd5c9fc77a%22}}";
        // Stuffs
//        url = "https://api.allanime.day/api?variables={%22staffAniListIds%22:[95039,95160,104534,109757,132225,154552,113867,182632],%22pageNo%22:1,%22offset%22:0,%22limit%22:500}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%227add54a3e7e6eef13f448af518d732d2882bb70fcd1c268ef7bcac37a879ed9e%22}}";
        //Character
//        url = "https://api.allanime.day/api?variables={%22aniListId%22:2752}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%225a01aa78b9377b86735e0867cd9be5d5d3f4c2545de172074462bfb307b8d959%22}}";
        // QueryPageForIndependentDoc
//        url = "https://api.allanime.day/api?variables={%22_id%22:%2260b2f0a57783fd9cff72316b%22,%22type%22:%22page%22,%22otherFields%22:{%22showId%22:%22ReooPAxPMsHM4KPMY%22,%22isManga%22:false}}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%229a18d540242f0e1e4d9b3169e97bad6eccc536b73c82aaefa114384051099302%22}}";
        // Popular
//        url = "https://api.allanime.day/api?variables={%22type%22:%22anime%22,%22size%22:20,%22dateRange%22:1,%22page%22:1,%22allowAdult%22:false,%22allowUnknown%22:false}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%221fc9651b0d4c3b9dfd2fa6e1d50b8f4d11ce37f988c23b8ee20f82159f7c1147%22}}";
        // Random Recommendation
//        url = "https://api.allanime.day/api?variables={%22format%22:%22anime%22,%22allowAdult%22:false}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%22c3fd993b7ec3ce68c08afc650f42f57c67bcb91c0e6548c9491066ce56262eae%22}}";
        // Collection
//        url = "https://api.allanime.day/api?variables={%22search%22:{%22formats%22:[%22anime%22,%22manga%22,%22music%22],%22sortBy%22:%22views%22}}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%225feb4bb6c8da66230c34790d58e0a58208196b68eaa7f9d8106d8c25041cfe6e%22}}";
        // Tags
//        url = "https://api.allanime.day/api?variables={%22search%22:{%22format%22:%22anime%22},%22page%22:1,%22limit%22:26}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%22fbd24de3aec73d35332185b621beec15396aaf8e8ae00183ddac6c19fbf8adcf%22}}";
        // Audios
        // Example url https://aimgf.youtube-anime.com/audios/W5BohAEJNkGPfFYSA/ki-otsuki-dear-sunrise-by-one-piece-.mp3
//        url = "https://api.allanime.day/api?variables={%22search%22:{%22sortBy%22:%22Latest_Update%22},%22limit%22:26,%22page%22:1}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%229da0eb24f0fe0ce87075bad508bc2f6e5c10e51d8d46e82c0093f5302d8971df%22}}";
        return connectAllAnime(url);

    }

}
