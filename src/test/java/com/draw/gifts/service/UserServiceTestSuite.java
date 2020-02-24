package com.draw.gifts.service;


import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.User;
import com.draw.gifts.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserServiceTestSuite {

    @Autowired
    private UserService userService;

    @Autowired
    private DrawingService drawingService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldCreateUser(){

        //Given
        String name = "Karol";
        Drawing drawing = drawingService.openDrawing();

        //When
        int usersNumber = userRepository.findAll().size();
        Long id = userService.createUser(name, drawing).getId();

        //Then
        assertEquals(++usersNumber, userRepository.findAll().size());
        assertEquals("Karol", userRepository.getOne(id).getName());

    }

    @Test
    public void shouldMarkUserAsHasDrawn(){

        //Given
        String name = "Karol";
        Drawing drawing = drawingService.openDrawing();
        User savedUser = userService.createUser(name, drawing);
        Long id = savedUser.getId();

        //When
        userService.markAsHasDrawn(savedUser);

        //Then
        assertEquals(true, userRepository.getOne(id).isIfHasDrawn());

    }


    @Test
    public void shouldMarkUserAsWasDrawn() {

        //Given
        String name = "Karol";
        Drawing drawing = drawingService.openDrawing();
        User savedUser = userService.createUser(name, drawing);
        User userAfterDataChange = userRepository.save(savedUser);
        Long id = savedUser.getId();

        //When
        userService.markAsWasDrawn(savedUser);

        //Then
        assertEquals(false, userRepository.getOne(id).isIfWasDrawn());

    }
    @Test
    public void saveDrawingResultsTest() {

        //Given
        Drawing drawing = drawingService.openDrawing();
        User drawingUser = userService.createUser("Maja", drawing);
        User drawnUser = userService.createUser("Franek", drawing);

        User drawingUserSaved = userRepository.save(drawingUser);
        User drawnUserSaved = userRepository.save(drawnUser);

        //When
        userService.saveDrawingResult(drawingUserSaved, drawnUserSaved);

        //Then
        assertEquals(drawingUserSaved.getId(), userRepository.getOne(drawingUserSaved.getId()).getDrawingUser().getId());
        assertEquals(drawnUserSaved.getId(), userRepository.getOne(drawingUserSaved.getId()).getDrawnUser().getId());

    }

}
