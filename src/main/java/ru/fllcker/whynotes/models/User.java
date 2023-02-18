package ru.fllcker.whynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

/**
 * @author github.com/fllcker
 */
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "password")
    private String passwordHashed;

    @NonNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Token> tokens;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Note> notes;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Reminder> reminders;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Reaction> reactions;
}
