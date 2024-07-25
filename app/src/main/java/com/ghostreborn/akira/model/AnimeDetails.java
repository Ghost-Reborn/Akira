package com.ghostreborn.akira.model;

public class AnimeDetails {

    private final String animeName;
    private final String animeImage;
    private final String animeBanner;
    private final String animeDescription;
    private final String animePrequel;
    private final String animeSequel;

    public AnimeDetails(String animeName,String animeImage,String animeDescription,String animeBanner, String animePrequel, String animeSequel) {
        this.animeName = animeName;
        this.animeImage = animeImage;
        this.animeDescription = animeDescription;
        this.animeBanner = animeBanner;
        this.animePrequel = animePrequel;
        this.animeSequel = animeSequel;
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

}
