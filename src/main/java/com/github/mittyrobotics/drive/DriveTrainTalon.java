package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainTalon extends SubsystemBase {
	private WPI_TalonSRX[] leftDrive = new WPI_TalonSRX[2];
	private WPI_TalonSRX[] rightDrive = new WPI_TalonSRX[2];
	private static DriveTrainTalon ourInstance = new DriveTrainTalon();
	private double count = 0;
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

		setDefaultCommand(new JoystickDrive_CarSteering());

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
		left *= Constants.TICKS_PER_INCH;
		right *= Constants.TICKS_PER_INCH;
		leftDrive[0].set(ControlMode.Velocity, left / 10);
		rightDrive[0].set(ControlMode.Velocity, right / 10);
//		System.out.println(left + " " + right);
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

	public void velocityPIDFeedForward(double velocity) {
		//velocity /= 2;
		controller.setSetpoint(velocity);
		double feedBackLeft = -controller.calculate(leftDrive[0].getSelectedSensorVelocity() / Constants.TICKS_PER_INCH, velocity) / 10;
		double feedBackRight = -controller.calculate(rightDrive[0].getSelectedSensorVelocity() / Constants.TICKS_PER_INCH, velocity) / 10;
//		if(leftDrive[0].getSelectedSensorVelocity() < velocity){
//			feedBackLeft *= -1;
//			feedBackRight *= -1;
//		}
		double feedForward = (0.177*velocity) / 12;
//		System.out.println(feedBackLeft + " " + feedBackRight);
//		System.out.println(feedForward);
//		double addition = feedForward + feedBackLeft;
//		System.out.println("Addition: " + addition);
//		System.out.println(leftDrive[0].getMotorOutputPercent());
		leftDrive[0].set(ControlMode.PercentOutput, feedForward);
		rightDrive[0].set(ControlMode.PercentOutput, feedForward);

	}
}