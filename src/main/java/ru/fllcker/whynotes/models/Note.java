package ru.fllcker.whynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author github.com/fllcker
 */
@Entity
@Table(name = "notes")
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    private User owner;

    @OneToMany(mappedBy = "note")
    @JsonBackReference
    private List<Reminder> reminders;

    @OneToMany(mappedBy = "note")
    @JsonBackReference
    private List<Reaction> reactions;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    private String description;
}
