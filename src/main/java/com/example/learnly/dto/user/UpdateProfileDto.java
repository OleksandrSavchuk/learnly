package com.example.learnly.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProfileDto(
        @Size(min = 3, max = 255, message = "First name must be between {min} and {max} characters")
        @Pattern(
                regexp = "^[A-Za-zА-Яа-яІіЇїЄєҐґ]+(?:[-' ]?[A-Za-zА-Яа-яІіЇїЄєҐґ]+)*$",
                message = "First name may contain one or more words with letters, spaces, hyphens, or apostrophes"
        )
        String firstName,

        @Size(min = 3, max = 255, message = "Last name must be between {min} and {max} characters")
        @Pattern(
                regexp = "^[A-Za-zА-Яа-яІіЇїЄєҐґ]+(?:[-' ]?[A-Za-zА-Яа-яІіЇїЄєҐґ]+)*$",
                message = "Last name may contain one or more words with letters, spaces, hyphens, or apostrophes"
        )
        String lastName
) {
}
