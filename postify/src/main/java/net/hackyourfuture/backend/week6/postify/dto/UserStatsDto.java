package net.hackyourfuture.backend.week6.postify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserStatsDto {
    private Integer userId;
    private String userName;
    private String userCountry;
    private Long totalStreams;
    private Long uniqueTracksStreamed;
    private Long uniqueArtistsStreamed;
    private Long totalListeningTimeSeconds;
    private String favoriteGenre;
}