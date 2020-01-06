package com.github.mittyrobotics.example;

import edu.wpi.first.wpilibj2.command.InstantCommand;
//Command that runs a function in initialize() and ends immediately (isFinished() returns true)
public class ExampleInstantCommand extends InstantCommand {
	public ExampleInstantCommand(){
		super(
				//Example function run once
				() -> ExampleSubsystem.getInstance().exampleInstantFunction(),
				//Subsystem command is being run on
				ExampleSubsystem.getInstance());
	}
}
