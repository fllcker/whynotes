package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.NewReminderDto;
import ru.fllcker.whynotes.models.Note;
import ru.fllcker.whynotes.models.Reminder;
import ru.fllcker.whynotes.models.User;
import ru.fllcker.whynotes.repositories.IRemindersRepository;
import ru.fllcker.whynotes.utils.TimeUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RemindersService {
    private final IRemindersRepository remindersRepository;
    private final UsersService usersService;
    private final NotesService notesService;

    public Reminder newReminder(String accessEmail, NewReminderDto dto) {
        Reminder reminder = new Reminder(dto.getStartTime(), dto.getIntervalTime());

        Note note = notesService.findById(dto.getNoteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found!"));
        User user = usersService.findByEmail(accessEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        reminder.setNextTime(TimeUtils.getNextTime(dto.getStartTime(), dto.getIntervalTime()));
        reminder.setOwner(user);
        reminder.setNote(note);

        remindersRepository.save(reminder);
        return reminder;
    }

    public List<Reminder> findExpiredReminders(String accessEmail) {
        User user = usersService.findByEmail(accessEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        List<Reminder> reminders = remindersRepository
                .findRemindersByNextTimeLessThanAndActivatedAndOwnerId(TimeUtils.nowLong(), true, user.getId());

        reminders.forEach(reminder -> reminder.setExpired(true));
        remindersRepository.saveAll(reminders);

        return reminders;
    }

}
