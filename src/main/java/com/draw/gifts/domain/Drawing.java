package com.draw.gifts.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Drawing {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    private List<User> usersList;

    private DrawingStatus drawingStatus;

    public Drawing(DrawingStatus drawingStatus) {
        this.drawingStatus = drawingStatus;
    }

    public void setDrawingStatus(DrawingStatus drawingStatus) {
        this.drawingStatus = drawingStatus;
    }
}
