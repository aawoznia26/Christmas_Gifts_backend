package com.draw.gifts.dto;


import com.draw.gifts.domain.Drawing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private boolean excludedFromDrawing;

    private Drawing drawing;

    public UserDto(String name) {
        this.name = name;
    }
}
