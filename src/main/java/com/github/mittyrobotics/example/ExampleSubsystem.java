package com.github.mittyrobotics.example;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Example Subsystem Code
public class ExampleSubsystem extends SubsystemBase {
	private WPI_TalonSRX exampleTalon;
	private double count = 0;

	//Making it a Singleton
	private static ExampleSubsystem instance;
	public static ExampleSubsystem getInstance(){
		if(instance == null){
			instance = new ExampleSubsystem();
		}
		return instance;
	}
	private ExampleSubsystem(){
		super();
		setName("Example Subsystem");
		setDefaultCommand(new ExampleCommand());
	}

	//Function that runs continuously regardless of any commands being run
	@Override
	public void periodic(){

	}

	//Function to initialize the hardware
	public void initHardware(){
		exampleTalon = new WPI_TalonSRX(0);
		exampleTalon.configFactoryDefault();
		exampleTalon.setInverted(true);
		exampleTalon.setNeutralMode(NeutralMode.Coast);
		exampleTalon.setSensorPhase(true);
		exampleTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		exampleTalon.config_kP(0, 0.2);
		exampleTalon.config_kI(0, 0);
		exampleTalon.config_kD(0, 1);
	}

	//Example Function used in ExampleInstantCommand
	public void exampleInstantFunction(){
		exampleTalon.set(ControlMode.PercentOutput, 0);
	}

	//Example function used in ExampleRunCommand
	public void exampleRepeatFunction(double value){
		exampleTalon.set(ControlMode.PercentOutput, value);
	}

	//Example functions used in ExampleCommand
	public void exampleFunction1(){
		exampleTalon.config_kP(0, 2);
	}
	public void exampleFunction2(){
		count++;
		exampleTalon.set(ControlMode.Position, count);
	}
	public void exampleFunction3(){
		exampleTalon.set(ControlMode.PercentOutput, Math.max(0, exampleTalon.getMotorOutputPercent() - 0.05));
	}
}
