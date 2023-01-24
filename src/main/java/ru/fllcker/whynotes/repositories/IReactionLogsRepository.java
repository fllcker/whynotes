package ru.fllcker.whynotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.whynotes.models.ReactionLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReactionLogsRepository extends JpaRepository<ReactionLog, Integer> {
    Optional<ReactionLog> findByText(String text);

    List<ReactionLog> findAllByOrderByUsesDesc();
}
