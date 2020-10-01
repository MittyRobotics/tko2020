package com.github.mittyrobotics.NewClimber;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.constants.ShooterConstants;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Climber subsystem with 2 sparks
 */

public class ClimberSubsystem extends SubsystemBase implements IMotorSubsystem {
    /**
     * {@link ClimberSubsystem} instance
     */
    private static ClimberSubsystem instance;

    /**
     * Shooter {@link CANSparkMax}s
     */
    private CANSparkMax climberLeftSparkMaster, climberRightSparkMaster;

    /**
     * Climber {@link CANEncoder}s
     */
    private CANEncoder leftEncoder, rightEncoder;

    private WPI_TalonSRX talonSRX1, talonSRX2;

    private PIDController primaryController1, primaryController2, auxController;

    private double setpoint;

    private boolean shouldMoveArm;

    /**
     * Calls SubsystemBase constructor and names the subsystem 'Climber'
     */
    private ClimberSubsystem() {
        super();
        setName("Climber");
    }

    /**
     * Returns the {@link ClimberSubsystem}'s {@link SubsystemBase} instance.
     *
     * @return the {@link ClimberSubsystem}'s {@link SubsystemBase} instance.
     */
    public static ClimberSubsystem getInstance() {
        if (instance == null) {
            instance = new ClimberSubsystem();
        }
        return instance;
    }

    /**
     * Initializes the climber's hardware.
     */
    @Override
    public void initHardware() {
        climberLeftSparkMaster =
                new CANSparkMax(ClimberConstants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        climberLeftSparkMaster.restoreFactoryDefaults();
        climberLeftSparkMaster.setInverted(ClimberConstants.LEFT_SPARK_INVERSION);
        this.leftEncoder = climberLeftSparkMaster.getEncoder();

        climberRightSparkMaster =
                new CANSparkMax(ClimberConstants.RIGHT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        climberRightSparkMaster.restoreFactoryDefaults();
        climberRightSparkMaster.setInverted(ClimberConstants.RIGHT_SPARK_INVERSION);
        this.rightEncoder = climberRightSparkMaster.getEncoder();
        setDefaultCommand(new StopClimberCommand());

        talonSRX1 = new WPI_TalonSRX(0);
        talonSRX2 = new WPI_TalonSRX(1);
        primaryController1 = new PIDController(1, 0, 0);
        primaryController2 = new PIDController(1, 0, 0);
        auxController = new PIDController(1, 0, 0);
        auxController.setSetpoint(0);
        shouldMoveArm = false;
        setpoint = ClimberConstants.ARM_SETPOINT;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Left Encoder Position", getLeftEncoderPosition());
        SmartDashboard.putNumber("Right Encoder Position", getRightEncoderPosition());
    }

    public void setLeftSpark(double percent) {
        climberLeftSparkMaster.set(percent);
    }

    public void setRightSpark(double percent) {
        climberRightSparkMaster.set(percent);
    }

    public void stopSparks() {
        setLeftSpark(0);
        setRightSpark(0);
    }

    public double getLeftEncoderPosition() {
        return leftEncoder.getPosition();
    }

    public double getRightEncoderPosition() {
        return rightEncoder.getPosition();
    }

    public void overrideSetMotor(double percent) {
        climberLeftSparkMaster.set(percent);
        climberRightSparkMaster.set(percent);
    }

    public void setSetpoint(double leftSetpoint, double rightSetpoint){
        primaryController1.setSetpoint(leftSetpoint);
        primaryController2.setSetpoint(rightSetpoint);
        shouldMoveArm = true;
    }

    public void periodic(){
        if (shouldMoveArm){
            double auxSpeed = auxController.calculate(climberLeftSparkMaster.get() - climberRightSparkMaster.get());
            double primarySpeed1 = primaryController1.calculate(climberLeftSparkMaster.get());
            double primarySpeed2 = primaryController2.calculate(climberRightSparkMaster.get());
            setLeftSpark(primarySpeed1 + auxSpeed);
            setRightSpark(primarySpeed2 - auxSpeed);
        }
    }
}