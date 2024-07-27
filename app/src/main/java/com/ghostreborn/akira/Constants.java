package com.ghostreborn.akira;

import com.ghostreborn.akira.model.Anime;
import com.ghostreborn.akira.model.AnimeDetails;
import com.ghostreborn.akira.model.Episode;

import java.util.ArrayList;

public class Constants {

    public static final String sharedPreference = "Akira";
    public static final String akiraLoggedIn = "AkiraLoggedIn";
    public static final String akiraToken = "AkiraToken";
    public static final String akiraUserId = "AkiraUserId";

    public static String userID = "";
    public static String userToken = "";

    public static String episodeUrl;
    public static String animeID;
    public static String allAnimeID;
    public static String animeProgress;
    public static AnimeDetails animeDetails;
    public static ArrayList<Anime> animes;
    public static ArrayList<Episode> parsedEpisodes;
    public static ArrayList<ArrayList<String>> groupedEpisodes;

}
