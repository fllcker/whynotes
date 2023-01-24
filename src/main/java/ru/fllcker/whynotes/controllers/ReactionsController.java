package ru.fllcker.whynotes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.NewReactionDto;
import ru.fllcker.whynotes.models.Reaction;
import ru.fllcker.whynotes.security.JwtAuthentication;
import ru.fllcker.whynotes.services.AuthService;
import ru.fllcker.whynotes.services.ReactionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reactions")
public class ReactionsController {
    private final AuthService authService;
    private final ReactionsService reactionsService;

    @PostMapping("new")
    public ResponseEntity<Reaction> createReaction(@RequestBody @Valid NewReactionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        JwtAuthentication authentication = authService.getAuthInfo();
        Reaction reaction = reactionsService.newReaction(authentication.getEmail(), dto);

        return ResponseEntity.ok(reaction);
    }

    @GetMapping("note_id/{noteId}")
    public ResponseEntity<List<Reaction>> findReactionsByNoteId(@PathVariable Integer noteId) {
        List<Reaction> reactionsByNoteId = reactionsService.findReactionsByNoteId(noteId);
        return ResponseEntity.ok(reactionsByNoteId);
    }
}
