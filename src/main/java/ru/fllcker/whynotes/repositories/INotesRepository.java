package ru.fllcker.whynotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.whynotes.models.Note;

@Repository
public interface INotesRepository extends JpaRepository<Note, Integer> {
}
