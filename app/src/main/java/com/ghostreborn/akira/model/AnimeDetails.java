package com.ghostreborn.akira.model;

import java.util.ArrayList;

public class AnimeDetails {

    private final String animeName;
    private final String animeImage;
    private final String animeBanner;
    private final String animeDescription;
    private final String animePrequel;
    private final String animeSequel;
    private final ArrayList<String> episodes;

    public AnimeDetails(String animeName,String animeImage,String animeDescription,String animeBanner, String animePrequel, String animeSequel, ArrayList<String> episodes) {
        this.animeName = animeName;
        this.animeImage = animeImage;
        this.animeDescription = animeDescription;
        this.animeBanner = animeBanner;
        this.animePrequel = animePrequel;
        this.animeSequel = animeSequel;
        this.episodes = episodes;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getAnimeImage() {
        return animeImage;
    }

    public String getAnimeBanner() {
        return animeBanner;
    }

    public String getAnimeDescription() {
        return animeDescription;
    }

    public String getAnimePrequel() {
        return animePrequel;
    }

    public String getAnimeSequel() {
        return animeSequel;
    }

    public ArrayList<String> getEpisodes() {
        return episodes;
    }
}
