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
        savedUser = userRepository.save(new User(name, false, false, false, drawing));
        LOGGER.info("User created successfully");
        return savedUser;

    }

    public User markAsHasDrawn(User user){
        LOGGER.info("Preparation to mark as has drawn  " + user);
        User savedUser = null;
        User userToMark = userRepository.getOne(user.getId());
        if(userToMark != null){
            LOGGER.info("User has been found");

            userToMark.setIfHasDrawn(true);
            userToMark.setExcludedTemporarily(true);
            savedUser = userRepository.save(userToMark);
            LOGGER.info("User marked successfully");
        } else {
            LOGGER.error("User not found. Data change impossible");
        }

        return savedUser;

    }

    public User markAsWasDrawn(User user){
        LOGGER.info("Preparation to mark user as was drawn  " + user);
        User savedUser = null;
        User userToMark = userRepository.getOne(user.getId());
        if(userToMark != null){
            LOGGER.info("User has been found");

            userToMark.setIfWasDrawn(true);
            savedUser = userRepository.save(userToMark);
            LOGGER.info("User marked successfully");
        } else {
            LOGGER.error("User not found. Data change impossible");
        }

        return savedUser;

    }

    public User switchTemporarilyExclusion(User user){
        LOGGER.info("Preparation to switch temporarily exclusion from drawing " + user);
        User savedUser = null;
        User userToMark = userRepository.getOne(user.getId());
        if(userToMark != null){
            LOGGER.info("User has been found");

            userToMark.setIfHasDrawn(!userToMark.isExcludedTemporarily());
            savedUser = userRepository.save(userToMark);
            LOGGER.info("User marked successfully");
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
