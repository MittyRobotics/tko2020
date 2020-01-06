package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainTalon extends SubsystemBase {
    private WPI_TalonSRX[] leftDrive = new WPI_TalonSRX[2];
	private WPI_TalonSRX[] rightDrive = new WPI_TalonSRX[2];
	private static DriveTrainTalon ourInstance = new DriveTrainTalon();
    private double count = 0;

    //Making it a Singleton
    private static DriveTrainTalon instance;
    public static DriveTrainTalon getInstance(){
        if(instance == null){
            instance = new DriveTrainTalon();
        }
        return instance;
    }
    private DriveTrainTalon(){
        super();
        setName("Example Subsystem");
        //setDefaultCommand(new Command());
    }


    @Override
    public void periodic(){

    }


    public void initHardware(){
	    leftDrive[0] = new WPI_TalonSRX(20);
	    leftDrive[1] = new WPI_TalonSRX(21);
	    rightDrive[0] = new WPI_TalonSRX(23);
	    rightDrive[1] = new WPI_TalonSRX(22);

	    leftDrive[1].setInverted(false);
	    rightDrive[0].setInverted(true);
	    rightDrive[1].setInverted(true);

	    leftDrive[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
	    rightDrive[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

	    leftDrive[0].setSensorPhase(false);
	    rightDrive[0].setSensorPhase(true);

	    leftDrive[1].set(ControlMode.Follower, leftDrive[0].getDeviceID());
	    rightDrive[1].set(ControlMode.Follower, rightDrive[0].getDeviceID());

	    leftDrive[0].config_kP(0, PID.DRIVE_VELOCITY[0]);
	    leftDrive[0].config_kI(0, PID.DRIVE_VELOCITY[1]);
	    leftDrive[0].config_kD(0, PID.DRIVE_VELOCITY[2]);
	    rightDrive[0].config_kP(0, PID.DRIVE_VELOCITY[0]);
	    rightDrive[0].config_kI(0, PID.DRIVE_VELOCITY[1]);
	    rightDrive[0].config_kD(0, PID.DRIVE_VELOCITY[2]);

	    leftDrive[0].setNeutralMode(NeutralMode.Brake);
	    leftDrive[1].setNeutralMode(NeutralMode.Brake);
	    rightDrive[0].setNeutralMode(NeutralMode.Brake);
	    rightDrive[1].setNeutralMode(NeutralMode.Brake);

//	    exampleTalon = new WPI_TalonSRX(0);
//        exampleTalon.configFactoryDefault();
//        exampleTalon.setInverted(true);
//        exampleTalon.setNeutralMode(NeutralMode.Coast);
//        exampleTalon.setSensorPhase(true);
//        exampleTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
//        exampleTalon.config_kP(0, 0.2);
//        exampleTalon.config_kI(0, 0);
//        exampleTalon.config_kD(0, 1);
    }

    //Example Function used in ExampleInstantCommand
    public void exampleInstantFunction(){
        leftDrive[0].set(ControlMode.PercentOutput, 0);
	    leftDrive[1].set(ControlMode.PercentOutput, 0);
	    rightDrive[0].set(ControlMode.PercentOutput, 0);
	    rightDrive[1].set(ControlMode.PercentOutput, 0);
    }

	public void tankVelocity(double left, double right) {
		left *= 79.68;
		right *= 79.68;
		leftDrive[0].set(ControlMode.Velocity, left / 10);
		rightDrive[0].set(ControlMode.Velocity, right/ 10);
	}
}
