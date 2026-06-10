package net.hackyourfuture.backend.week6.postify.service;

import net.hackyourfuture.backend.week6.postify.dto.ExternalLyricsResponse;
import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsDto;
import net.hackyourfuture.backend.week6.postify.repository.TrackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TrackLyricsService {

    private final TrackRepository trackRepository;
    private final RestClient restClient;

    public TrackLyricsService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
        // Sets up the RestClient base URL pointing directly to the external lyrics provider
        this.restClient = RestClient.builder()
                .baseUrl("https://api.lyrics.ovh/v1")
                .build();
    }

    public TrackLyricsDto getTrackLyrics(Long trackId) {
        // 1. Check the DB for the track. If the Optional box is empty, instantly throw a 404.
        TrackLyricsDto dto = trackRepository.findTrackAndArtistById(trackId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not found in database"));

        try {
            // 2. Make the external network request using RestClient
            ExternalLyricsResponse apiResponse = restClient.get()
                    .uri("/{artist}/{title}", dto.getArtistName(), dto.getTrackTitle())
                    .retrieve()
                    .body(ExternalLyricsResponse.class);

            // 3. If lyrics exist in the API payload, attach them to our response object
            if (apiResponse != null && apiResponse.lyrics() != null) {
                dto.setLyrics(apiResponse.lyrics());
                return dto;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lyrics not found for this track in the external API");
            }

        } catch (HttpClientErrorException.NotFound e) {
            // Catches an external 404 error and converts it into a clear, helpful message for the user
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The lyrics API does not have any lyrics available for this song.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "External lyrics service communication error");
        }
    }
}