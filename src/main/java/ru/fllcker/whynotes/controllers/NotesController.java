package ru.fllcker.whynotes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.NewNoteDto;
import ru.fllcker.whynotes.models.Note;
import ru.fllcker.whynotes.security.JwtAuthentication;
import ru.fllcker.whynotes.services.AuthService;
import ru.fllcker.whynotes.services.NotesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notes")
public class NotesController {
    private final AuthService authService;
    private final NotesService notesService;

    @GetMapping("by/user")
    public ResponseEntity<List<Note>> findUserNotes() {
        JwtAuthentication authentication = authService.getAuthInfo();
        List<Note> notes = notesService.findByUser(authentication.getEmail());
        return ResponseEntity.ok(notes);
    }

    @PostMapping("new")
    public ResponseEntity<Note> createNote(@RequestBody @Valid NewNoteDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        JwtAuthentication authentication = authService.getAuthInfo();
        Note note = notesService.create(authentication.getEmail(), dto);

        return ResponseEntity.ok(note);
    }
}
