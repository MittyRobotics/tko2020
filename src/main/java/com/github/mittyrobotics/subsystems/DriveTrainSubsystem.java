package com.github.mittyrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.github.mittyrobotics.commands.ArcadeDriveCommand;
import com.github.mittyrobotics.constants.DriveConstants;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class DriveTrainSubsystem extends SubsystemBase implements ISubsystem {
    private static DriveTrainSubsystem instance;
    private WPI_TalonFX[] leftDrive = new WPI_TalonFX[2];
    private WPI_TalonFX[] rightDrive = new WPI_TalonFX[2];
    private double leftLastMeasured = 0;
    private double rightLastMeasured = 0;
    private double latestLeftVelSetpoint = 0;
    private double latestRightVelSetpoint = 0;

    private DriveTrainSubsystem() {
        super();
        setName("Drive Train Falcon");
    }

    public static DriveTrainSubsystem getInstance() {
        if (instance == null) {
            instance = new DriveTrainSubsystem();
        }
        return instance;
    }

    @Override
    public void periodic() {

    }

    public void initHardware() {

        leftDrive[0] = new WPI_TalonFX(DriveConstants.LEFT_FALCON_1);
        leftDrive[1] = new WPI_TalonFX(DriveConstants.LEFT_FALCON_2);
        rightDrive[0] = new WPI_TalonFX(DriveConstants.RIGHT_FALCON_1);
        rightDrive[1] = new WPI_TalonFX(DriveConstants.RIGHT_FALCON_2);
        leftDrive[0].configFactoryDefault();
        leftDrive[1].configFactoryDefault();
        rightDrive[0].configFactoryDefault();
        rightDrive[1].configFactoryDefault();

        leftDrive[0].setInverted(false);
        leftDrive[1].setInverted(false);

        rightDrive[0].setInverted(true);
        rightDrive[1].setInverted(true);

//        leftDrive[1].set(TalonFXControlMode.Follower, leftDrive[0].getDeviceID());
//        rightDrive[1].set(TalonFXControlMode.Follower, rightDrive[0].getDeviceID());


//        leftDrive[0].config_kP(0, Constants.DRIVE_VELOCITY_PID[0]);
//        leftDrive[0].config_kI(0, Constants.DRIVE_VELOCITY_PID[1]);
//        leftDrive[0].config_kD(0, Constants.DRIVE_VELOCITY_PID[2]);
//        rightDrive[0].config_kP(0, Constants.DRIVE_VELOCITY_PID[0]);
//        rightDrive[0].config_kI(0, Constants.DRIVE_VELOCITY_PID[1]);
//        rightDrive[0].config_kD(0, Constants.DRIVE_VELOCITY_PID[2]);

        leftDrive[0].setNeutralMode(NeutralMode.Coast);
        rightDrive[0].setNeutralMode(NeutralMode.Coast);
        leftDrive[1].setNeutralMode(NeutralMode.Coast);
        rightDrive[0].setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("drive-vel-left", getLeftEncoderVelocity());
        SmartDashboard.putNumber("drive-vel-right", getRightEncoderVelocity());
        SmartDashboard.putNumber("drive-vel-left-setpoint", getLeftVelSetpoint());
        SmartDashboard.putNumber("drive-vel-right-setpoint", getRightVelSetpoint());
    }

    public void tankDrive(double left, double right, double threshold, double multiplier) {
        multiplier = MathUtil.clamp(multiplier, 0, 1);
        threshold = MathUtil.clamp(threshold, 0, 1);
        left = MathUtil.clamp(left, -1, 1);
        right = MathUtil.clamp(right, -1, 1);
        if (Math.abs(left) > threshold) {
            leftDrive[0].set(left * multiplier);
            leftDrive[1].set(left * multiplier);
        } else {
            leftDrive[0].set(0);
            leftDrive[1].set(0);
        }
        if (Math.abs(right) > 0.1) {
            rightDrive[0].set(right * multiplier);
            rightDrive[1].set(right * multiplier);
        } else {
            rightDrive[0].set(0);
            rightDrive[1].set(0);
        }
    }

    public void tankDrive(double left, double right) {
        tankDrive(left, right, 0.2, 1);
    }

    public double getLeftEncoder() {
        return leftDrive[0].getSelectedSensorPosition() / DriveConstants.TICKS_PER_INCH_FALCON;
    }

    public double getRightEncoder() {
        return rightDrive[0].getSelectedSensorPosition() / DriveConstants.TICKS_PER_INCH_FALCON;
    }

    public double getLeftEncoderVelocity() {
        return (leftDrive[0].getSelectedSensorVelocity() / DriveConstants.TICKS_PER_INCH_FALCON * 10);
    }

    public double getRightEncoderVelocity() {
        return (rightDrive[0].getSelectedSensorVelocity() / DriveConstants.TICKS_PER_INCH_FALCON * 10);
    }

    public void resetEncoder() {
        leftDrive[0].setSelectedSensorPosition(0);
        rightDrive[0].setSelectedSensorPosition(0);
    }

    public void tankVelocity(double leftVel, double rightVel) {
        double left;
        double right;

        this.latestLeftVelSetpoint = leftVel;
        this.latestRightVelSetpoint = rightVel;

        double MAX_SPEED = 12;

        double measuredLeft = getLeftEncoderVelocity();
        //0.06
        //0.0
        double kA = 0.0;
        double FFLeft = DriveConstants.DRIVE_FALCON_FF * leftVel + kA * ((measuredLeft - leftLastMeasured) / .02);
        leftLastMeasured = measuredLeft;
        double errorLeft = leftVel - measuredLeft;
        //0.01
        double FBLeft = DriveConstants.DRIVE_FALCON_P * errorLeft;
        left = (FFLeft + FBLeft);
        left = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, left));

        double measuredRight = getRightEncoderVelocity();

        double FFRight = DriveConstants.DRIVE_FALCON_FF * rightVel + kA * ((measuredRight - rightLastMeasured) / .02);

        rightLastMeasured = measuredRight;

        double errorRight = rightVel - measuredRight;

        double FBRight = DriveConstants.DRIVE_FALCON_P * errorRight;

        right = (FFRight + FBRight);

        right = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, right));

        left = left / 12;
        right = right / 12;

        tankDrive(left, right);
    }

    public double getRightVelSetpoint() {
        return latestRightVelSetpoint;
    }

    public double getLeftVelSetpoint() {
        return latestLeftVelSetpoint;
    }

    public double getAverageVelocity() {
        return (getLeftEncoderVelocity() + getRightEncoderVelocity()) / 2;
    }
}
