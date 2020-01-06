package com.github.mittyrobotics.example;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
//OI Class, essentially the same as last year
public class ExampleOI {
	private static ExampleOI instance;
	private XboxController controller;
	public static ExampleOI getInstance(){
		if(instance == null){
			instance = new ExampleOI();
		}
		return instance;
	}
	public XboxController getController(){
		if(controller == null){
			controller = new XboxController(0);
		}
		return controller;
	}
	public void setupDigitalButtons(){
		Button instCommand = new Button() {
			@Override
			public boolean get(){
				return getController().getAButton();
			}
		};
		instCommand.whenPressed(new ExampleInstantCommand());
	}
}