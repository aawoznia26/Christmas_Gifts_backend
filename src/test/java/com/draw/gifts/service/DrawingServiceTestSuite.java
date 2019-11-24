package com.draw.gifts.service;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.DrawingStatus;
import com.draw.gifts.domain.User;
import com.draw.gifts.repository.DrawingRepository;
import com.draw.gifts.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
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
        Assert.assertEquals(++drawingListSizeBefore, drawingRepository.findAll().size());
        Assert.assertEquals(DrawingStatus.OPENED, drawingRepository.getOne(createdDrawing.getId()).getDrawingStatus());

    }

    @Test
    public void shouldCloseDrawing(){

        //Given
        Drawing createdDrawing = drawingService.openDrawing();

        //When
        drawingService.closeDrawing(createdDrawing.getId());

        //Then
        Assert.assertEquals(DrawingStatus.FINISHED, drawingRepository.getOne(createdDrawing.getId()).getDrawingStatus());

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
        Assert.assertEquals(true, userRepository.getOne(chosenUser.getId()).isExcludedFromDrawing());

    }

    @Test
    public void drawTest(){

        //Given
        Drawing createdDrawing = drawingService.openDrawing();
        User user1 = userService.createUser("Ela", createdDrawing);
        User user2 = userService.createUser("Hela", createdDrawing);
        drawingRepository.save(createdDrawing);

        //When
        User drawnUser = drawingService.draw(createdDrawing);

        //Then
        Assert.assertEquals(true, userRepository.getOne(drawnUser.getId()).isExcludedFromDrawing());

    }


}
