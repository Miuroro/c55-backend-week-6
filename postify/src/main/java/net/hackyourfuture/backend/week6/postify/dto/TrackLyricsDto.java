package net.hackyourfuture.backend.week6.postify.dto;

public class TrackLyricsDto {
    private Long trackId;
    private String trackTitle;
    private String artistName;
    private String lyrics;

    public TrackLyricsDto(Long trackId, String trackTitle, String artistName, String lyrics) {
        this.trackId = trackId;
        this.trackTitle = trackTitle;
        this.artistName = artistName;
        this.lyrics = lyrics;
    }

    // Getters and Setters
    public Long getTrackId() { return trackId; }
    public void setTrackId(Long trackId) { this.trackId = trackId; }
    public String getTrackTitle() { return trackTitle; }
    public void setTrackTitle(String trackTitle) { this.trackTitle = trackTitle; }
    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }
    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
}