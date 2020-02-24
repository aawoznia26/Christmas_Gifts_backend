package com.draw.gifts.controllers;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.User;
import com.draw.gifts.dto.UserDto;
import com.draw.gifts.service.DrawingService;
import com.draw.gifts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class DrawingController {

    @Autowired
    private UserService userService;

    @Autowired
    private DrawingService drawingService;

    @RequestMapping(method = RequestMethod.POST,  value = "/drawing/start")
    public Long startDrawing(@RequestBody List<String> userNames) {
        Drawing drawing = drawingService.openDrawing();
        userNames.stream().map(u -> userService.createUser(u, drawing)).collect(Collectors.toList());
        return drawing.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/drawing/select/{drawingId}")
    public UserDto chooseUserToDraw(@PathVariable Long drawingId) {
        return User.mapToUserDto(drawingService.choseDrawingUser(drawingId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/drawing/draw/{drawingId}")
    public UserDto draw(@PathVariable Long drawingId) {
        return User.mapToUserDto(drawingService.draw(drawingId));
    }

}
