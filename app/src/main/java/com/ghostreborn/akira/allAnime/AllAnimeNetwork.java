package com.ghostreborn.akira.allAnime;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnimeNetwork {

    public static String connectAllAnime(String variables, String queryTypes, String query) {
        OkHttpClient client = new OkHttpClient();
        String queryUrl = "https://api.allanime.day/api" + "?variables={" + variables + "}&query=" + "query(" + queryTypes + "){" + query + "}";
        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0").build();
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

    public static String queryPopular() {
        String variables = "\"size\":30,\"type\":\"anime\",\"dateRange\":1,\"page\":1,\"allowAdult\":true,\"allowUnknown\":true";
        String queryTypes = "$size:Int!,$type:VaildPopularTypeEnumType!,$dateRange:Int!,$page:Int!,$allowAdult:Boolean!,$allowUnknown:Boolean!";
        String query = "queryPopular(type:$type,size:$size,dateRange:$dateRange,page:$page,allowAdult:$allowAdult,allowUnknown:$allowUnknown){total,recommendations{anyCard{_id,name,englishName,thumbnail}}}";
        return connectAllAnime(variables, queryTypes, query);
    }

    public static String searchAnime(String anime) {
        String variables = "\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"";
        String queryTypes = "$search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType";
        String query = "shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{_id,name,englishName,thumbnail}}";
        return connectAllAnime(variables, queryTypes, query);
    }

    public static String animeDetails(String id) {
        String variables = "\"showId\":\"" + id + "\"";
        String queryTypes = "$showId:String!";
        String query = "show(_id:$showId){name,englishName,thumbnail,description,banner,relatedShows,availableEpisodesDetail}";
        return connectAllAnime(variables, queryTypes, query);
    }

    public static String episodeDetails(String id, String episode) {
        String variables = "\"showId\":\"" + id + "\",\"episode\":\"" + episode + "\",\"translationType\":\"sub\"";
        String queryTypes = "$showId:String!,$episode:String!,$translationType:VaildTranslationTypeEnumType!";
        String query = "episode(showId:$showId,episodeString:$episode,translationType:$translationType){" +
                "episodeString," +
                "episodeInfo{notes,thumbnails}" +
                "}";
        return connectAllAnime(variables, queryTypes, query);
    }

    public static String episodeUrls(String id, String episode) {
        String variables = "\"showId\":\"" + id + "\",\"episode\":\"" + episode + "\",\"translationType\":\"sub\"";
        String queryTypes = "$showId:String!,$episode:String!,$translationType:VaildTranslationTypeEnumType!";
        String query = "episode(showId:$showId,episodeString:$episode,translationType:$translationType){" +
                "sourceUrls"+
                "}";
        return connectAllAnime(variables, queryTypes, query);
    }

}
