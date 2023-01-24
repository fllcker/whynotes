package ru.fllcker.whynotes.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reactionlogs")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ReactionLog {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "text")
    private String text;

    @Column(name = "uses")
    private Long uses = 0L;
}
