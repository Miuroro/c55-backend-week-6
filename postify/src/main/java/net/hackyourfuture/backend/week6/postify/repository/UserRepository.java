package net.hackyourfuture.backend.week6.postify.repository;

import lombok.RequiredArgsConstructor;
import net.hackyourfuture.backend.week6.postify.dto.UserStatsDto;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DataSource dataSource;

    public Optional<UserStatsDto> getUserStats(Integer userId) {
        String sql = """
            SELECT 
                u.user_id,
                u.user_name,
                u.user_country,
                COUNT(s.stream_id) AS total_streams,
                COUNT(DISTINCT s.track_id) AS unique_tracks,
                COUNT(DISTINCT al.artist_id) AS unique_artists,
                COALESCE(SUM(t.track_duration_s), 0) AS total_time,
                (
                    SELECT t2.genre 
                    FROM streams s2
                    JOIN tracks t2 ON s2.track_id = t2.track_id
                    WHERE s2.user_id = u.user_id
                    GROUP BY t2.genre
                    ORDER BY COUNT(s2.stream_id) DESC, t2.genre ASC
                    LIMIT 1
                ) AS favorite_genre
            FROM users u
            LEFT JOIN streams s ON u.user_id = s.user_id
            LEFT JOIN tracks t ON s.track_id = t.track_id
            LEFT JOIN albums al ON t.album_id = al.album_id
            WHERE u.user_id = ?
            GROUP BY u.user_id, u.user_name, u.user_country;
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getString("user_name") == null) {
                        return Optional.empty();
                    }

                    UserStatsDto dto = new UserStatsDto(
                            resultSet.getInt("user_id"),
                            resultSet.getString("user_name"),
                            resultSet.getString("user_country"),
                            resultSet.getLong("total_streams"),
                            resultSet.getLong("unique_tracks"),
                            resultSet.getLong("unique_artists"),
                            resultSet.getLong("total_time"),
                            resultSet.getString("favorite_genre")
                    );
                    return Optional.of(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred while fetching user statistics", e);
        }

        return Optional.empty();
    }
}