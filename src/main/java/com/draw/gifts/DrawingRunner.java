package com.draw.gifts;

import com.draw.gifts.domain.Drawing;
import com.draw.gifts.domain.User;
import com.draw.gifts.service.DrawingService;
import com.draw.gifts.service.UserService;

import java.util.Scanner;

public class DrawingRunner {

    private UserService userService;
    private DrawingService drawingService;
    private static final Scanner scanner = new Scanner(System.in);

    public DrawingRunner(UserService userService, DrawingService drawingService) {
        this.userService = userService;
        this.drawingService = drawingService;
    }

    public void runDrawing(){

        Drawing drawing = drawingService.openDrawing();
        System.out.println("Wprowadź pojedyńczo imiona graczy. Naciśnij x jeśli wprowadziłeś już wszystkich");

        addUsers(drawing);

        while(drawing.getUsersList().stream().filter(u -> u.isIfHasDrawn() == false).count() > 0){

            for(int i = 0; i < 50; i++) {
                System.out.println(" ");
            }

            User drawingUser = drawingService.choseDrawingUser(drawing);

            System.out.println(drawingUser.getName() + " kliknij x i Enter, aby wylosować osobę której sprawisz prezent");

            for(int i = 0; i < 10; i++) {
                System.out.println(" ");
            }

            checkIfUserWantsToDraw(drawingUser, drawing);

            System.out.println(drawingUser.getName() + " kliknij x i Enter, jeśli zapoznałeś/aś się z wynikiem. Umożliwi to losowanie kolejnej osobie");
            finishSingleDrawing(drawingUser);

        }

        drawingService.closeDrawing(drawing);
        System.out.println("KONIEC");

    }

    public void addUsers(Drawing drawing){
        boolean finish = false;

        while(!finish){
            String playerName = scanner.next();
            if(playerName.equals("x")){
                finish = true;
            } else {
                userService.createUser(playerName, drawing);
            }

        }

    }

    public void finishSingleDrawing(User drawingUser){
        boolean ifFinishSingleDrawing = false;

        while(!ifFinishSingleDrawing){

            String finishSingleDrawing = scanner.next();

            if(finishSingleDrawing.equals("x")){
                ifFinishSingleDrawing = true;
            } else {
                System.out.println(drawingUser.getName() + " kliknij x i Enter, jeśli zapoznałeś/aś się z wynikiem. Umożliwi to losowanie kolejnej osobie.");
            }
        }

    }

    public void checkIfUserWantsToDraw(User drawingUser, Drawing drawing){

        boolean ifUserWantToDraw = false;

        while(!ifUserWantToDraw){
            String draw = scanner.next();
            if(draw.equals("x")){
                drawingService.draw(drawing, drawingUser);
                ifUserWantToDraw = true;
            } else {
                System.out.println(drawingUser.getName() + " kliknij x i Enter, aby wylosować osobę której sprawisz prezent");
            }
        }

    }


}
