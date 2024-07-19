package com.ghostreborn.akira.model;

public class Anime {

    private final String animeThumbnail;
    private final String animeName;

    public Anime(String animeName, String animeThumbnail){
        this.animeName = animeName;
        this.animeThumbnail = animeThumbnail;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getAnimeThumbnail() {
        return animeThumbnail;
    }
}
