package com.draw.gifts.domain;

import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter
public class Drawing {
    private List<User> usersList;

    private DrawingStatus drawingStatus;


    public Drawing( DrawingStatus drawingStatus) {
        this.usersList = new ArrayList<>();
        this.drawingStatus = drawingStatus;
    }

    public void setDrawingStatus(DrawingStatus drawingStatus) {
        this.drawingStatus = drawingStatus;
    }
}
