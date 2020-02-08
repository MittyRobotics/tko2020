package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import java.util.jar.JarOutputStream;

public class DriveTrainTalon extends SubsystemBase {
	private WPI_TalonSRX[] leftDrive = new WPI_TalonSRX[2];
	private WPI_TalonSRX[] rightDrive = new WPI_TalonSRX[1r];
	private static DriveTrainTalon ourInstance = new DriveTrainTalon();
	private PIDController controller;

	//Making it a Singleton
	private static DriveTrainTalon instance;

	public static DriveTrainTalon getInstance() {
		if (instance == null) {
			instance = new DriveTrainTalon();
		}
		return instance;
	}

	private DriveTrainTalon() {
		super();
		setName("Example Subsystem");
	}


	@Override
	public void periodic() {

	}


	public void initHardware() {
		leftDrive[0] = new WPI_TalonSRX(Constants.LEFT_TALON_1);
		leftDrive[1] = new WPI_TalonSRX(Constants.LEFT_TALON_2);
		rightDrive[0] = new WPI_TalonSRX(Constants.RIGHT_TALON_1);
		rightDrive[1] = new WPI_TalonSRX(Constants.RIGHT_TALON_2);

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

		setDefaultCommand(new MaxSpeedTestTalon);

		//Feedforward Velocit1y PID
		controller = new PIDController(0.309 / 12.0, 0, 0);


	}

	public void tankDrive(double left, double right) {
		if (Math.abs(left) < 0.1) {
			leftDrive[0].set(ControlMode.PercentOutput, 0);
			leftDrive[1].set(ControlMode.PercentOutput, 0);
		} else {
			leftDrive[0].set(ControlMode.PercentOutput, left);
			leftDrive[1].set(ControlMode.PercentOutput, left);
		}
		if (Math.abs(right) < 0.1) {
			rightDrive[0].set(ControlMode.PercentOutput, 0);
			rightDrive[1].set(ControlMode.PercentOutput, 0);
		} else {
			rightDrive[0].set(ControlMode.PercentOutput, right);
			rightDrive[1].set(ControlMode.PercentOutput, right);
		}
	}

	public void tankVelocity(double left, double right) {
		if(Math.abs(left) < 0.05){
			left = 0;
		}
		if(Math.abs(right) < 0.05){
			right = 0;
		}
		velocityPIDFeedForward(left*340, right*340);
	}

	public void movePos(double left, double right) {
		leftDrive[0].set(ControlMode.Position, left * Constants.TICKS_PER_INCH);
		rightDrive[0].set(ControlMode.Position, right * Constants.TICKS_PER_INCH);
	}

	public double getLeftEncoder() {
		return leftDrive[0].getSelectedSensorPosition() / Constants.TICKS_PER_INCH;
	}

	public double getRightEncoder() {
		return rightDrive[0].getSelectedSensorPosition() / Constants.TICKS_PER_INCH;
	}

	public double getLeftEncoderVelocity() {
		return (leftDrive[0].getSelectedSensorVelocity() / Constants.TICKS_PER_INCH) * 10;
	}

	public double getRightEncoderVelocity() {
		return (rightDrive[0].getSelectedSensorVelocity() / Constants.TICKS_PER_INCH) * 10;
	}

	public void resetEncoder() {
		leftDrive[0].setSelectedSensorPosition(0);
		rightDrive[0].setSelectedSensorPosition(0);
	}

	public WPI_TalonSRX getLeftTalon() {
		return leftDrive[0];
	}

	public WPI_TalonSRX getRightTalon() {
		return rightDrive[0];
	}

	public void velocityPIDFeedForward(double leftVelocity, double rightVelocity) {
		leftVelocity *= Constants.TICKS_PER_INCH / 10.0;
		double ffLeft = leftVelocity * 1.0/Constants.MAX_FALCON_SPEED;
		rightVelocity *= Constants.TICKS_PER_INCH / 10.0;
		double ffRight = (rightVelocity) * 1.0/Constants.MAX_FALCON_SPEED;

		PIDController leftController = new PIDController(Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[0], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[1], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[2]);
		PIDController rightController = new PIDController(Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[0], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[1], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[2]);

		leftController.setSetpoint(leftVelocity);
		rightController.setSetpoint(rightVelocity);
		//System.out.println(leftController.calculate(leftDrive[0].getSelectedSensorVelocity()));

		double fbLeft = leftController.calculate(leftDrive[0].getSelectedSensorVelocity());
		double fbRight = rightController.calculate(rightDrive[0].getSelectedSensorVelocity());

		if(Math.abs(leftVelocity - leftDrive[0].getSelectedSensorVelocity()) > 10 * Constants.TICKS_PER_INCH_FALCON / 10){
			fbLeft = 0;
		}
		if(Math.abs(rightVelocity - rightDrive[0].getSelectedSensorVelocity()) > 10 * Constants.TICKS_PER_INCH_FALCON / 10){
			fbRight = 0;
		}
		leftDrive[0].set(ControlMode.PercentOutput, ffLeft + fbLeft);
		rightDrive[0].set(ControlMode.PercentOutput, ffRight + fbRight);
		leftDrive[0].set(ControlMode.PercentOutput, ffLeft + fbLeft);
		rightDrive[0].set(ControlMode.PercentOutput, ffRight + fbRight);

	}
}