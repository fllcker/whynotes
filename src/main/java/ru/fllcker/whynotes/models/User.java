package ru.fllcker.whynotes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Note> notes;
}
