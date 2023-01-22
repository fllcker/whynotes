package ru.fllcker.whynotes.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewNoteDto {
    @Size(max = 64, message = "Title should be less than 64 letters")
    private String title;
    private String description;
}
