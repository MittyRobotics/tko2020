package com.github.mittyrobotics.example;

import edu.wpi.first.wpilibj2.command.CommandBase;
//ExampleCommand showing all the different functionality in CommandBase
public class ExampleCommand extends CommandBase {
	public ExampleCommand(){
		addRequirements(ExampleSubsystem.getInstance()); //Subsystems being used on the command
	}
	@Override
	public void initialize(){
		ExampleSubsystem.getInstance().exampleFunction1(); //Example function being run in initialize
	}
	@Override
	public void execute(){
		ExampleSubsystem.getInstance().exampleFunction2(); //Example function being run in execute
	}
	@Override
	public void end(boolean interrupted){
		if(interrupted){
			ExampleSubsystem.getInstance().exampleFunction3(); //Function run if it ends when interrupted by other command
		} else {
			ExampleSubsystem.getInstance().exampleFunction1(); //Function run if it ends when isFinished returns true
		}
	}
	@Override
	public boolean isFinished(){ //WHen command should end (command never ends in this example)
		return false;
	}
}