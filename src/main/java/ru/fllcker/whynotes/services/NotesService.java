package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.NewNoteDto;
import ru.fllcker.whynotes.models.Note;
import ru.fllcker.whynotes.models.User;
import ru.fllcker.whynotes.repositories.INotesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotesService {
    private final INotesRepository notesRepository;
    private final UsersService usersService;

    public List<Note> findByUser(String accessEmail) {
        User owner = usersService.findByEmail(accessEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        return notesRepository.findNotesByOwner(owner);
    }

    public Note create(String accessEmail, NewNoteDto dto) {
        User owner = usersService.findByEmail(accessEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        Note note = new Note(dto.getTitle(), dto.getDescription());

        note.setOwner(owner);
        notesRepository.save(note);
        return note;
    }
}
