package net.hackyourfuture.backend.week6.postify.repository;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrackRepository {

    private final JdbcTemplate jdbcTemplate;

    public TrackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<TrackLyricsDto> findTrackAndArtistById(Long trackId) {
        String sql = """
            SELECT t.track_id, t.track_title, a.artist_name 
            FROM tracks t
            JOIN albums al ON t.album_id = al.album_id
            JOIN artists a ON al.artist_id = a.artist_id
            WHERE t.track_id = ?
        """;

        try {
            // If found, wrap the object inside a safe Optional box
            TrackLyricsDto dto = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                    new TrackLyricsDto(
                            rs.getLong("track_id"),
                            rs.getString("track_title"),
                            rs.getString("artist_name"),
                            null // Lyrics are left empty for now
                    ), trackId);
            return Optional.ofNullable(dto);

        } catch (EmptyResultDataAccessException e) {
            // If the database finds nothing, return a safe empty box
            return Optional.empty();
        }
    }
}