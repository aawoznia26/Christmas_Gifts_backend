package com.draw.gifts.service;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.DrawingStatus;
import com.draw.gifts.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DrawingService {

    private UserService userService;
    private Random generator = new Random();

    public DrawingService(UserService userService) {
        this.userService = userService;
    }

    public Drawing openDrawing(){
        Drawing drawing = new Drawing(DrawingStatus.OPENED);
        return drawing;

    }

    public Drawing closeDrawing(Drawing drawing){
        drawing.setDrawingStatus(DrawingStatus.FINISHED);
        return drawing;

    }

    public User choseDrawingUser(Drawing drawing){

        List<User> usersToChoseFrom = drawing.getUsersList().stream()
                .filter(u -> u.isIfHasDrawn() == false)
                .collect(Collectors.toList());
        int randomNumber = generator.nextInt(usersToChoseFrom.size());
        User chosenUser = usersToChoseFrom.get(randomNumber);
        userService.markAsExcludedFromDrawing(chosenUser);
        userService.markAsAlreadyHasDrawn(chosenUser);

        System.out.println("Teraz losuje " + chosenUser.getName());
        return chosenUser;
    }

    public User draw(Drawing drawing, User drawingUser){

        List<User> usersToChoseFrom = drawing.getUsersList().stream()
                .filter(u -> u.isExcludedFromDrawing() == false)
                .filter(u -> u.isIfWasDrawn() == false)
                .collect(Collectors.toList());
        int randomNumber = generator.nextInt(usersToChoseFrom.size());
        User drawnUser = usersToChoseFrom.get(randomNumber);

        userService.markAsAlreadyWasDrawn(drawnUser);
        userService.unmarkAsExcludedFromDrawing(drawingUser);
        System.out.println(drawingUser.getName() + " wylosował/a " + drawnUser.getName());

        return drawnUser;

    }
}
