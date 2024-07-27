package com.ghostreborn.akira.model;

public class Anime {

    private final String animeID;
    private final String mediaListEntryId;
    private final String animeThumbnail;
    private final String animeName;
    private final String progress;

    public Anime(String animeID, String mediaListEntryId, String animeName, String animeThumbnail, String progress) {
        this.animeID = animeID;
        this.mediaListEntryId = mediaListEntryId;
        this.animeName = animeName;
        this.animeThumbnail = animeThumbnail;
        this.progress = progress;
    }

    public String getAnimeID() {
        return animeID;
    }

    public String getMediaListEntryId() {
        return mediaListEntryId;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getAnimeThumbnail() {
        return animeThumbnail;
    }

    public String getProgress() {
        return progress;
    }
}
