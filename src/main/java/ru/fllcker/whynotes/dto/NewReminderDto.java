package ru.fllcker.whynotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.fllcker.whynotes.models.Reminder} entity
 */
@AllArgsConstructor
@Getter
public class NewReminderDto implements Serializable {
    private final Long startTime;
    private final Long intervalTime;
    private final boolean isActivated;

    // other
    private final int noteId;
}