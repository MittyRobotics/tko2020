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

	    leftDrive[0].setInverted(Constants.LEFT_TALON_INVERSIONS[0]);
	    leftDrive[1].setInverted(Constants.LEFT_TALON_INVERSIONS[1]);
	    rightDrive[0].setInverted(Constants.RIGHT_TALON_INVERSIONS[0]);
	    rightDrive[1].setInverted(Constants.RIGHT_TALON_INVERSIONS[1]);

	    leftDrive[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
	    rightDrive[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

	    leftDrive[0].setSensorPhase(false);
	    rightDrive[0].setSensorPhase(true);

	    leftDrive[1].set(ControlMode.Follower, leftDrive[0].getDeviceID());
	    rightDrive[1].set(ControlMode.Follower, rightDrive[0].getDeviceID());

	    leftDrive[0].config_kP(0, Constants.DRIVE_VELOCITY_PID[0]);
	    leftDrive[0].config_kI(0, Constants.DRIVE_VELOCITY_PID[1]);
	    leftDrive[0].config_kD(0, Constants.DRIVE_VELOCITY_PID[2]);
	    rightDrive[0].config_kP(0, Constants.DRIVE_VELOCITY_PID[0]);
	    rightDrive[0].config_kI(0, Constants.DRIVE_VELOCITY_PID[1]);
	    rightDrive[0].config_kD(0, Constants.DRIVE_VELOCITY_PID[2]);
	    leftDrive[0].setNeutralMode(NeutralMode.Brake);
	    leftDrive[1].setNeutralMode(NeutralMode.Brake);
	    rightDrive[0].setNeutralMode(NeutralMode.Brake);
	    rightDrive[1].setNeutralMode(NeutralMode.Brake);

    }

	public void tankVelocity(double left, double right) {
		left *= Constants.TICKS_PER_INCH;
		right *= Constants.TICKS_PER_INCH;
		leftDrive[0].set(ControlMode.Velocity, left / 10);
		rightDrive[0].set(ControlMode.Velocity, right/ 10);
	}
}