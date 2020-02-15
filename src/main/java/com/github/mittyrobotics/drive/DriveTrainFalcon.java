package com.github.mittyrobotics.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
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

        leftDrive[0].setInverted(false);
        leftDrive[1].setInverted(false);

        rightDrive[0].setInverted(true);
        rightDrive[1].setInverted(true);

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

        leftDrive[0].setSensorPhase(false);
        rightDrive[0].setSensorPhase(false);
        leftDrive[1].setSensorPhase(false);
        rightDrive[0].setSensorPhase(false);
    }

    public void tankDrive(double left, double right){
        leftDrive[0].set(TalonFXControlMode.PercentOutput, left);
        leftDrive[1].set(TalonFXControlMode.PercentOutput, left);
        rightDrive[0].set(TalonFXControlMode.PercentOutput, right);
        rightDrive[1].set(TalonFXControlMode.PercentOutput, right);
//        if(Math.abs(left) > 0.1) {
//
//        } else {
//            leftDrive[0].set(TalonFXControlMode.PercentOutput, 0);
//            leftDrive[1].set(TalonFXControlMode.PercentOutput, 0);
//        }
//        if(Math.abs(right) > 0.1){
//
//        }  else {
//            rightDrive[0].set(TalonFXControlMode.PercentOutput, 0);
//            rightDrive[1].set(TalonFXControlMode.PercentOutput, 0);
//        }
    }

    public void tankVelocity(double left, double right) {
        customTankVelocity(left, right);
    }

    public void movePos(double left, double right) {
        leftDrive[0].set(TalonFXControlMode.Position, left * Constants.TICKS_PER_INCH_FALCON);
        rightDrive[0].set(TalonFXControlMode.Position, right * Constants.TICKS_PER_INCH_FALCON);
    }

    public double getLeftEncoder() {
        return leftDrive[0].getSelectedSensorPosition() / Constants.TICKS_PER_INCH_FALCON;
    }

    public double getRightEncoder() {
        return rightDrive[0].getSelectedSensorPosition() / Constants.TICKS_PER_INCH_FALCON;
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

    private double leftLastMeasured = 0;
    private double rightLastMeasured = 0;
    private final double kV = 0.06; //0.06
    private final double kA = 0.0; //0.0
    private final double kP = 0.01; //0.01

    private double latestLeftVelSetpoint = 0;
    private double latestRightVelSetpoint = 0;

    public void customTankVelocity(double leftVel, double rightVel) {
        double left;
        double right;

        this.latestLeftVelSetpoint = leftVel;
        this.latestRightVelSetpoint = rightVel;

        double MAX_SPEED = 12;

        double measuredLeft = getLeftEncoderVelocity();
        double FFLeft = kV * leftVel + kA * ((measuredLeft - leftLastMeasured) / .02);
        leftLastMeasured = measuredLeft;
        double errorLeft = leftVel - measuredLeft;
        double FBLeft = kP * errorLeft;
        left = (FFLeft + FBLeft);
        left = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, left));

        double measuredRight = getRightEncoderVelocity();

        double FFRight = kV * rightVel + kA * ((measuredRight - rightLastMeasured) / .02);

        rightLastMeasured = measuredRight;

        double errorRight = rightVel - measuredRight;

        double FBRight = kP * errorRight;

        right = (FFRight + FBRight);

        right = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, right));

        left = left / 12;
        right = right / 12;

        tankDrive(left, right);
    }

    public double getRightVelSetpoint() {
        return latestRightVelSetpoint;
    }

    public double getLeftVelSetpoint(){
        return latestLeftVelSetpoint;
    }
}