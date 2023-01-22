package ru.fllcker.whynotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.whynotes.models.Note;
import ru.fllcker.whynotes.models.User;

import java.util.List;

@Repository
public interface INotesRepository extends JpaRepository<Note, Integer> {
    List<Note> findNotesByOwner(User owner);
}
