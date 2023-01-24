package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.NewReactionDto;
import ru.fllcker.whynotes.models.Note;
import ru.fllcker.whynotes.models.Reaction;
import ru.fllcker.whynotes.models.User;
import ru.fllcker.whynotes.repositories.IReactionsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionsService {
    private final IReactionsRepository reactionsRepository;
    private final UsersService usersService;
    private final NotesService notesService;
    private final ReactionLogsService reactionLogsService;

    public Reaction newReaction(String accessEmail, NewReactionDto dto) {
        User user = usersService.findByEmail(accessEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        Note note = notesService.findById(dto.getNoteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found!"));

        Reaction reaction = new Reaction(dto.getText());
        reaction.setNote(note);
        reaction.setOwner(user);
        reactionsRepository.save(reaction);
        reactionLogsService.newReactionLog(dto.getText());

        return reaction;
    }

    public List<Reaction> findReactionsByNoteId(int noteId) {
        Note note = notesService.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found!"));

        return reactionsRepository.findReactionsByNote(note);
    }
}
