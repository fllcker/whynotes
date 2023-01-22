package ru.fllcker.whynotes.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User owner;

    @OneToMany(mappedBy = "note")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Reminder> reminders;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    private String description;
}
