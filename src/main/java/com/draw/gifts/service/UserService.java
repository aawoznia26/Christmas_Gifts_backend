package com.draw.gifts.service;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.User;
import com.draw.gifts.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    public User createUser(String name, Drawing drawing){
        User savedUser;
        LOGGER.info("Preparation to create user " + name);
        savedUser = userRepository.save(new User(name, false, drawing));
        LOGGER.info("User created successfully");
        return savedUser;

    }

    public User markAsExcludedFromDrawing(User user){
        LOGGER.info("Preparation to exclusion from drawing of user  " + user);
        User savedUser = null;
        User userToMark = userRepository.getOne(user.getId());
        if(userToMark != null){
            LOGGER.info("User has been found");

            userToMark.setExcludedFromDrawing(true);
            savedUser = userRepository.save(userToMark);
            LOGGER.info("User marked as excluded from drawing successfully");
        } else {
            LOGGER.error("User not found. Data change impossible");
        }

        return savedUser;

    }

    public User unmarkAsExcludedFromDrawing(User user){
        LOGGER.info("Preparation to inclusion to drawing of user  " + user);
        User savedUser = null;
        User userToMark = userRepository.getOne(user.getId());
        if(userToMark != null){
            LOGGER.info("User has been found");

            userToMark.setExcludedFromDrawing(false);
            savedUser = userRepository.save(userToMark);
            LOGGER.info("User marked as included to drawing successfully");
        } else {
            LOGGER.error("User not found. Data change impossible");
        }

        return savedUser;

    }

    public void saveDrawingResult(User drawingUser, User drawnUser){
        LOGGER.info("Preparation to saving drawing results");

        drawingUser.setDrawingUser(drawingUser);
        drawingUser.setDrawnUser(drawnUser);
        userRepository.save(drawingUser);

        LOGGER.info("Drawing results saved");

    }

}
