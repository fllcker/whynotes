package ru.fllcker.whynotes.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "reminders")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Reminder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "start_time")
    private Long startTime;

    @NonNull
    @Column(name = "interval_time")
    private Long intervalTime;

    private boolean isActivated = true;

    @ManyToOne
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Note note;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User owner;
}
