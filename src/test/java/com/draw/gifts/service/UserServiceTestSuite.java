package com.draw.gifts.service;


import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.User;
import com.draw.gifts.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
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
        Assert.assertEquals(++usersNumber, userRepository.findAll().size());
        Assert.assertEquals("Karol", userRepository.getOne(id).getName());

    }

    @Test
    public void shouldMarkUserAsExcluded(){

        //Given
        String name = "Karol";
        Drawing drawing = drawingService.openDrawing();
        User savedUser = userService.createUser(name, drawing);
        Long id = savedUser.getId();

        //When
        userService.markAsExcludedFromDrawing(savedUser);

        //Then
        Assert.assertEquals(true, userRepository.getOne(id).isExcludedFromDrawing());

    }


    @Test
    public void shouldMarkUserAsIncluded() {

        //Given
        String name = "Karol";
        Drawing drawing = drawingService.openDrawing();
        User savedUser = userService.createUser(name, drawing);
        savedUser.setExcludedFromDrawing(true);
        User userAfterDataChange = userRepository.save(savedUser);
        Long id = savedUser.getId();

        //When
        userService.unmarkAsExcludedFromDrawing(savedUser);

        //Then
        Assert.assertEquals(false, userRepository.getOne(id).isExcludedFromDrawing());

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
        Assert.assertEquals(drawingUserSaved.getId(), userRepository.getOne(drawingUserSaved.getId()).getDrawingUser().getId());
        Assert.assertEquals(drawnUserSaved.getId(), userRepository.getOne(drawingUserSaved.getId()).getDrawnUser().getId());

    }

}
