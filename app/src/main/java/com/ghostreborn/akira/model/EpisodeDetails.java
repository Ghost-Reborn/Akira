package com.ghostreborn.akira.model;

public class EpisodeDetails {

    private final String episodeTitle;
    private final String episodeThumbnail;

    public EpisodeDetails(String episodeTitle, String episodeThumbnail) {
        this.episodeTitle = episodeTitle;
        this.episodeThumbnail = episodeThumbnail;
    }

    public String getEpisodeThumbnail() {
        return episodeThumbnail;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }
}
