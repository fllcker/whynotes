package ru.fllcker.whynotes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fllcker.whynotes.models.ReactionLog;
import ru.fllcker.whynotes.services.ReactionLogsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reaction-logs")
public class ReactionLogsController {
    private final ReactionLogsService reactionLogsService;

    @GetMapping
    public ResponseEntity<List<ReactionLog>> getMostPopular() {
        List<ReactionLog> mostPopular = reactionLogsService.getMostPopular();
        return ResponseEntity.ok(mostPopular);
    }
}
