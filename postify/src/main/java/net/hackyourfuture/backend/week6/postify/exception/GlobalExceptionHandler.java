package net.hackyourfuture.backend.week6.postify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Catches any 404 errors thrown by RestClient when the external lyrics API comes up empty
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Map<String, String>> handleExternalLyricsNotFound(HttpClientErrorException.NotFound ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "The lyrics API does not have any lyrics available for this song."));
    }
}