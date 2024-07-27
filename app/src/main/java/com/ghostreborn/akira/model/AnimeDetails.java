package com.ghostreborn.akira.model;

public class AnimeDetails {

    private final String animeName;
    private final String animeImage;
    private final String animeDescription;

    public AnimeDetails(String animeName,String animeImage,String animeDescription) {
        this.animeName = animeName;
        this.animeImage = animeImage;
        this.animeDescription = animeDescription;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getAnimeImage() {
        return animeImage;
    }

    public String getAnimeDescription() {
        return animeDescription;
    }

}
