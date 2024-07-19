package com.ghostreborn.akira.model;

public class Anime {

    private final String animeID;
    private final String animeThumbnail;
    private final String animeName;

    public Anime(String animeID,String animeName, String animeThumbnail){
        this.animeID = animeID;
        this.animeName = animeName;
        this.animeThumbnail = animeThumbnail;
    }

    public String getAnimeID() {
        return animeID;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getAnimeThumbnail() {
        return animeThumbnail;
    }
}
