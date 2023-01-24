package ru.fllcker.whynotes.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import ru.fllcker.whynotes.utils.TimeUtils;

@Entity
@Table(name = "reactions")
@Getter
@Setter
@NoArgsConstructor
public class Reaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    @JsonManagedReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Note note;

    public Reaction(@NonNull String text) {
        this.text = text;
        this.createdAt = TimeUtils.nowLong();
    }
}
