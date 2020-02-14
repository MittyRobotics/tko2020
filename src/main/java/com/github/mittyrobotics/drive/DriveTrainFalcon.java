package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainFalcon extends SubsystemBase {
    private WPI_TalonFX[] leftDrive = new WPI_TalonFX[2];
    private WPI_TalonFX[] rightDrive = new WPI_TalonFX[2];


    private static DriveTrainFalcon instance;
    public static DriveTrainFalcon getInstance() {
        if(instance == null){
            instance = new DriveTrainFalcon();
        }
        return instance;
    }

    private DriveTrainFalcon() {
        super();
        setName("Drive Train Falcon");
    }

    @Override
    public void periodic() {

    }

    public void initHardware() {

        leftDrive[0] = new WPI_TalonFX(Constants.LEFT_FALCON_1);
        leftDrive[1] = new WPI_TalonFX(Constants.LEFT_FALCON_2);
        rightDrive[0] = new WPI_TalonFX(Constants.RIGHT_FALCON_1);
        rightDrive[1] = new WPI_TalonFX(Constants.RIGHT_FALCON_2);

        leftDrive[0].setInverted(true);
        leftDrive[1].setInverted(true);

        leftDrive[1].set(TalonFXControlMode.Follower, leftDrive[0].getDeviceID());
        rightDrive[1].set(TalonFXControlMode.Follower, rightDrive[0].getDeviceID());

//        leftDrive[0].config_kP(0, Constants.DRIVE_VELOCITY_PID[0]);
//        leftDrive[0].config_kI(0, Constants.DRIVE_VELOCITY_PID[1]);
//        leftDrive[0].config_kD(0, Constants.DRIVE_VELOCITY_PID[2]);
//        rightDrive[0].config_kP(0, Constants.DRIVE_VELOCITY_PID[0]);
//        rightDrive[0].config_kI(0, Constants.DRIVE_VELOCITY_PID[1]);
//        rightDrive[0].config_kD(0, Constants.DRIVE_VELOCITY_PID[2]);

        leftDrive[0].setNeutralMode(NeutralMode.Brake);
        rightDrive[0].setNeutralMode(NeutralMode.Brake);
        leftDrive[1].setNeutralMode(NeutralMode.Brake);
        rightDrive[0].setNeutralMode(NeutralMode.Brake);


        setDefaultCommand(new CurvatureSteering());


        //controller = new PIDController(0.309 / 12.0, 0, 0);


    }

    public void tankDrive(double left, double right){
        if(Math.abs(left) > 0.1) {
            leftDrive[0].set(TalonFXControlMode.PercentOutput, left);
            leftDrive[1].set(TalonFXControlMode.PercentOutput, left);
        } else {
            leftDrive[0].set(TalonFXControlMode.PercentOutput, 0);
            leftDrive[1].set(TalonFXControlMode.PercentOutput, 0);
        }
        if(Math.abs(right) > 0.1){
            rightDrive[0].set(TalonFXControlMode.PercentOutput, right);
            rightDrive[1].set(TalonFXControlMode.PercentOutput, right);
        }  else {
            rightDrive[0].set(TalonFXControlMode.PercentOutput, 0);
            rightDrive[1].set(TalonFXControlMode.PercentOutput, 0);
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
        leftDrive[0].set(TalonFXControlMode.Position, left * Constants.TICKS_PER_INCH_FALCON);
        rightDrive[0].set(TalonFXControlMode.Position, right * Constants.TICKS_PER_INCH_FALCON);
    }

    public double getLeftEncoder() {
        return leftDrive[0].getSelectedSensorPosition() / Constants.TICKS_PER_INCH_FALCON * 10;
    }

    public double getRightEncoder() {
        return rightDrive[0].getSelectedSensorPosition() / Constants.TICKS_PER_INCH_FALCON * 10;
    }

    public double getLeftEncoderVelocity() {
        return (leftDrive[0].getSelectedSensorVelocity() / Constants.TICKS_PER_INCH_FALCON * 10);
    }

    public double getRightEncoderVelocity() {
        return (rightDrive[0].getSelectedSensorVelocity() / Constants.TICKS_PER_INCH_FALCON * 10);
    }

    public void resetEncoder() {
        leftDrive[0].setSelectedSensorPosition(0);
        rightDrive[0].setSelectedSensorPosition(0);
    }

    public WPI_TalonFX getLeftFalcon() {
        return leftDrive[0];
    }

    public WPI_TalonFX getRightFalcon() {
        return rightDrive[0];
    }

    public void velocityPIDFeedForward(double leftVelocity, double rightVelocity) { // in inches per second
        leftVelocity *= Constants.TICKS_PER_INCH_FALCON / 10.0;
        double ffLeft = leftVelocity * 1.0/Constants.MAX_FALCON_SPEED;
        rightVelocity *= Constants.TICKS_PER_INCH_FALCON / 10.0;
        double ffRight = (rightVelocity) * 1.0/Constants.MAX_FALCON_SPEED;

        PIDController leftController = new PIDController(Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[0], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[1], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[2]);
        PIDController rightController = new PIDController(Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[0], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[1], Constants.HIGH_SPEED_VELOCITY_PID_CONTROLLER[2]);

        leftController.setSetpoint(leftVelocity);
        rightController.setSetpoint(rightVelocity);
        //System.out.println(leftController.calculate(leftDrive[0].getSelectedSensorVelocity()));

        double fbLeft = leftController.calculate(leftDrive[0].getSelectedSensorVelocity());
        double fbRight = rightController.calculate(rightDrive[0].getSelectedSensorVelocity());

        if(Math.abs(leftVelocity - leftDrive[0].getSelectedSensorVelocity()) > 10 * Constants.TICKS_PER_INCH / 10){
            fbLeft = 0;
        }
        if(Math.abs(rightVelocity - rightDrive[0].getSelectedSensorVelocity()) > 10 * Constants.TICKS_PER_INCH / 10){
            fbRight = 0;
        }

        leftDrive[0].set(TalonFXControlMode.PercentOutput, ffLeft + fbLeft);
        rightDrive[0].set(TalonFXControlMode.PercentOutput, ffRight + fbRight);
        leftDrive[1].set(TalonFXControlMode.PercentOutput, ffLeft + fbLeft);
        rightDrive[1].set(TalonFXControlMode.PercentOutput, ffRight + fbRight);

    }
}
