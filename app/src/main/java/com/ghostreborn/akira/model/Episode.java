package com.ghostreborn.akira.model;

public class Episode {

    private final String episodeNumber;
    private final String episodeTitle;

    public Episode(
            String episodeNumber,
            String episodeTitle
    ) {
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

}
