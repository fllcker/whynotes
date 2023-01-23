package ru.fllcker.whynotes.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "interval_time")
    private Long intervalTime;

    @NonNull
    @Column(name = "next_time")
    private Long nextTime;

    @Column(name = "is_expired")
    private boolean expired = false;

    @Column(name = "is_activated")
    private boolean activated = true;

    @ManyToOne
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    @JsonManagedReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Note note;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User owner;
}
