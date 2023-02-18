package ru.fllcker.whynotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.whynotes.models.Token;

import java.util.Optional;

@Repository
public interface ITokensRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
