package com.draw.gifts;

import com.draw.gifts.service.DrawingService;
import com.draw.gifts.service.UserService;


public class GiftsApplication {

	public static void main(String[] args) {

		UserService userService = new UserService();
		DrawingService drawingService = new DrawingService(userService);

		DrawingRunner drawingRunner = new DrawingRunner(userService, drawingService);
		drawingRunner.runDrawing();


	}


}



