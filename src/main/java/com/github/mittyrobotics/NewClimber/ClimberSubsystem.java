package com.github.mittyrobotics.NewClimber;

import com.github.mittyrobotics.constants.ShooterConstants;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import com.revrobotics.*;
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
}