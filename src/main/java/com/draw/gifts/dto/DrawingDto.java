package com.draw.gifts.dto;

import com.draw.gifts.domain.DrawingStatus;
import com.draw.gifts.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrawingDto {

    private Long id;

    private List<User> usersList;

    private DrawingStatus drawingStatus;
}
