package ru.fllcker.whynotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewReactionDto {
    private String text;

    // other
    private int noteId;
}
