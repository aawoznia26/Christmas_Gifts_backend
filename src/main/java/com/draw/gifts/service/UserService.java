package com.draw.gifts.service;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {


    public User createUser(String name, Drawing drawing){
        User user = new User(name, false, false, false);
        drawing.getUsersList().add(user);
        return user;

    }

    public User markAsExcludedFromDrawing(User user){
        user.setExcludedFromDrawing(true);
        return user;
    }

    public User unmarkAsExcludedFromDrawing(User user){
        user.setExcludedFromDrawing(false);
        return user;
    }

    public User markAsAlreadyWasDrawn(User user){
        user.setIfWasDrawn(true);
        return user;
    }

    public User markAsAlreadyHasDrawn(User user){
        user.setIfHasDrawn(true);
        return user;
    }


}
