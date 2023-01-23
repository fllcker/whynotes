package ru.fllcker.whynotes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.NewReminderDto;
import ru.fllcker.whynotes.models.Reminder;
import ru.fllcker.whynotes.security.JwtAuthentication;
import ru.fllcker.whynotes.services.AuthService;
import ru.fllcker.whynotes.services.RemindersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reminders")
public class RemindersController {
    private final AuthService authService;
    private final RemindersService remindersService;

    @PostMapping("new")
    public ResponseEntity<Reminder> newReminder(@RequestBody @Valid NewReminderDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        JwtAuthentication authentication = authService.getAuthInfo();
        Reminder reminder = remindersService.newReminder(authentication.getEmail(), dto);

        return ResponseEntity.ok(reminder);
    }

    @GetMapping("expired")
    public ResponseEntity<List<Reminder>> findExpiredReminders() {
        JwtAuthentication authentication = authService.getAuthInfo();
        List<Reminder> reminders = remindersService.findExpiredReminders(authentication.getEmail());

        return ResponseEntity.ok(reminders);
    }
}
