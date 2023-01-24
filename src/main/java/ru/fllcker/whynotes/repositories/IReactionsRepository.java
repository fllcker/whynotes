package ru.fllcker.whynotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.whynotes.models.Note;
import ru.fllcker.whynotes.models.Reaction;

import java.util.List;

@Repository
public interface IReactionsRepository extends JpaRepository<Reaction, Integer> {
    List<Reaction> findReactionsByNote(Note note);
}
