package com.draw.gifts.service;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.DrawingStatus;
import com.draw.gifts.domain.User;
import com.draw.gifts.repository.DrawingRepository;
import com.draw.gifts.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DrawingService {


    @Autowired
    private DrawingRepository drawingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public Drawing openDrawing(){
        LOGGER.info("Preparation to creating new drawing");
        Drawing savedDrawing = drawingRepository.save(new Drawing(DrawingStatus.OPENED));
        LOGGER.info("Drawing created successfully");
        return savedDrawing;

    }

    public Drawing closeDrawing(Long drawingId){
        LOGGER.info("Preparation to closing the drawing");
        Drawing foundDrawing = drawingRepository.getOne(drawingId);
        Drawing savedDrawing = null;
        if(foundDrawing != null){
            LOGGER.info("Drawing has been found");
            foundDrawing.setDrawingStatus(DrawingStatus.FINISHED);
            savedDrawing = drawingRepository.save(foundDrawing);
            LOGGER.info("Drawing closed successfully");
        } else {
            LOGGER.error("Drawing could not be found");
        }
        return savedDrawing;

    }

    public User choseDrawingUser(Long drawingId){

        LOGGER.info("Preparation to choosing drawing user");

        List<User> usersToChoseFrom = userRepository.findAllByIfHasDrawnIsFalseAndDrawing_Id(drawingId);
        if (usersToChoseFrom.size()>0) {
            Random generator = new Random();
            int randomNumber = generator.nextInt(usersToChoseFrom.size());
            User chosenUser = usersToChoseFrom.get(randomNumber);
            userService.markAsHasDrawn(chosenUser);
            LOGGER.info("User chosen");

            return usersToChoseFrom.get(randomNumber);
        } else {
            closeDrawing(drawingId);
            User chosenUser = new User();
            LOGGER.info("No more users to chose");
            return chosenUser;
        }
    }

    public User draw(Long drawingId){
        LOGGER.info("Preparation to drawing");

        List<User> usersToChoseFrom = userRepository.findAllByIfWasDrawnIsFalseAndExcludedTemporarilyIsFalseAndDrawing_Id(drawingId);
        if (usersToChoseFrom.size()>0) {
            Random generator = new Random();
            int randomNumber = generator.nextInt(usersToChoseFrom.size());
            User drawnUser = usersToChoseFrom.get(randomNumber);
            User drawingUser = userRepository.findUserByExcludedTemporarilyIsTrueAndDrawing_Id(drawingId);
            userService.saveDrawingResult(drawingUser, drawnUser);
            userService.markAsWasDrawn(drawnUser);
            userService.switchTemporarilyExclusion(drawingUser);
            LOGGER.info("User chosen");
            return drawnUser;
        } else {
            User drawnUser = new User();
            LOGGER.info("No more users to draw");
            return drawnUser;
        }


    }
}
