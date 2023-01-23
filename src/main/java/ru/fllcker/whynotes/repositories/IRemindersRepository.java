package ru.fllcker.whynotes.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.whynotes.models.Reminder;

import java.util.List;

@Repository
public interface IRemindersRepository extends JpaRepository<Reminder, Integer> {
    List<Reminder> findRemindersByNextTimeLessThanAndActivatedAndOwnerId(@NonNull Long nextTime, boolean activated, int ownerId);
}
