package com.ghostreborn.akira.model;

public class Episode {

    private final String episodeNumber;
    private final String episodeTitle;
    private final String episodeThumbnail;

    public Episode(
            String episodeNumber,
            String episodeTitle,
            String episodeThumbnail
    ) {
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.episodeThumbnail = episodeThumbnail;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeThumbnail() {
        return episodeThumbnail;
    }
}
