package net.hackyourfuture.backend.week6.postify.controller;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsDto;
import net.hackyourfuture.backend.week6.postify.service.TrackLyricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {

    private final TrackLyricsService trackLyricsService;

    public TrackController(TrackLyricsService trackLyricsService) {
        this.trackLyricsService = trackLyricsService;
    }

    @GetMapping("/tracks/{id}/lyrics")
    public TrackLyricsDto getLyrics(@PathVariable("id") Long id) {
        return trackLyricsService.getTrackLyrics(id);
    }
}