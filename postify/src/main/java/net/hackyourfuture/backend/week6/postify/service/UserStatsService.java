package net.hackyourfuture.backend.week6.postify.service;

import lombok.RequiredArgsConstructor;
import net.hackyourfuture.backend.week6.postify.dto.UserStatsDto;
import net.hackyourfuture.backend.week6.postify.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserStatsService {

    private final UserRepository userRepository;

    public UserStatsDto getUserStats(Integer userId) {
        return userRepository.getUserStats(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}