package net.hackyourfuture.backend.week6.postify.controller;

import lombok.RequiredArgsConstructor;
import net.hackyourfuture.backend.week6.postify.dto.UserStatsDto;
import net.hackyourfuture.backend.week6.postify.service.UserStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserStatsController {

    private final UserStatsService userStatsService;

    @GetMapping("/users/{id}/stats")
    public UserStatsDto getUserStats(@PathVariable("id") Integer id) {
        return userStatsService.getUserStats(id);
    }
}