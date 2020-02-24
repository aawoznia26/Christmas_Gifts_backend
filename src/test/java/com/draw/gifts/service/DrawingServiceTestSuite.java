package com.draw.gifts.service;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.DrawingStatus;
import com.draw.gifts.domain.User;
import com.draw.gifts.repository.DrawingRepository;
import com.draw.gifts.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class DrawingServiceTestSuite {

    @Autowired
    private UserService userService;

    @Autowired
    private DrawingService drawingService;

    @Autowired
    private DrawingRepository drawingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldOpenDrawing(){

        //Given
        int drawingListSizeBefore = drawingRepository.findAll().size();

        //When
       Drawing createdDrawing = drawingService.openDrawing();

        //Then
        assertEquals(++drawingListSizeBefore, drawingRepository.findAll().size());
        assertEquals(DrawingStatus.OPENED, drawingRepository.getOne(createdDrawing.getId()).getDrawingStatus());

    }

    @Test
    public void shouldCloseDrawing(){

        //Given
        Drawing createdDrawing = drawingService.openDrawing();

        //When
        drawingService.closeDrawing(createdDrawing.getId());

        //Then
        assertEquals(DrawingStatus.FINISHED, drawingRepository.getOne(createdDrawing.getId()).getDrawingStatus());

    }

    @Test
    public void choseDrawingUserTest(){

        //Given
        Drawing createdDrawing = drawingService.openDrawing();
        User user1 = userService.createUser("Ela", createdDrawing);
        User user2 = userService.createUser("Hela", createdDrawing);

        //When
        User chosenUser = drawingService.choseDrawingUser(createdDrawing.getId());

        //Then
        assertTrue( userRepository.getOne(chosenUser.getId()).isIfHasDrawn());

    }

    @Test
    public void drawTest(){

        //Given
        Drawing createdDrawing = drawingService.openDrawing();
        User user1 = userService.createUser("Ela", createdDrawing);
        User user2 = userService.createUser("Hela", createdDrawing);
        drawingRepository.save(createdDrawing);

        //When
        User drawnUser = drawingService.draw(createdDrawing.getId());

        //Then
        assertTrue( userRepository.getOne(drawnUser.getId()).isIfWasDrawn());

    }


}
