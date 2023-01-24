package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fllcker.whynotes.models.ReactionLog;
import ru.fllcker.whynotes.repositories.IReactionLogsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionLogsService {
    private final IReactionLogsRepository reactionLogsRepository;

    public void newReactionLog(String text) {
        Optional<ReactionLog> reactionLog = reactionLogsRepository.findByText(text);

        ReactionLog rLog = reactionLog.orElse(new ReactionLog(text));
        rLog.setUses(rLog.getUses() + 1L);
        reactionLogsRepository.save(rLog);
    }

    public List<ReactionLog> getMostPopular() {
        return reactionLogsRepository.findAllByOrderByUsesDesc();
    }
}
