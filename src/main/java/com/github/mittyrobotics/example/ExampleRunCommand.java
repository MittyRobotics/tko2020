package com.github.mittyrobotics.example;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.RunCommand;
//Example Run Command, which basically is a command with a function as a parameter running in execute and isFinished() returns false
public class ExampleRunCommand extends RunCommand {

	public ExampleRunCommand() {
		super(
				//Function being used to run in execute()
				() -> ExampleSubsystem.getInstance().exampleRepeatFunction(ExampleOI.getInstance().getController().
						getX(GenericHID.Hand.kLeft)),
				//Subsystem being used
				ExampleSubsystem.getInstance());
	}
}